import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.*;
import java.util.ArrayList;


public abstract class MobilePlan implements Cloneable, Comparable<MobilePlan>, Serializable {
    protected String userName;
    protected int id;
    protected MobilePhone handset;
    protected int internetQuota;
    protected int capLimit;
    protected MyDate expiryDate;

    public MobilePlan(String userName, int id, MobilePhone handset, int internetQuota, int capLimit, MyDate myDate) throws
            PlanException, UsernameException {
        try {
            if (!userName.matches("USR[0-9]*U")) {
                throw new UsernameException(userName);
            }
        } catch (UsernameException e) {
            userName = e.getNewUsername();
        }
        this.userName = userName;
        try {
            if (id < 3000000 || id > 3999999) {
                throw new PlanException(id);
            }
        } catch (PlanException e) {
            id = e.getNewID();
        }
        this.id = id;
        this.handset = handset;
        this.internetQuota = internetQuota;
        this.capLimit = capLimit;
        this.expiryDate = myDate;
    }

    public double getHandsetPrice() {
        return handset.getPrice();
    }

    public String getHandsetModel() {
        return handset.getModel();
    }

    public int getId() {
        return id;
    }

    public MyDate getExpiryDate() {
        return expiryDate;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public void setHandsetModel(String model) {
        handset.setModel(model);
    }

    public abstract double calcPayment(double flatRate);

    public void setCapLimit(int capLimit) {
        this.capLimit = capLimit;
    }

    public void setExpiryDate(MyDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void mobilePriceRise(double risePercent) {
        handset.priceRise(risePercent);
    }

    public void print() {
        System.out.print("ID:" + id + " Username:" + userName);
        handset.print();
        System.out.print(" InternetQuota: " + internetQuota + " CapLimit:" + capLimit);
        //System.out.println(" Expiry Date: " + expiryDate);
        expiryDate.print();
    }

    public void print(double flatRate) {
        print();
        System.out.print(" Monthly Payment: " + calcPayment(flatRate));
    }

    public String toString() {
        return "ID:" + id + " Username:" + userName + " Handset:" + handset + " InternetQuota: " +
                internetQuota + " CapLimit: " + capLimit + " Expiry Date: " + expiryDate;
    }

    public static void printPlans(ArrayList<MobilePlan> plans) {
        for (MobilePlan plan : plans) {
            plan.print();
        }
    }

    public static void printPlans(ArrayList<MobilePlan> plans, double flatRate) {
        for (MobilePlan plan : plans) {
            plan.print(flatRate);
        }
    }

    public static double calcTotalPayments(ArrayList<MobilePlan> plans, double flatRate) {
        double totalMonthlyPayments = 0;
        for (MobilePlan plan : plans) {
            totalMonthlyPayments += plan.calcPayment(flatRate);
        }
        return totalMonthlyPayments;
    }

    public static void mobilePriceRiseAll(ArrayList<MobilePlan> plans, double risePercent) {
        for (MobilePlan plan : plans) {
            plan.mobilePriceRise(risePercent);
        }
    }

    public static ArrayList<MobilePlan> filterByMobileModel(ArrayList<MobilePlan> plans, String mobileModel) {
        ArrayList<MobilePlan> filteredPlans = new ArrayList<MobilePlan>();
        for (MobilePlan plan : plans) {
            //if(plan.handset.getModel().contains(mobileModel)) // bad way
            if (plan.getHandsetModel().contains(mobileModel)) {
                filteredPlans.add(plan);
            }
        }
        return filteredPlans;
    }

    public static ArrayList<MobilePlan> filterByExpiryDate(ArrayList<MobilePlan> plans, MyDate date) {
        ArrayList<MobilePlan> filteredPlans = new ArrayList<MobilePlan>();
        for (MobilePlan plan : plans) {
            if (plan.expiryDate.isExpired(date)) {
                filteredPlans.add(plan);
            }
        }
        return filteredPlans;
    }

    //Lab 4

    public MobilePlan(MobilePlan MobilePlan) {
        this.userName = MobilePlan.userName;
        this.id = MobilePlan.id;
        this.handset = new MobilePhone(MobilePlan.handset);
        this.internetQuota = MobilePlan.internetQuota;
        this.capLimit = MobilePlan.capLimit;
        this.expiryDate = new MyDate(MobilePlan.expiryDate);
    }

    public MobilePlan clone() throws CloneNotSupportedException {
        MobilePlan output = (MobilePlan) super.clone();
        output.handset = handset.clone();
        output.expiryDate = expiryDate.clone();
        return output;
    }

    public static ArrayList<MobilePlan> shallowCopy(ArrayList<MobilePlan> plans) throws
            CloneNotSupportedException {
        ArrayList<MobilePlan> shallowCopy = new ArrayList<MobilePlan>();
        for (MobilePlan plan : plans) {
            shallowCopy.add(plan);
        }
        return shallowCopy;
    }

    public static ArrayList<MobilePlan> deepCopy(ArrayList<MobilePlan> plans) throws CloneNotSupportedException {
        ArrayList<MobilePlan> deepCopy = new ArrayList<MobilePlan>();
        for (MobilePlan plan : plans) {
            deepCopy.add(plan.clone());
        }
        return deepCopy;
    }

    public int compareTo(MobilePlan otherPlan) {
        return expiryDate.compareTo(otherPlan.expiryDate);
    }

    public abstract void setCity(String city);

    ////Lab 5

    public static void printPlans(HashMap<Integer, MobilePlan> plans) {
        for (MobilePlan plan : plans.values()) {
            plan.print();
        }
    }

    public static void printPlans(HashMap<Integer, MobilePlan> plans, double flatRate) {
        for (MobilePlan plan : plans.values()) {
            plan.print(flatRate);
        }
    }

    public static double calcTotalPayments(HashMap<Integer, MobilePlan> plans, double flatRate) {
        double totalMonthlyPayments = 0;
        for (MobilePlan plan : plans.values()) {
            totalMonthlyPayments += plan.calcPayment(flatRate);
        }
        return totalMonthlyPayments;
    }

    public static void mobilePriceRiseAll(HashMap<Integer, MobilePlan> plans, double risePercent) {
        for (MobilePlan plan : plans.values()) {
            plan.mobilePriceRise(risePercent);
        }
    }

    public static ArrayList<MobilePlan> shallowCopy(HashMap<Integer, MobilePlan> plans) throws
            CloneNotSupportedException {
        ArrayList<MobilePlan> shallowCopy = new ArrayList<MobilePlan>();
        for (MobilePlan plan : plans.values()) {
            shallowCopy.add(plan);
        }
        return shallowCopy;
    }

    public static ArrayList<MobilePlan> deepCopy(HashMap<Integer, MobilePlan> plans) throws
            CloneNotSupportedException {
        ArrayList<MobilePlan> deepCopy = new ArrayList<MobilePlan>();
        for (MobilePlan plan : plans.values()) {
            deepCopy.add(plan.clone());
        }
        return deepCopy;
    }

    public static HashMap<Integer, MobilePlan> shallowCopyHashMap(HashMap<Integer, MobilePlan> plans) throws
            CloneNotSupportedException {
        HashMap<Integer, MobilePlan> shallowCopy = new HashMap<>();
        for (MobilePlan plan : plans.values()) {
            shallowCopy.put(plan.getId(), plan);
        }
        return shallowCopy;
    }

    public static HashMap<Integer, MobilePlan> deepCopyHashMap(HashMap<Integer, MobilePlan> plans) throws
            CloneNotSupportedException {
        HashMap<Integer, MobilePlan> deepCopy = new HashMap<>();
        for (MobilePlan plan : plans.values()) {
            deepCopy.put(plan.getId(), plan.clone());
        }
        return deepCopy;
    }

    //Lab 6
    public static HashMap<Integer, MobilePlan> load(String fileName) {
        HashMap<Integer, MobilePlan> plans = new HashMap<>();
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
        } catch (IOException e) {
            System.err.println("Failed to load the file");
            System.exit(1);
        }
        try {
            while (true) {
                MobilePlan plan = (MobilePlan) inputStream.readObject();
                plans.put(plan.getId(), plan);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Wrong class in the file");
        } catch (EOFException e) {
            System.out.println("No more plans");
        } catch (IOException e) {
            System.err.println("Error in adding object to the file");
            System.exit(1);
        }
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            System.err.println("Error in closing the file");
            System.exit(1);
        }
        return plans;
    }

    public static Boolean save(HashMap<Integer, MobilePlan> plans, String fileName) throws IOException {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
        } catch (IOException ex) {
            System.err.println("error in create/open the file ");
            return false;
        }
        try {
            for (MobilePlan plan : plans.values()) {
                output.writeObject(plan);
            }
        } catch (IOException ex) {
            System.err.println("error in adding the objects to the file ");
            return false;
        }
        try {
            if (output != null)
                output.close();
            return true;
        } catch (IOException ex) {
            System.err.println("error in closing the file ");
//            System.exit(1);
            return false;
        }
    }

    public String toDelimitedString() {
        return id + "," + userName + "," + handset.toDelimitedString() + "," +
                internetQuota + "," + capLimit + "," + expiryDate.toDelimitedString();
    }

    public static MobilePlan readOnePlan(String[] field, int counter) throws IOException, PlanException {
        MobilePlan outputPlan = null;
        String usernamePlan = field[counter];
        int idPlan = Integer.parseInt(field[counter + 1]);
        String modelPlan = field[counter + 3];
        MobileType mobileTypePlan = MobileType.valueOf(field[counter + 4].toUpperCase());
        int memorySizePlan = Integer.parseInt(field[counter + 5]);
        double pricePlan = Double.parseDouble(field[counter + 6]);
        int internetQuotaPlan = Integer.parseInt(field[counter + 7]);
        int capLimitPlan = Integer.parseInt(field[counter + 8]);
        int dayPlan = Integer.parseInt(field[counter + 9]);
        int monthPlan = Integer.parseInt(field[counter + 10]);
        int yearPlan = Integer.parseInt(field[counter + 11]);
        MobilePhone handsetPlan = new MobilePhone(modelPlan, mobileTypePlan, memorySizePlan, pricePlan);
        MyDate datePlan = new MyDate(dayPlan, monthPlan, yearPlan);
        switch (field[counter]) {
            case "PP":
                String cityPlan = field[counter + 12];
                outputPlan = new PersonalPlan(usernamePlan, idPlan, handsetPlan, internetQuotaPlan, capLimitPlan, datePlan, cityPlan);
                break;
            case "BP":
                int numberOfEmployeesPlan = Integer.parseInt(field[counter + 12]);
                int ABN = Integer.parseInt(field[counter + 13]);
                outputPlan = new BusinessPlan(usernamePlan, idPlan, handsetPlan, internetQuotaPlan, capLimitPlan, datePlan, numberOfEmployeesPlan, ABN);
                break;
        }
        return outputPlan;
    }

    public static HashMap<Integer, MobilePlan> loadTextFile(String fileName) throws IOException, PlanException {
        HashMap<Integer, MobilePlan> plansHashMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            line = line.trim();
            String[] field = line.split(",");
            MobilePlan plan = MobilePlan.readOnePlan(field, 0);
            plansHashMap.put(plan.getId(), plan);
            line = reader.readLine();
        }
        reader.close();
        return plansHashMap;
    }

    public static Boolean saveTextFile(HashMap<Integer, MobilePlan> plans, String fileName) throws IOException {
        BufferedWriter write = new BufferedWriter((new FileWriter(fileName)));
        for (MobilePlan plan : plans.values()) {
            write.write(plan.toDelimitedString() + "\n");
        }
        write.close();
        return true;
    }
}

