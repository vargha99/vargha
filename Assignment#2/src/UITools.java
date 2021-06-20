import java.util.InputMismatchException;
import java.util.Scanner;
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
import java.util.List;

public class UITools implements Serializable {
    //User UI methods

    public static void validateUser(User user, int userID, String password) {
        if (user.validateUser(userID, password)) {
            System.out.println("User Login Successful");
        } else {
            System.out.println("User Login Unsuccessful");
        }
    }

    public static void addPlan(User user, MobilePlan plan) {
        if (user.addPlan(plan)) {
            System.out.println("The plan has been added successfully");
        } else {
            System.out.println("The plan can't be added ID already exists");
        }
    }

//    public static void addBusinessPlan(User user, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, int numOfEmployees, int ABN) {
//        if (user.createBusinessPlan(userName, planID, mobile, internetQuota, capLimit, expiryDate, numOfEmployees, ABN)) {
//            System.out.println("The plan was added successfully");
//        } else {
//            System.out.println("The plan can't be added. A plan with the same ID exists");
//        }
//    }
//
//    public static void addPersonalPlan(User user, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, String city) {
//        if (user.createPersonalPlan(userName, planID, mobile, internetQuota, capLimit, expiryDate, city)) {
//            System.out.println("The plan was added successfully");
//        } else {
//            System.out.println("The plan can't be added. A plan with the same ID exists");
//        }
//    }

    //Company UI methods

    public static void validateAdmin(MobileCompany mobileCompany, String username, String password) {
        if (mobileCompany.validateAdmin(username, password)) {
            System.out.println("Admin Login Successful");
        } else {
            System.out.println("Admin Login Unsuccessful");
        }
    }

    public static void addUser(MobileCompany mobileCompany, User user) {
        if (mobileCompany.addUser(user)) {
            System.out.println("The user has been added successfully");
        } else {
            System.out.println("The user can't be added ID already exists");
        }
    }

    public static void addUser(MobileCompany mobileCompany, String name, int id, Address address, String password) {
        if (mobileCompany.addUser(name, id, address, password)) {
            System.out.println("The user has been added successfully");
        } else {
            System.out.println("The user can't be added ID already exists");
        }
    }

    public static void addPlan(MobileCompany mobileCompany, int userID, MobilePlan plan) {
        if (mobileCompany.addPlan(userID, plan)) {
            System.out.println("The plan was added successfully");
        } else {
            System.out.println("The plan can't be added. A plan with the same ID exists");
        }
    }

//    public static void addBusinessPlan(MobileCompany mobileCompany, int userID, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, int numOfEmployees, int ABN) {
//        if (mobileCompany.createBusinessPlan(userID, userName, planID, mobile, internetQuota, capLimit, expiryDate, numOfEmployees, ABN)) {
//            System.out.println("The plan was added successfully");
//        } else {
//            System.out.println("The plan can't be added. A plan with the same ID exists");
//        }
//    }
//
//    public static void addPersonalPlan(MobileCompany mobileCompany, int userID, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, String city) {
//        if (mobileCompany.createPersonalPlan(userID, userName, planID, mobile, internetQuota, capLimit, expiryDate, city)) {
//            System.out.println("The plan was added successfully");
//        } else {
//            System.out.println("The plan can't be added. A plan with the same ID exists");
//        }
//    }

    public static void mobilePriceRise(MobileCompany mobileCompany, int userID, double risePercent) {
        if (mobileCompany.mobilePriceRise(userID, risePercent)) {
            System.out.println("The price rise has been applied successfully");
        } else {
            System.out.println("The user has not been found");
        }
    }

    //scanner and user input methods
    //Lab 5

    public static Address getAddress() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Address:");
        System.out.println("Enter Street Number:");
        int streetNum = checkIntScanner();
        System.out.println("Enter Street Name:");
        String streetName = sc.nextLine();
        System.out.println("Enter Suburb:");
        String suburb = sc.nextLine();
        System.out.println("Enter City:");
        String city = sc.nextLine();
        return new Address(streetNum, streetName, suburb, city);
    }

    public static User getUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create User---");
        System.out.println("Enter User ID:");
        int userID = checkIntScanner();
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();
        Address address = UITools.getAddress();
        return new User(userID, name, address, password);
    }

    public static MobilePhone getMobilePhone() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Mobile Phone:");
        System.out.println("Enter Model:");
        String model = sc.nextLine();
        System.out.println("Enter Type a number between 0-2");
        System.out.println("1. Android");
        System.out.println("2. IOS");
        System.out.println("3. Windows");
        int type = 0;
        boolean flag = false;
        while (!flag) {
            type = checkIntScanner();
            try {
                if (type < 0 || type > 3) {
                    System.out.println("Not in Range!");
                    throw new InputMismatchException("Not In Range");
                } else {
                    flag = true;
                }
            } catch (InputMismatchException e) {
                System.out.println(e);
            }
        }
        MobileType phoneType = MobileType.values()[type - 1];
        //or this one by reading the string and not a number
        // phoneType=MobileType.valueOf(sc.next());
        System.out.println("Enter Memory Size:");
        int memorySize = checkIntScanner();
        System.out.println("Enter Price:");
        double price = checkDoubleScanner();
        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);
        return new MobilePhone(model, phoneType, memorySize, price);
    }

    public static MyDate getDate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Expiry Date--");
        System.out.println("Enter Day:");
        int day = checkIntScanner();
        System.out.println("Enter Month:");
        int month = checkIntScanner();
        System.out.println("Enter Year:");
        int year = checkIntScanner();
        return new MyDate(year, month, day);
    }

    //Lab5
    public static PersonalPlan getPersonalPlan() throws PlanException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Personal Plan---");
        System.out.println("Enter Plan Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Plan ID:");
        int planID = checkIntScanner();
        if (planID < 3000000 || planID > 3999999) {
            throw new PlanException(planID);
        }
        sc.nextLine();
        MobilePhone mobilePhone = UITools.getMobilePhone();
        System.out.println("Enter Internet Quota:");
        int internetQuota = checkIntScanner();
        System.out.println("Enter Cap Limit:");
        int capLimit = checkIntScanner();
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter City:");
        String city = sc.nextLine();
        return new PersonalPlan(userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, city);
    }

    public static BusinessPlan getBusinessPlan() throws PlanException {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Business Plan---");
        System.out.println("Enter Plan Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Plan ID:");
        int planID = checkIntScanner();
        if (planID < 3000000 || planID > 3999999) {
            throw new PlanException(planID);
        }
        sc.nextLine();
        MobilePhone mobilePhone = UITools.getMobilePhone();
        System.out.println("Enter Internet Quota:");
        int internetQuota = checkIntScanner();
        System.out.println("Enter Cap Limit:");
        int capLimit = sc.nextInt();
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter Number Of Employees:");
        int numOfEmployees = checkIntScanner();
        System.out.println("Enter ABN:");
        int ABN = checkIntScanner();
        return new BusinessPlan(userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, numOfEmployees, ABN);
    }

    public static int checkIntScanner() {
        Scanner sc = new Scanner(System.in);
        int inputInteger = 0;
        while (inputInteger == 0) {
            try {
                inputInteger = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                String badInput = sc.next();
                System.out.println(badInput + " is Not Correct!\nEnter an Integer Please!!");
            }
        }
        return inputInteger;
    }

    public static int checkIntScannerForType() {
        Scanner sc = new Scanner(System.in);
        int inputInteger = 0;
        while (inputInteger == 0) {
            try {
                inputInteger = sc.nextInt();
                if (inputInteger < 1 || inputInteger > 3) {
                    throw new InputMismatchException("Not In Range");
                }
                sc.nextLine();
            } catch (InputMismatchException e) {
                String badInput = sc.next();
                System.out.println(badInput + " is Not Correct!\nEnter an Integer Please!!");
            }
        }
        return inputInteger;
    }

    public static double checkDoubleScanner() {
        Scanner sc = new Scanner(System.in);
        int inputDouble = 0;
        while (inputDouble == 0) {
            try {
                inputDouble = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                String badInput = sc.next();
                System.out.println(badInput + " is Not Correct!\nEnter an Integer Please!!");
            }
        }
        return inputDouble;
    }

    public static void addBusinessPlan(User user, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, int numOfEmployees, int ABN) throws PlanException {
        if (planID < 3000000 || planID > 3999999) {
            throw new PlanException(planID);
        }
        if (user.createBusinessPlan(userName, planID, mobile, internetQuota, capLimit, expiryDate, numOfEmployees, ABN)) {
            System.out.println("The plan was added successfully");
        } else {
            System.out.println("The plan can't be added. A plan with the same ID exists");
        }
    }

    public static void addPersonalPlan(User user, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, String city) throws PlanException {
        if (user.createPersonalPlan(userName, planID, mobile, internetQuota, capLimit, expiryDate, city)) {
            System.out.println("The plan was added successfully");
        } else {
            System.out.println("The plan can't be added. A plan with the same ID exists");
        }
    }

    public static void addBusinessPlan(MobileCompany mobileCompany, int userID, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, int numOfEmployees, int ABN) throws PlanException {
        if (mobileCompany.createBusinessPlan(userID, userName, planID, mobile, internetQuota, capLimit, expiryDate, numOfEmployees, ABN)) {
            System.out.println("The plan was added successfully");
        } else {
            System.out.println("The plan can't be added. A plan with the same ID exists");
        }
    }

    public static void addPersonalPlan(MobileCompany mobileCompany, int userID, String userName, int planID, MobilePhone mobile, int internetQuota, int capLimit, MyDate expiryDate, String city) throws PlanException {
        if (mobileCompany.createPersonalPlan(userID, userName, planID, mobile, internetQuota, capLimit, expiryDate, city)) {
            System.out.println("The plan was added successfully");
        } else {
            System.out.println("The plan can't be added. A plan with the same ID exists");
        }
    }

    public static void reportTotalPaymentsPerMobileModel(User user, double flatRate) {
        System.out.println("\nPrinting Total Payments Per Mobile Models For User " + user.getUserID());
        user.reportForMobileModels(user.getTotalPaymentForMobileModel(flatRate), user.getTotalCountForMobileModel());
    }

    public static void reportTotalPaymentsPerCityAllUsers(MobileCompany mobileCompany, double flatRate) {
        System.out.println("\nPrinting Total Payments Per City For All Users in Mobile Company:");
        mobileCompany.reportForCityForAllUsers(mobileCompany.getTotalPaymentForCity(flatRate), mobileCompany.getTotalCountForCity());
    }

    public static void reportTotalPaymentsPerMobileModelAllUsers(MobileCompany mobileCompany, double flatRate) {
        System.out.println("\nPrinting Total Payments Per Mobile Models For All Users in Mobile Company:");
        mobileCompany.reportForMobileModelsForAllUsers(mobileCompany.getTotalPaymentsForMobileModel(flatRate), mobileCompany.getTotalCountForMobileModel());
    }

    //Lab6
    public static Boolean loadAdmin(MobileCompany mobileCompany, String fileName) throws PlanException, IOException {
        return mobileCompany.load(fileName);
    }

    public static Boolean saveAdmin(MobileCompany mobileCompany, String fileName) throws PlanException, IOException {
        return mobileCompany.save(fileName);
    }
}

