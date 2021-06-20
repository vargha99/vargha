import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
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
import java.util.ArrayList;

public class User implements Cloneable, Comparable<User>, Serializable{
    private String name;
    // private String username; not in the spec
    private int userID;
    private String password;
    private Address address;
    //    private ArrayList<MobilePlan> plans;
    private HashMap<Integer, MobilePlan> plans;
    private static int countID = 2000; //base id to be 2000

    public User(int userID, String name, Address address, String password) {
        this.name = name;
        this.userID = userID;
        this.address = address;
        this.password = password;
//        this.plans = new ArrayList<MobilePlan>();
        this.plans = new HashMap<>();
    }

    public User(String name, Address address, String password) {
        this.name = name;
        this.userID = countID++; //automatic ID generation
        this.address = address;
        this.password = password;
//        this.plans = new ArrayList<MobilePlan>();
        this.plans = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return userID;
    }

    public String getCity() {
        return address.getCity();
    }

    public void setCity(String city) {
        address.setCity(city);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getUserID() {
        return userID;
    }

    public Address getAddress() {
        return address;
    }

    public HashMap<Integer, MobilePlan> getPlans() {
        return plans;
    }

    public boolean validateUser(int userID, String password) // and not username as username might not be unique as we didn't check it
    {
        if ((this.userID == userID) && this.password.equals(password)) {
            return true;
        }
        return false;
    }

//    public MobilePlan findPlan(int planID) {
//        for (MobilePlan plan : plans) {
//            if (plan.getId() == planID) {
//                return plan;
//            }
//        }
//        return null;
//    }
//
//    public boolean addPlan(MobilePlan plan) {
//        if (findPlan(plan.getId()) == null) {
//            plans.add(plan);
//            return true;
//        }
//        return false;
//    }

//    public double calcTotalPayments(double flatRate) {
//        return MobilePlan.calcTotalPayments(plans, flatRate);
//    }
//
//    public void mobilePriceRiseAll(double risePercent) {
//        MobilePlan.mobilePriceRiseAll(plans, risePercent);
//    }

    public boolean createPersonalPlan(String username, int id, MobilePhone mobilePhone, int internetQuota, int capLimit, MyDate expiryDate, String city) throws PlanException {
//        try {
        return addPlan(new PersonalPlan(username, id, mobilePhone, internetQuota, capLimit, expiryDate, city));
//        } catch (PlanException e) {
//            System.out.println(e);
//            return addPlan(new PersonalPlan(username, PlanException.getNewID(), mobilePhone, internetQuota, capLimit, expiryDate, city));
//        }
    }

    public boolean createBusinessPlan(String username, int id, MobilePhone mobilePhone, int internetQuota, int capLimit, MyDate expiryDate, int numberOfEmployees, int ABN) throws PlanException {
//        try {
        return addPlan(new BusinessPlan(username, id, mobilePhone, internetQuota, capLimit, expiryDate, numberOfEmployees, ABN));
//        } catch (PlanException e) {
//            System.out.println(e);
//            return addPlan(new BusinessPlan(username, PlanException.getNewID(), mobilePhone, internetQuota, capLimit, expiryDate, numberOfEmployees, ABN));
//        }
    }

    public ArrayList<MobilePlan> filterByMobileModel(String mobileModel) {
        return MobilePlan.filterByMobileModel((ArrayList<MobilePlan>) plans.values(), mobileModel);
    }

    public ArrayList<MobilePlan> filterByExpiryDate(MyDate date) {
        return MobilePlan.filterByExpiryDate((ArrayList<MobilePlan>) plans.values(), date);
    }

    public void printUserInformation() {
        System.out.println("User ID:" + userID + " Name:" + name);
        System.out.println(" Address: ");
        address.print();
    }

    public void print() {
        // System.out.println("User ID:" + userID + " Name:" + name);
        // System.out.println(" Address: ");
        // address.print();
        printUserInformation(); //instead of previous 3 lines
        MobilePlan.printPlans(plans);
    }

//    public void print(double flatRate) {
//        // System.out.println("User ID:" + userID + " Name:" + name);
//        // System.out.println(" Address: ");
//        // address.print();
//        printUserInformation(); //instead of previous 3 lines
//        MobilePlan.printPlans(plans, flatRate);
//    }
//
//    public String toString() {
//        String output = "User ID:" + userID + " Name:" + name + " Address: " + address.toString() + " Plans: \n";
//        for (MobilePlan plan : plans.values()) {
//            output += plan.toString() + "\n";
//        }
//        return output;
//    }

    public void printPlans(double flatRate) {
        /*System.out.println("List of Plans:");
        for(MobilePlan plan : plans)
        {
            System.out.println(plan);
            System.out.println("Monthly Payment ="+ plan.calcPayment(flatRate));
        }*/
        MobilePlan.printPlans(plans, flatRate);
        System.out.println("Total Monthly Payments:" + calcTotalPayments(flatRate));
    }

    public ArrayList<String> populateDistinctMobileModels() {
        ArrayList<String> models = new ArrayList<String>();
        for (MobilePlan plan : plans.values()) {
            boolean found = false;
            for (String model : models) {
                if (plan.getHandsetModel().equals(model)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                models.add(plan.getHandsetModel());
        }
        return models;
    }

    public int getTotalCountForMobileModel(String mobileModel) {
        int count = 0;
        for (MobilePlan plan : plans.values()) {
            if (plan.getHandsetModel().equals(mobileModel))
                count++;
        }
        return count;
    }

    public double getTotalPaymentForMobileModel(String mobileModel, double flatRate) {
        double total = 0;
        for (MobilePlan plan : plans.values()) {
            if (plan.getHandsetModel().equals(mobileModel))
                total += plan.calcPayment(flatRate);
        }
        return total;
    }

    public ArrayList<Integer> getTotalCountPerMobileModel(ArrayList<String> mobileModels) {
        ArrayList<Integer> totalCounts = new ArrayList<Integer>();
//            int count=0;
        for (String model : mobileModels) {
            //assuming that the other method is not done. This code was correct:
//                count=0;
//                for( MobilePlan plan:plans)
//                {
//                    if(plan.getHandsetModel().equals(model))
//                        count++;
//                }
//                totalCounts.add(count);

            //a better way by using the other method
            totalCounts.add(getTotalCountForMobileModel(model));
        }
        return totalCounts;
    }

    ArrayList<Double> getTotalPaymentPerMobileModel(ArrayList<String> mobileModels, double flatRate) {
        ArrayList<Double> totalPayments = new ArrayList<Double>();
//            double payment=0;
        for (String model : mobileModels) {
            //assuming that the other method is not done. The folowwing code should have been done
//            payment=0;
//            for( MobilePlan plan:plans)
//            {
//                if(plan.getHandsetModel().equals(model))
//                    payment+=plan.calcPayment(flatRate);
//            }
//            totalPayments.add(payment);

            //a better way now by using the other method
            totalPayments.add(getTotalPaymentForMobileModel(model, flatRate));
        }
        return totalPayments;
    }

    public void reportPaymentsPerMobileModel
            (ArrayList<String> mobileModels, ArrayList<Integer> counts, ArrayList<Double> monthlyPayments) {
        System.out.println("\n MobileModel \t \t \t Total Monthly Payments \t \t \tAverage Monthly Payment");
        for (int i = 0; i < counts.size(); i++)
            System.out.println(mobileModels.get(i) + " \t \t \t " + monthlyPayments.get(i) + " \t \t \t " + monthlyPayments.get(i) / (double) counts.get(i));
    }

    public void reportPaymentsPerMobileModel(ArrayList<String> mobileModels, double flatRate) {
        ArrayList<Integer> counts = getTotalCountPerMobileModel(mobileModels);
        ArrayList<Double> monthlyPayments = getTotalPaymentPerMobileModel(mobileModels, flatRate);
        reportPaymentsPerMobileModel(mobileModels, counts, monthlyPayments);
        System.out.println("\n MobileModel \t \t Total Monthly Payments \t \t Average Monthly Payment");
        for (int i = 0; i < counts.size(); i++)
            System.out.println(mobileModels.get(i) + " \t \t " + monthlyPayments.get(i) + " \t \t " + monthlyPayments.get(i) / (double) counts.get(i));
    }

    public void reportPaymentsPerMobileModel(double flatRate) {
        ArrayList<String> mobileModels = populateDistinctMobileModels();
        reportPaymentsPerMobileModel(mobileModels, flatRate); // better than below
//        ArrayList<String> mobileModels=populateDistinctMobileModels();
//        ArrayList<Integer> counts=getTotalCountPerMobileModel(mobileModels);
//        ArrayList<Double> monthlyPayments=getTotalPaymentPerMobileModel(mobileModels, flatRate);
//        reportPaymentsPerMobileModel (mobileModels, counts, monthlyPayments); // instead of doing this again as below
//            System.out.println("\n MobileModel \t \t Total Monthly Payments \t \t Average Monthly Payment");
//            for (int i=0;i<counts.size();i++)
//                System.out.println(mobileModels.get(i)+" \t \t "+monthlyPayments.get(i)+" \t \t "+monthlyPayments.get(i)/(double)counts.get(i));
    }

    ////////Lab 4
//    public User(User user) {
//        this.address = new Address(user.address);
//        this.userID = user.userID;
//        this.password = user.password;
//        this.name = user.name;
//        this.plans = new ArrayList<>();
//        for (MobilePlan plan : user.plans) {
//            if (plan instanceof PersonalPlan) {
//                plans.add(new PersonalPlan((PersonalPlan) plan));
//            } else {
//                plans.add(new BusinessPlan((BusinessPlan) plan));
//            }
//        }
//    }
//
//    public User clone() throws CloneNotSupportedException {
//        User output = (User) super.clone();
//        output.address = address.clone();
//        output.plans = deepCopyPlans();
//        return output;
//    }

    public static ArrayList<User> shallowCopy(ArrayList<User> users) throws CloneNotSupportedException {
        ArrayList<User> shallowCopy = new ArrayList<>();
        for (User user : users) {
            shallowCopy.add(user);
        }
        return shallowCopy;
    }

    public static ArrayList<User> deepCopy(ArrayList<User> users) throws CloneNotSupportedException {
        ArrayList<User> deepCopy = new ArrayList<>();
        for (User user : users) {
            deepCopy.add(user.clone());
        }
        return deepCopy;
    }

    public ArrayList<MobilePlan> shallowCopyPlans() throws CloneNotSupportedException {
        return MobilePlan.shallowCopy(plans);
    }

    public ArrayList<MobilePlan> deepCopyPlans() throws CloneNotSupportedException {
        return MobilePlan.deepCopy(plans);
    }

//    public int compareTo(User otherUser) {
//        return this.address.compareTo(otherUser.address);
//    }

//    public int compareTo1(User otherUser) {
//        int flatRate = 200;
//        return Double.compare(calcTotalPayments(flatRate), otherUser.calcTotalPayments(flatRate));
//    }

//    public ArrayList<MobilePlan> sortPlansByDate() {
//        Collections.sort(plans);
//        return plans;
//    }

    public void setCityForPersonalPlan(String city, int planID) {
        MobilePlan plan = findPlan(planID);
        if (plan != null) {
            PersonalPlan personalPlan = (PersonalPlan) plan;
            personalPlan.setCity(city);
        } else {
            System.out.println("Plan Not Found!");
        }
    }

    public void setCapLimitForPlan(int capLimit, int planID) {
        MobilePlan plan = findPlan(planID);
        if (plan != null) {
            plan.setCapLimit(capLimit);
        } else {
            System.out.println("Plan Not Found!");
        }
    }

    public void setMobileModelForPlan(String mobileModel, int planID) {
        MobilePlan plan = findPlan(planID);
        if (plan != null) {
            plan.setHandsetModel(mobileModel);
        } else {
            System.out.println("Plan Not Found!");
        }
    }

    public void setExpiryDateForPlan(MyDate newDate, int planID) {
        MobilePlan plan = findPlan(planID);
        if (plan != null) {
            plan.setExpiryDate(newDate);
        } else {
            System.out.println("Plan Not Found!");
        }
    }

    /////Lab 5
    public MobilePlan findPlan(int planID) {
        return plans.get(planID);
    }

    public boolean addPlan(MobilePlan plan) {
        if (findPlan(plan.getId()) == null) {
            plans.put(plan.getId(), plan);
            return true;
        }
        return false;
    }

    public void print(double flatRate) {
        // System.out.println("User ID:" + userID + " Name:" + name);
        // System.out.println(" Address: ");
        // address.print();
        printUserInformation(); //instead of previous 3 lines
        MobilePlan.printPlans(plans, flatRate);
    }

    public double calcTotalPayments(double flatRate) {
        return MobilePlan.calcTotalPayments(plans, flatRate);
    }

    public void mobilePriceRiseAll(double risePercent) {
        MobilePlan.mobilePriceRiseAll(plans, risePercent);
    }

    public User(User user) {
        this.address = new Address(user.address);
        this.userID = user.userID;
        this.password = user.password;
        this.name = user.name;
        this.plans = new HashMap<>();
        for (MobilePlan plan : user.plans.values()) {
            if (plan instanceof PersonalPlan) {
                plans.put(plan.getId(), new PersonalPlan((PersonalPlan) plan));
            } else {
                plans.put(plan.getId(), new BusinessPlan((BusinessPlan) plan));
            }
        }
    }

    public User clone() throws CloneNotSupportedException {
        User output = (User) super.clone();
        output.address = address.clone();
        output.plans = deepCopyPlansHashMap();
        return output;
    }

    public String toString() {
        String output = "User ID:" + userID + " Name:" + name + " Address: " + address.toString() + " Plans: \n";
        for (MobilePlan plan : plans.values()) {
            output += plan.toString() + "\n";
        }
        return output;
    }

    public static ArrayList<User> shallowCopy(HashMap<Integer, User> users) throws CloneNotSupportedException {
        ArrayList<User> shallowCopy = new ArrayList<>();
        for (User user : users.values()) {
            shallowCopy.add(user);
        }
        return shallowCopy;
    }

    public static ArrayList<User> deepCopy(HashMap<Integer, User> users) throws CloneNotSupportedException {
        ArrayList<User> deepCopy = new ArrayList<>();
        for (User user : users.values()) {
            deepCopy.add(user.clone());
        }
        return deepCopy;
    }

    public HashMap<Integer, MobilePlan> shallowCopyPlansHashMap() throws CloneNotSupportedException {
        return MobilePlan.shallowCopyHashMap(plans);
    }

    public HashMap<Integer, MobilePlan> deepCopyPlansHashMap() throws CloneNotSupportedException {
        return MobilePlan.deepCopyHashMap(plans);
    }

    public static HashMap<Integer, User> shallowCopyHashMap(HashMap<Integer, User> users) throws
            CloneNotSupportedException {
        HashMap<Integer, User> shallowCopy = new HashMap<>();
        for (User user : users.values()) {
            shallowCopy.put(user.getUserID(), user);
        }
        return shallowCopy;
    }

    public static HashMap<Integer, User> deepCopyHashmap(HashMap<Integer, User> users) throws
            CloneNotSupportedException {
        HashMap<Integer, User> deepCopy = new HashMap<>();
        for (User user : users.values()) {
            deepCopy.put(user.getUserID(), user.clone());
        }
        return deepCopy;
    }

//    public ArrayList<MobilePlan> sortPlansByDate() {
//        ArrayList<MobilePlan> plansArrayList = new ArrayList<>(plans.values());
//        Collections.sort(plansArrayList);
//        return plansArrayList;
//    }

    public HashMap<String, Integer> getTotalCountForMobileModel() {
        HashMap<String, Integer> totalCountForMobileModel = new HashMap<String, Integer>();
        for (MobilePlan plan : plans.values()) {
            Integer count = totalCountForMobileModel.get(plan.getHandsetModel());
            if (count != null) {
                totalCountForMobileModel.put(plan.getHandsetModel(), count + 1);
            } else {
                totalCountForMobileModel.put(plan.getHandsetModel(), 1);
            }
        }
        return totalCountForMobileModel;
    }

    public HashMap<String, Double> getTotalPaymentForMobileModel(double flatRate) {
        HashMap<String, Double> totalPaymentForMobileModel = new HashMap<>();
        for (MobilePlan plan : plans.values()) {
            Double payment = totalPaymentForMobileModel.get(plan.getHandsetModel());
            if (payment != null) {
                totalPaymentForMobileModel.put(plan.getHandsetModel(), payment + plan.calcPayment(flatRate));
            } else {
                totalPaymentForMobileModel.put(plan.getHandsetModel(), plan.calcPayment(flatRate));
            }
        }
        return totalPaymentForMobileModel;
    }

    public void reportForMobileModels(HashMap<String, Double> payments, HashMap<String, Integer> counts) {
        System.out.println();
        System.out.println("\n Mobile Model \t \t Total Monthly Payment \t \t Average Monthly Payment");
        for (String mobileModel : payments.keySet()) {
            System.out.println(mobileModel + "\t \t " + payments.get(mobileModel) + " \t \t " + payments.get(mobileModel) / (double) counts.get(mobileModel));
        }
    }

    public static void printPlans(HashMap<Integer, MobilePlan> plans) {
        for (MobilePlan plan : plans.values()) {
            System.out.println(plan);
        }
    }

    public static void printUsers(HashMap<Integer, User> users) {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    //Lab 6
    public static Boolean save(HashMap<Integer, User> users, String fileName) throws IOException {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("File could not be created/found");
            //System.exit(1);
            return false;
        }
        try {
            for (User user : users.values()) {
                outputStream.writeObject(user);
            }
        } catch (IOException e) {
            System.err.println("Error in adding the object to the file");
            //System.exit(1);
            return false;
        }
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error in closing the file");
            //System.exit(1);
            return false;
        }
    }

    public static HashMap<Integer, User> load(String fileName) throws IOException {
        HashMap<Integer, User> users = new HashMap<>();
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
        } catch (IOException e) {
            System.err.println("Failed to load the file");
            System.exit(1);
        }
        try {
            while (true) {
                User user = (User) inputStream.readObject();
                users.put(user.getUserID(), user);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Wrong class in the file");
        } catch (EOFException e) {
            System.out.println("No more Users");
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
        return users;
    }

    public String toDelimitedString() {
        String output = name + "," + userID + "," + password + "," + address.toDelimitedString() + "," + plans.size();
        for (MobilePlan plan : plans.values()) {
            output = output + "," + plan.toDelimitedString();
        }
        return output;
    }

    public static HashMap<Integer, User> loadTextFile(String fileName) throws IOException, PlanException {
        HashMap<Integer, User> usersHashMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            line = line.trim();
            String[] field = line.split(",");
            User user = readOneUser(field, 0);
            usersHashMap.put(user.getUserID(), user);
            line = reader.readLine();
        }
        reader.close();
        return usersHashMap;
    }

    public static Boolean saveTextFile(HashMap<Integer, User> users, String fileName) throws IOException, PlanException {
        BufferedWriter write = new BufferedWriter((new FileWriter(fileName)));
        for (User user : users.values()) {
            write.write(user.toDelimitedString() + "\n");
        }
        write.close();
        return true;
    }

    public static User readOneUser(String[] field, int counter) throws PlanException, IOException {
        String name = field[counter];
        int userID = Integer.parseInt(field[counter + 1]);
        String password = field[counter + 2];
        int streetNumber = Integer.parseInt(field[counter + 3]);
        String streetName = field[counter + 4];
        String suburb = field[counter + 5];
        String city = field[counter + 6];
        int plansCount = Integer.parseInt(field[counter + 7]);
        counter += 8;
        Address address = new Address(streetNumber, streetName, suburb, city);
        User user = new User(userID, name, address, password);
        for (int i = 0; i < plansCount; i++) {
            MobilePlan plan = MobilePlan.readOnePlan(field, counter);
            user.addPlan(plan);
            if (plan instanceof PersonalPlan) {
                counter += 13;
            } else if (plan instanceof BusinessPlan) {
                counter += 14;
            }
        }
        return user;
    }

    /////////////////////////////////////////////////////////Assignment 2/////////////////////////////////////////////////////////

    public int compareTo1(User otherUser) {
        int flatRate = 200;
        return Double.compare(calcTotalPayments(flatRate), otherUser.calcTotalPayments(flatRate));
    }

}