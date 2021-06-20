import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MobileCompany implements Cloneable, Serializable{
    private String name;
    private String adminUsername;
    private String adminPassword;
    private double flatRate;
    private HashMap<Integer, User> users;
//    private ArrayList<User> users;

    MobileCompany(String name, String adminUsername, String adminPassword, double flatRate) {
        this.name = name;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.flatRate = flatRate;
//        this.users = new ArrayList<User>();
        this.users = new HashMap<>();
    }

    public double getFlatRate() {
        return flatRate;
    }

    public boolean validateAdmin(String username, String password) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public User validateUser(int userID, String password) {

//        for(User user : users) // a bad way
//        {
//            if(user.validateUser(userID, password))
//            {
//                return user;
//            }
//        }
//        return null;

        // Correct way by using findUser
        User user = findUser(userID);
        if ((user != null) && user.validateUser(userID, password)) {
            return user;
        } else
            return null;
    }

//    public User findUser(int userID) {
//        for (User user : users) {
//            if (user.getUserID() == userID) {
//                return user;
//            }
//        }
//        return null;
//    }


//
//    public boolean addUser(User user) {
//        if (findUser(user.getUserID()) == null) {
//            users.add(user);
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean addUser(String name, int userID, Address address, String password) {
        User user = new User(userID, name, address, password);
        return addUser(user);
    }

    public boolean addUser(String name, Address address, String password) //automatic ID generation
    {
        User user = new User(name, address, password); // user constructor to generate ID automatically
        return addUser(user);
    }

    public boolean addPlan(int userID, MobilePlan plan) {
        User user = findUser(userID);
        if (user != null) {
            return user.addPlan(plan);
        }
        return false;
    }

    public MobilePlan findPlan(int userID, int planID) {
        User user = findUser(userID);
        if (user != null) {
            return user.findPlan(planID);
        } else
            return null;
    }

    public void printPlans(int userID) {
        User user = findUser(userID);
        if (user != null) {
            user.printPlans(flatRate);
        }
    }

    public void printUser(int userID) //added for UI
    {
        User user = findUser(userID);
        if (user != null) {
            user.print(flatRate);
        }
    }

    public boolean createPersonalPlan(int userID, String username, int id, MobilePhone mobilePhone, int internetQuota, int caplimit, MyDate expiryDate, String city) throws PlanException {
        User user = findUser(userID);
        if (user != null) {
            return user.createPersonalPlan(username, id, mobilePhone, internetQuota, caplimit, expiryDate, city);
        }
        return false;
    }

    public boolean createBusinessPlan(int userID, String username, int id, MobilePhone mobilePhone, int internetQuota, int caplimit, MyDate expiryDate, int numberOfEmployees, int ABN) throws PlanException {
        User user = findUser(userID);
        if (user != null) {
            return user.createBusinessPlan(username, id, mobilePhone, internetQuota, caplimit, expiryDate, numberOfEmployees, ABN);
        }
        return false;
    }

    public double calcTotalPayments(int userID) {
        User user = findUser(userID);
        if (user != null) {
            return user.calcTotalPayments(flatRate);
        }
        return 0;
    }

    public double calcTotalPayments() {
        double totalPayment = 0;
        for (User user : users.values()) {
            totalPayment += user.calcTotalPayments(flatRate);
        }
        return totalPayment;
    }

    public boolean mobilePriceRise(int userID, double risePercent) {
        User user = findUser(userID);
        if (user != null) {
            user.mobilePriceRiseAll(risePercent);
        }
        return false;
    }

    public void mobilePriceRise(double risePercent) {
        for (User user : users.values()) {
            user.mobilePriceRiseAll(risePercent);
        }
    }

    public ArrayList<MobilePlan> allPlans() {
        ArrayList<MobilePlan> allUserPlans = new ArrayList<MobilePlan>();
        for (User user : users.values()) {
            for (MobilePlan plan : user.getPlans().values()) {
                allUserPlans.add(plan); // you are not allowded to user addAll to undestand the algorithm better
            }
        }
        return allUserPlans;
    }

    public ArrayList<MobilePlan> filterByMobileModel(int userID, String mobileModel) {
        User user = findUser(userID);
        if (user != null) {
            return user.filterByMobileModel(mobileModel);
        }
        return new ArrayList<>();
    }

    public ArrayList<MobilePlan> filterByExpiryDate(int userID, MyDate date) {
        User user = findUser(userID);
        if (user != null) {
            return user.filterByExpiryDate(date);
        }
        return new ArrayList<>();
    }

    public ArrayList<MobilePlan> filterByMobileModel(String mobileModel) {
        ArrayList<MobilePlan> filteredPlans = new ArrayList<MobilePlan>();
        for (User user : users.values()) {
            ArrayList<MobilePlan> userFilteredPlans = user.filterByMobileModel(mobileModel);
            for (MobilePlan plan : userFilteredPlans) {
                filteredPlans.add(plan);
            }
        }
        return filteredPlans;
    }

    public ArrayList<MobilePlan> filterByExpiryDate(MyDate date) {
        ArrayList<MobilePlan> filteredPlans = new ArrayList<>();
        for (User user : users.values()) {
            ArrayList<MobilePlan> userFilteredPlans = user.filterByExpiryDate(date);
            for (MobilePlan plan : userFilteredPlans) {
                filteredPlans.add(plan);
            }
        }
        return filteredPlans;
    }

    public void print() {
        System.out.println("Company name: " + name + " Flat Rate: " + flatRate);
        for (User user : users.values()) {
            //user.print(); // WRONG not based on the spec

            // user.printUserInformation() ; // correct but duplicate
            // user.printPlans(flatRate);

            user.print(flatRate); // add this to user and make it better than previous 2 lines
        }
    }

    public String toString() {
        String printString = "Company name: " + name + " Flat Rate: " + flatRate + "\n";
        for (User user : users.values()) {
            printString += user.toString() + "\n";
        }
        return printString;
    }

    public ArrayList<String> populateDistinctCityNames() {
        ArrayList<String> cities = new ArrayList<String>();
        for (User user : users.values()) {
            boolean found = false;
            for (String city : cities) {
                if (user.getCity().equals(city)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                cities.add(user.getCity());
        }
        return cities;
    }

    public double getTotalPaymentForCity(String city) {
        double totalPaymentForCity = 0;
        for (User user : users.values()) {
            if (user.getCity().equals(city)) {
                totalPaymentForCity += user.calcTotalPayments(flatRate);
            }
        }
        return totalPaymentForCity;
    }

    public ArrayList<Double> getTotalPaymentPerCity(ArrayList<String> cities) {
        ArrayList<Double> totalPerCity = new ArrayList<>();
        for (String city : cities) {
            totalPerCity.add(getTotalPaymentForCity(city));
        }
        return totalPerCity;
    }

    public void reportPaymentPerCity(ArrayList<String> cities, ArrayList<Double> payments) // it is in the spec but not good
    {
        String format = "%1$-20s%2$-20s\n";
        System.out.format(format, "City Name", "Total Monthly Payment");
        for (int i = 0; i < cities.size(); i++) {
            System.out.format(format, cities.get(i), payments.get(i));
        }
    }

    public void reportPaymentPerCity(ArrayList<String> cities) // for a list of given cities
    {
        ArrayList<Double> payments = getTotalPaymentPerCity(cities);
        String format = "%1$-20s%2$-20s\n";
        System.out.format(format, "City Name", "Total Monthly Payment");
        for (int i = 0; i < cities.size(); i++) {
            System.out.format(format, cities.get(i), payments.get(i));
        }
    }

    public void reportPaymentPerCity() // for all cities
    {
        ArrayList<String> cities = populateDistinctCityNames();
        ArrayList<Double> payments = getTotalPaymentPerCity(cities);
        String format = "%1$-20s%2$-20s\n";
        System.out.format(format, "City Name", "Total Monthly Payment");
        for (int i = 0; i < cities.size(); i++) {
            System.out.format(format, cities.get(i), payments.get(i));
        }
    }

    public ArrayList<String> populateDistinctMobileModels() {
        ArrayList<String> allModels = new ArrayList<String>();
        for (User user : users.values()) {
            ArrayList<String> userModels = user.populateDistinctMobileModels();
            for (String userModel : userModels) {
                boolean found = false;
                for (String model : allModels) {
                    if (model.equals(userModel)) {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    allModels.add(userModel);
            }
        }
        return allModels;
    }

//    public ArrayList<Integer> getTotalCountPerMobileModel (ArrayList<String> mobileModels)
//    {
//        ArrayList<Integer> totalCounts=new ArrayList<Integer>();
//        int count=0;
//        for (String model:mobileModels)
//        {
//            count=0;
//            for (User user:users)
//            {
//                count+=user.getTotalCountForMobileModel(model); // by calling this method which is not the same as spec
//            }
//            totalCounts.add(count);
//        }
//        return totalCounts;
//    }

    public ArrayList<Integer> getTotalCountPerMobileModel(ArrayList<String> mobileModels) {
        ArrayList<Integer> totalCounts = new ArrayList<Integer>();
        for (int i = 0; i < mobileModels.size(); i++) {
            totalCounts.add(0);// initial values with 0
        }

        for (User user : users.values()) {
            ArrayList<Integer> userCounts = user.getTotalCountPerMobileModel(mobileModels);
            for (int i = 0; i < userCounts.size(); i++) {
                totalCounts.set(i, totalCounts.get(i) + userCounts.get(i));//for each element of total add the user count
            }
        }
        return totalCounts;
    }

    public ArrayList<Double> getTotalPaymentPerMobileModel(ArrayList<String> mobileModels) {
        ArrayList<Double> totalPayments = new ArrayList<Double>();
        for (int i = 0; i < mobileModels.size(); i++) {
            totalPayments.add(0.0);// initial values with 0
        }

        for (User user : users.values()) {
            ArrayList<Double> userTotalPayments = user.getTotalPaymentPerMobileModel(mobileModels, flatRate);
            for (int i = 0; i < userTotalPayments.size(); i++) {
                totalPayments.set(i, totalPayments.get(i) + userTotalPayments.get(i));
            }
        }
        return totalPayments;
    }

    //as spec but it is not good. all lists are sent as parameters
    public void reportPaymentsPerMobileModel(ArrayList<String> mobileModels, ArrayList<Integer> counts, ArrayList<Double> monthlyPayments) {
        System.out.println("\n MobileModel \t \t \t Total Monthly Payments \t \t \t Average Monthly Payment");
        for (int i = 0; i < counts.size(); i++)
            System.out.println(mobileModels.get(i) + " \t \t \t " + monthlyPayments.get(i) + " \t \t \t " + monthlyPayments.get(i) / (double) counts.get(i));
    }

    //a list of given models 
    public void reportPaymentsPerMobileModel(ArrayList<String> mobileModels) {
        ArrayList<Integer> counts = getTotalCountPerMobileModel(mobileModels);
        ArrayList<Double> monthlyPayments = getTotalPaymentPerMobileModel(mobileModels);
        reportPaymentsPerMobileModel(mobileModels, counts, monthlyPayments);
    }

    public void reportPaymentsPerMobileModel() {
        ArrayList<String> mobileModels = populateDistinctMobileModels();
        reportPaymentsPerMobileModel(mobileModels);
    }

    //Lab 4

//    public MobileCompany(MobileCompany mobileCompany) {
//        this.name = mobileCompany.name;
//        this.adminUsername = mobileCompany.adminUsername;
//        this.adminPassword = mobileCompany.adminPassword;
//        this.flatRate = mobileCompany.flatRate;
////        this.users = new ArrayList<>();
//        this.users = new HashMap<>();
//        for (User user : mobileCompany.users) {
//            users.add(new User(user));
//        }
//    }

    public static void printUsers(ArrayList<User> users) {
        for (User user : users) {
            user.print();
        }
    }

//    @Override
//    public MobileCompany clone() throws CloneNotSupportedException {
//        MobileCompany output = (MobileCompany) super.clone();
//        output.users = deepCopyUsers();
//        return output;
//    }

    public ArrayList<User> shallowCopyUsers() throws CloneNotSupportedException {
        return User.shallowCopy(users);
    }

    public ArrayList<User> deepCopyUsers() throws CloneNotSupportedException {
        return User.deepCopy(users);
    }

//    public ArrayList<User> sortUsers() {
//        Collections.sort(users);
//        return users;
//    }

    //Lab5

    public MobileCompany(MobileCompany mobileCompany) {
        this.name = mobileCompany.name;
        this.adminUsername = mobileCompany.adminUsername;
        this.adminPassword = mobileCompany.adminPassword;
        this.flatRate = mobileCompany.flatRate;
        this.users = new HashMap<>();
        for (User user : mobileCompany.users.values()) {
            users.put(user.getUserID(), new User(user));
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public MobileCompany clone() throws CloneNotSupportedException {
        MobileCompany output = (MobileCompany) super.clone();
        output.users = deepCopyUsersHashMap();
        return output;
    }

    public User findUser(int userID) {
        return users.get(userID);
    }

    public boolean addUser(User user) {
        if (findUser(user.getUserID()) == null) {
            users.put(user.getID(), user);
            return true;
        } else {
            return false;
        }
    }

    public HashMap<Integer, User> shallowCopyUsersHashMap() throws CloneNotSupportedException {
        return User.shallowCopyHashMap(users);
    }

    public HashMap<Integer, User> deepCopyUsersHashMap() throws CloneNotSupportedException {
        return User.deepCopyHashmap(users);
    }

    public ArrayList<User> sortUsers() {
        ArrayList<User> usersArrayList = new ArrayList<>(users.values());
        Collections.sort(usersArrayList);
        return usersArrayList;
    }

    public HashMap<Integer, MobilePlan> allPlansHashMap() {
        HashMap<Integer, MobilePlan> allUsersPlans = new HashMap<>();
        for (User user : users.values()) {
            for (MobilePlan plan : user.getPlans().values()) {
                allUsersPlans.put(plan.id, plan);
            }
        }
        return allUsersPlans;
    }

    compareTo


    public HashMap<String, Double> getTotalPaymentForCity(double flatRate) {
        HashMap<String, Double> totalPaymentForCity = new HashMap<String, Double>();
        for (User user : users.values()) {
            Double payment = totalPaymentForCity.get(user.getCity());
            if (payment != null) {
                totalPaymentForCity.put(user.getCity(), payment + user.calcTotalPayments(flatRate));
            } else {
                totalPaymentForCity.put(user.getCity(), user.calcTotalPayments(flatRate));
            }
        }
        return totalPaymentForCity;
    }

    public HashMap<String, Integer> getTotalCountForCity() {
        HashMap<String, Integer> totalCountForCity = new HashMap<String, Integer>();
        for (User user : users.values()) {
            Integer count = totalCountForCity.get(user.getCity());
            if (count != null) {
                totalCountForCity.put(user.getCity(), count + 1);
            } else {
                totalCountForCity.put(user.getCity(), 1);
            }
        }
        return totalCountForCity;
    }

    public HashMap<String, Integer> getTotalCountForMobileModel() {
        HashMap<String, Integer> totalCountForMobileModel = new HashMap<>();
        for (User user : users.values()) {
            HashMap<String, Integer> countForMobileModel = user.getTotalCountForMobileModel();
            for (String mobileModel : countForMobileModel.keySet()) {
                Integer counts = totalCountForMobileModel.get(mobileModel);
                Integer countPerModel = countForMobileModel.get(mobileModel);
                if (counts != null) {
                    totalCountForMobileModel.put(mobileModel, countPerModel + counts);
                } else {
                    totalCountForMobileModel.put(mobileModel, countPerModel);
                }
            }
        }
        return totalCountForMobileModel;
    }

    public HashMap<String, Double> getTotalPaymentsForMobileModel(double flatRate) {
        HashMap<String, Double> totalPaymentsForMobileModel = new HashMap<>();
        for (User user : users.values()) {
            HashMap<String, Double> paymentForMobileModel = user.getTotalPaymentForMobileModel(flatRate);
            for (String mobileModel : paymentForMobileModel.keySet()) {
                Double payments = totalPaymentsForMobileModel.get(mobileModel);
                if (payments != null) {
                    totalPaymentsForMobileModel.put(mobileModel, payments + paymentForMobileModel.get(mobileModel));
                } else {
                    totalPaymentsForMobileModel.put(mobileModel, paymentForMobileModel.get(mobileModel));
                }
            }
        }
        return totalPaymentsForMobileModel;
    }

    public void reportForMobileModels(HashMap<String, Double> payments) {
        System.out.println();
        System.out.println("\n Mobile Model \t \t Total Monthly Payment");
        for (String mobileModel : payments.keySet()) {
            System.out.println(mobileModel + "\t \t " + payments.get(mobileModel));
        }
    }

    public void reportForMobileModelsForAllUsers(HashMap<String, Double> payments, HashMap<String, Integer> counts) {
        System.out.println();
        System.out.println("\n Mobile Model \t \t Total Monthly Payment \t \t Average Monthly Payment");
        for (String mobileModel : payments.keySet()) {
            System.out.println(mobileModel + "\t \t " + payments.get(mobileModel) + " \t \t " + payments.get(mobileModel) / (double) counts.get(mobileModel));
        }
    }

    public void reportForCityForAllUsers(HashMap<String, Double> payments, HashMap<String, Integer> counts) {
        System.out.println();
        System.out.println("\n City Names \t \t Total Monthly Payment \t \t Average Monthly Payment");
        for (String city : payments.keySet()) {
            System.out.println(city + "\t \t " + payments.get(city) + " \t \t " + payments.get(city) / (double) counts.get(city));
        }
    }

    public static void printUsers(HashMap<Integer, User> users) {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    //Lab 6
    public MobileCompany() {
    }

    public Boolean load(String fileName) {
        ObjectInputStream inputs = null;
        MobileCompany mobileCompany = new MobileCompany();
        try {
            inputs = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
        } catch (IOException ex) {
            System.err.println("error in create/open the file ");
            System.exit(1);
        }
        try {
            mobileCompany = (MobileCompany) inputs.readObject();
        } catch (EOFException ex) {
            System.out.println("no more record!");
        } catch (ClassNotFoundException ex) {
            System.err.println("error in wrong class in the file ");
        } catch (IOException ex) {
            System.err.println("error in add object to the file ");
            return false;
        }
        try {
            if (inputs != null)
                inputs.close();
        } catch (IOException ex) {
            System.err.println("error in close the file ");
            return false;
        }
        this.name = mobileCompany.name;
        this.adminUsername = mobileCompany.adminUsername;
        this.adminPassword = mobileCompany.adminPassword;
        this.flatRate = mobileCompany.flatRate;
        this.users = mobileCompany.users;

        return true;
    }

    public Boolean save(String fileName) throws IOException {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
        } catch (IOException ex) {
            System.err.println("error in create/open the file ");
            return false;
        }
        try {
            output.writeObject(this);
        } catch (IOException ex) {
            System.err.println("error in adding the objects to the file ");
        }
        try {
            if (output != null)
                output.close();
            return true;
        } catch (IOException ex) {
            System.err.println("error in closing the file ");
            System.exit(1);
            return false;
        }
    }

    public String toDelimitedString() {
        String output = name + "," + adminUsername + "," + adminPassword + "," + flatRate + "," + users.size();
        for (User user : users.values()) {
            output = output + "," + user.toDelimitedString();
        }
        return output;
    }

    public Boolean loadTextFile(String fileName) throws IOException, PlanException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        line = line.trim();
        String[] field = line.split(",");

        String name = field[0];
        String adminUsername = field[1];
        String adminPassword = field[2];
        double flatRate = Double.parseDouble(field[3]);
        int userCount = Integer.parseInt(field[4]);
        MobileCompany company = new MobileCompany(name, adminUsername, adminPassword, flatRate);
        int counter = 5;
        for (int i = 0; i < userCount; i++) {
            User user = User.readOneUser(field, counter);
            company.users.put(user.getUserID(), user);
            counter += 8;
            for (MobilePlan plan : user.getPlans().values()) {
                if (plan instanceof PersonalPlan) {
                    counter += 13;
                } else if (plan instanceof BusinessPlan) {
                    counter += 14;
                }
            }
        }
        this.name = company.name;
        this.adminUsername = company.adminUsername;
        this.adminPassword = company.adminPassword;
        this.flatRate = company.flatRate;
        this.users = company.users;
        reader.close();
        return true;
    }

    public Boolean saveTextFile(String fileName) throws IOException {
        BufferedWriter write = new BufferedWriter((new FileWriter(fileName)));
        write.write(this.toDelimitedString() + "\n");
        write.close();
        return true;
    }

    /////////////////////////////////////////////////////////Assignment 2/////////////////////////////////////////////////////////

    public ArrayList<User> sortUsersByMonthlyPayment() {
        ArrayList<User> usersArrayList = new ArrayList<>(users.values());
        userPaymentsComparator userComparator = new userPaymentsComparator();
        Collections.sort(usersArrayList, userComparator);
        return usersArrayList;
    }

    public HashMap<String, ArrayList<User>> getUsersPerCity() {
        HashMap<String, ArrayList<User>> allUsersPerCity = new HashMap<>();
        for (User user : users.values()) {
            allUsersPerCity.put(user.getCity(), (ArrayList<User>) users.values());
        }
        return allUsersPerCity;
    }

    public HashMap<String, ArrayList<MobilePlan>> filterPlansByExpiryDate(MyDate expiryDate) {
        HashMap<String, ArrayList<MobilePlan>> filteredPlans = new HashMap<>();
        for (User user : users.values()) {
            filteredPlans.put(user.getName(), user.getPlans());
        }
        return filteredPlans;
    }

}

class userPaymentsComparator implements Comparator<User> {
    @Override
    public int compare(User user, User user1) {
        return Double.compare(user.calcTotalPayments(), user1.calcTotalPayments());
    }
}