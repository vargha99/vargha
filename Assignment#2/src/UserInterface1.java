import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface1 // different designs  
{
    public static void displayMainMenu() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("---Main Menu---");
        System.out.println("(1) Admin Login");
        System.out.println("(2) User Login");
        System.out.println("(3) Exit");
        System.out.println("\n\nSelect and Option from 1-3");
    }

    public static void mainMenu(MobileCompany mobileCompany) throws PlanException, CloneNotSupportedException, IOException {
        int input = 0;
        while (input != 3) {
            displayMainMenu();
            input = UITools.checkIntScanner();
            switch (input) {
                case 1:
                    adminLogin(mobileCompany);
                    break;
                case 2:
                    userLogin(mobileCompany);
                    break;
                case 3:
                    pressEnterToContinue();
                    break;
                default:
                    System.out.println("Wrong Option. Please enter a number between 1-3");
                    pressEnterToContinue();
            }
        }
    }
    //main menu options

    public static void adminLogin(MobileCompany mobileCompany) throws PlanException, CloneNotSupportedException, IOException {
        boolean loggedIn = false;
        Scanner sc = new Scanner(System.in);
        String username;
        String password;

        while (!loggedIn) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("--Admin Login---");
            System.out.println("Enter Username(or Q to return to main menu) :");
            username = sc.nextLine();
            if (username.equals("Q"))
                return; // return to main menu again
            System.out.println("Enter Password:");
            password = sc.nextLine();
            if (mobileCompany.validateAdmin(username, password)) {
                loggedIn = true;
                System.out.println("Login Successful");
            } else {
                System.out.println("Login Unsuccessful");
            }
        }
        //login successfull
        adminMenu(mobileCompany);
    }

    public static void userLogin(MobileCompany mobileCompany) throws PlanException {
        boolean loggedIn = false;
        Scanner sc = new Scanner(System.in);
        int userID;
        String password;
        User user = null;
        while (!loggedIn) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("--User Login---");
            System.out.println("Enter UserID ( or 0 to return to main menu) : ");
            userID = UITools.checkIntScanner();
            if (userID == 0)
                return; // return to main menu again
            System.out.println("Enter Password:");
            password = sc.nextLine();
            user = mobileCompany.validateUser(userID, password);
            if (user != null) {
                loggedIn = true;
                System.out.println("Login Successful");
            } else {
                System.out.println("Login Unsuccessful");
            }
        }
        userMainMenu(user);
    }

    public static void displayAdminMainMenu() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("---Admin Menu---");
        System.out.println("(1) Test Code");
        System.out.println("(2) Create User");
        System.out.println("(3) Create Personal Plan");
        System.out.println("(4) Create Business Plan");
        System.out.println("(5) Print User Information");
        System.out.println("(6) Filter By Mobile Model");
        System.out.println("(7) Filter By Expiry Date");
        System.out.println("(8) Update Address");
        System.out.println("(9) Report Monthly Payments for All Users Per City Names");
        System.out.println("(10) Report Monthly and Average Per Mobile Model For All Users");
        System.out.println("(11) Load File");
        System.out.println("(12) Save File");
        System.out.println("(13) Log out");
        System.out.println("\n\nSelect and Option from 1-9");
    }

    public static void adminMenu(MobileCompany mobileCompany) throws PlanException, CloneNotSupportedException, IOException {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != 9) {
            displayAdminMainMenu();
            option = UITools.checkIntScanner();
            switch (option) {
                case 1:
                    TestCase.testCode(mobileCompany);
                    break;
                case 2:
                    createUser(mobileCompany);
                    break;
                case 3:
                    createPersonalPlan(mobileCompany);
                    break;
                case 4:
                    createBusinessPlan(mobileCompany);
                    break;
                case 5:
                    printUserInformation(mobileCompany);
                    break;
                case 6:
                    filterByMobileModel(mobileCompany);
                    break;
                case 7:
                    filterByExpiryDate(mobileCompany);
                    break;
                case 8:
                    updateAddress(mobileCompany);
                    break;
                case 9:
                    reportPerCityAllUsers(mobileCompany);
                case 10:
                    reportPerMobileModelForAllUsers(mobileCompany);
                case 11:
                    loadAdmin(mobileCompany);
                case 12:
                    saveAdmin(mobileCompany);
                case 13:
                    break;
                default:
                    System.out.println("That option doesn't exist try a number within 1-13");
            }
            pressEnterToContinue();
        }
    }

    public static void displayUserMainMenu() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("---User Menu---");
        System.out.println("(1) Print All Plans and Total Monthly Payments");
        System.out.println("(2) Create Personal Plan");
        System.out.println("(3) Create Business Plan");
        System.out.println("(4) Print User Information");
        System.out.println("(5) Update Address");
        System.out.println("(6) Report Monthly and Average Per Mobile Model");
        System.out.println("(7) Log Out");
        System.out.println("\n\nSelect and Option from 1-6");
    }

    public static void userMainMenu(User user) throws PlanException {
        Scanner sc = new Scanner(System.in);
        int input = 0;

        while (input != 7) {
            displayUserMainMenu();
            input = UITools.checkIntScanner();

            switch (input) {
                case 1:
                    printUser(user);
                    break;
                case 2:
                    createPersonalPlan(user);
                    break;
                case 3:
                    createBusinessPlan(user);
                    break;
                case 4:
                    printUser(user);
                    break;
                case 5:
                    updateAddress(user);
                    break;
                case 6:
                    reportPerMobileModel(user, 50);
                case 7:
                    break;
                default:
                    System.out.println("That option doesn't exist try a number within 1-6");
            }
            pressEnterToContinue();
        }
    }

    //another way by sending company as proxy and user ID
//    public static void userMainMenu(MobileCompany mobileCompany, int userID) 
//    {
//        Scanner sc = new Scanner(System.in);
//        int input = 0;
//        while (input != 7)
//	{
//            displayUserMainMenu();
//            input = sc.nextInt();
//
//            switch (input)
//            {
//                case 1:
//                    printUser(mobileCompany,userID);
//                    break;
//                case 2:
//                    createPersonalPlan(mobileCompany, userID); // using company 
//                    break;
//                case 3:
//                    createBusinessPlan(mobileCompany, userID);
//                    break;
//                case 4:
//                    printUser(mobileCompany,userID);
//                    break;
//                case 5:
//                    updateAddress(mobileCompany, userID);
//                    break;
//                case 6:
//                    break;
//                default:
//                    System.out.println("That option doesn't exist try a number within 1-6");
//            }
//            pressEnterToContinue();
//        }		
//    }

    // admin menu options

    public static void createUser1(MobileCompany mobileCompany) // I keep it to show that this also can be done
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create User---");
        System.out.println("Enter User ID:");
        int userID = UITools.checkIntScanner();
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();
//        System.out.println("Address:");
//        System.out.println("Enter Street Number:");
//        int steetNum = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Enter Street Name:");
//        String streetName = sc.nextLine();
//        System.out.println("Enter Suburb:");
//        String suburb = sc.nextLine();
//        System.out.println("Enter City:");
//        String city = sc .nextLine();
//        addUser(mobileCompany,new User(userID, name, new Address(steetNum, streetName, suburb, city), username, password));
        Address address = UITools.getAddress();
        UITools.addUser(mobileCompany, new User(userID, name, address, password));
        //or
        // UITools.addUser(mobileCompany,name,userID, address, password);
    }

    public static void createUser(MobileCompany mobileCompany) throws PlanException //good way
    {
        UITools.addUser(mobileCompany, UITools.getUser());
    }

    public static void createPersonalPlan1(MobileCompany mobileCompany) throws PlanException // I keep it to show that this also can be done
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create Personal Plan---");

        System.out.println("Enter a UserID to add Plan to:");
        int userID = UITools.checkIntScanner();
        System.out.println("Enter Plan Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Plan ID:");
        int planID = sc.nextInt();
        sc.nextLine();
        // the below code is correct but better to be sent to UITools to avoid redundancy /duplication 

//        System.out.println("Mobile Phone:");
//        System.out.println("Enter Model:");
//        String model = sc.nextLine();
//        System.out.println("Enter Type a number between 0-2");
//        System.out.println("0. Android");
//        System.out.println("1. IOS");
//        System.out.println("2. Windows");
//        int type = sc.nextInt();
//        MobileType phoneType = MobileType.values()[type];
//        //or this one by reading the string and not a number
//        // phoneType=MobileType.valueOf(sc.next());
//        System.out.println("Enter Memory Size:");
//        int memorySize = sc.nextInt();
//        System.out.println("Enter Price:");
//        double price = sc.nextInt();
//        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);

        MobilePhone mobilePhone = UITools.getMobilePhone();// instead of above lines
        System.out.println("Enter Internet Quota:");
        int internetQuota = UITools.checkIntScanner();
        System.out.println("Enter Cap Limit:");
        int capLimit = UITools.checkIntScanner();
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        sc.nextLine();
//        MyDate expiryDate = new MyDate(year, month, day);
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter City:");
        String city = sc.nextLine();
        UITools.addPersonalPlan(mobileCompany, userID, userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, city);
        //or this one by creating the paln and calling addPlan
        UITools.addPlan(mobileCompany, userID, new PersonalPlan(userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, city));
    }

    public static void createPersonalPlan(MobileCompany mobileCompany) throws PlanException // good way
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create Personal Plan---");
        System.out.println("Enter a UserID to add Plan to:");
        int userID = UITools.checkIntScanner();
        UITools.addPlan(mobileCompany, userID, UITools.getPersonalPlan());
    }

    public static void createPersonalPlan1(MobileCompany mobileCompany, int userID) throws PlanException // bad way
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create Personal Plan---");
        System.out.println("Enter Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Plan ID:");
        int planID = UITools.checkIntScanner();
//        System.out.println("Mobile Phone--");
//        System.out.println("Enter Model:");
//        String model = sc.nextLine();
//        System.out.println("Enter Type a number between 0-2");
//        System.out.println("0. Android");
//        System.out.println("1. IOS");
//        System.out.println("2. Windows");
//        int type = sc.nextInt();
//        MobileType phoneType = MobileType.values()[type];
//        System.out.println("Enter Memory Size:");
//        int memorySize = sc.nextInt();
//        System.out.println("Enter Price:");
//        double price = sc.nextInt();
//        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);
        MobilePhone mobilePhone = UITools.getMobilePhone();
        System.out.println("Enter Internet Quota:");
        int internetQuota = UITools.checkIntScanner();
        System.out.println("Enter Cap Limit:");
        int capLimit = UITools.checkIntScanner();
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        sc.nextLine();
//        MyDate expiryDate = new MyDate(year, month, day);
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter City:");
        String city = sc.nextLine();
        UITools.addPersonalPlan(mobileCompany, userID, userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, city);
        //or this one by creating the paln and calling addPlan
        UITools.addPlan(mobileCompany, userID, new PersonalPlan(userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, city));
    }

    public static void createPersonalPlan(MobileCompany mobileCompany, int userID) throws PlanException // Good way
    {
        System.out.println("---Create Personal Plan---");
        UITools.addPlan(mobileCompany, userID, UITools.getPersonalPlan());
    }

    public static void createBusinessPlan1(MobileCompany mobileCompany) throws PlanException // bad way
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create Business Plan---");

        System.out.println("Enter User Id to add Plan to:");
        int userID = UITools.checkIntScanner();
        System.out.println("Enter Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Plan ID:");
        int planID = UITools.checkIntScanner();
//        System.out.println("Mobile Phone--");
//        System.out.println("Enter Model:");
//        String model = sc.nextLine();
//        System.out.println("Enter Type a number between 0-2");
//        System.out.println("0. Android");
//        System.out.println("1. IOS");
//        System.out.println("2. Windows");
//        int type = sc.nextInt();
//        MobileType phoneType = MobileType.values()[type];
//        System.out.println("Enter Memory Size:");
//        int memorySize = sc.nextInt();
//        System.out.println("Enter Price:");
//        double price = sc.nextInt();
//        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);
        MobilePhone mobilePhone = UITools.getMobilePhone();
        System.out.println("Enter Internet Quota:");
        int internetQuota = UITools.checkIntScanner();
        System.out.println("Enter Cap Limit:");
        int capLimit = UITools.checkIntScanner();
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        MyDate expiryDate = new MyDate(year, month, day);
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter Number Of Employees:");
        int numOfEmployees = UITools.checkIntScanner();
        System.out.println("Enter ABN:");
        int ABN = UITools.checkIntScanner();
        UITools.addBusinessPlan(mobileCompany, userID, userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, numOfEmployees, ABN);
        //or this one by creating the paln and calling addPlan
        UITools.addPlan(mobileCompany, userID, new BusinessPlan(userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, numOfEmployees, ABN));
    }

    public static void createBusinessPlan(MobileCompany mobileCompany) throws PlanException // Good way
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create Business Plan---");

        System.out.println("Enter User Id to add Plan to:");
        int userID = UITools.checkIntScanner();
        UITools.addPlan(mobileCompany, userID, UITools.getBusinessPlan());
    }

    public static void createBusinessPlan1(MobileCompany mobileCompany, int userID) throws PlanException // bad way
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Create Business Plan---");
        System.out.println("Enter Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Plan ID:");
        int planID = UITools.checkIntScanner();
//        System.out.println("Mobile Phone--");
//        System.out.println("Enter Model:");
//        String model = sc.nextLine();
//        System.out.println("Enter Type a number between 0-2");
//        System.out.println("0. Android");
//        System.out.println("1. IOS");
//        System.out.println("2. Windows");
//        int type = sc.nextInt();
//        MobileType phoneType = MobileType.values()[type];
//        System.out.println("Enter Memory Size:");
//        int memorySize = sc.nextInt();
//        System.out.println("Enter Price:");
//        double price = sc.nextInt();
//        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);
        MobilePhone mobilePhone = UITools.getMobilePhone();
        System.out.println("Enter Internet Quota:");
        int internetQuota = UITools.checkIntScanner();
        System.out.println("Enter Cap Limit:");
        int capLimit = UITools.checkIntScanner();
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        MyDate expiryDate = new MyDate(year, month, day);
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter Number Of Employees:");
        int numOfEmployees = UITools.checkIntScanner();
        System.out.println("Enter ABN:");
        int ABN = UITools.checkIntScanner();
        UITools.addBusinessPlan(mobileCompany, userID, userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, numOfEmployees, ABN);
        //or this one by creating the paln and calling addPlan
        UITools.addPlan(mobileCompany, userID, new BusinessPlan(userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, numOfEmployees, ABN));
    }

    public static void createBusinessPlan(MobileCompany mobileCompany, int userID) throws PlanException // Good way
    {
        System.out.println("---Create Business Plan---");
        UITools.addPlan(mobileCompany, userID, UITools.getBusinessPlan());
    }

    public static void printUserInformation(MobileCompany mobileCompany) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Print User Information---");
        System.out.println("Enter User ID:");
        int userID = UITools.checkIntScanner();
        //mobileCompany.printPlans(userID); // does not print user info
        mobileCompany.printUser(userID);// better    
    }

    public static void printUser(MobileCompany mobileCompany, int userID) {
        System.out.println("---User Information---");
        mobileCompany.printUser(userID);
    }

    public static void filterByMobileModel(MobileCompany mobileCompany) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Filter By Mobile Model---");
        System.out.println("Enter Mobile Model:");
        String mobileModel = sc.nextLine();
        ArrayList<MobilePlan> filteredPlans = mobileCompany.filterByMobileModel(mobileModel);
        System.out.println("Filtered Plans--");
        MobilePlan.printPlans(filteredPlans);
        double totalMonthlyPayments = MobilePlan.calcTotalPayments(filteredPlans, mobileCompany.getFlatRate());
        System.out.println("Total Monthly Payments:$" + totalMonthlyPayments);
    }

    public static void filterByExpiryDate(MobileCompany mobileCompany) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Filter By Expiry Date---");
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        MyDate expiryDate = new MyDate(year, month, day);
        MyDate expiryDate = UITools.getDate();
        System.out.println("Enter User ID:");
        int userId = UITools.checkIntScanner();
        ArrayList<MobilePlan> filteredPlans = mobileCompany.filterByExpiryDate(userId, expiryDate);
        System.out.println("Expired Plans--");
        MobilePlan.printPlans(filteredPlans);
    }

    public static void updateAddress(MobileCompany mobileCompany) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---Update Address---");
        System.out.println("Enter User ID for Address to be changed:");
        int userId = UITools.checkIntScanner();
//        System.out.println("Address--");
//        System.out.println("Enter Street Number:");
//        int steetNum = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Enter Street Name:");
//        String streetName = sc.nextLine();
//        System.out.println("Enter Suburb:");
//        String suburb = sc.nextLine();
//        System.out.println("Enter City:");
//        String city = sc .nextLine();
//        Address newAddress=new Address(steetNum, streetName, suburb, city);
        Address newAddress = UITools.getAddress();
        User user = mobileCompany.findUser(userId);
        if (user != null) {
            user.setAddress(newAddress);
            System.out.println("New Address:");
            System.out.println(user.getAddress());
        } else {
            System.out.println("User has not been found");
        }
    }

    public static void updateAddress(MobileCompany mobileCompany, int userID) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("---Update Address---");
//        System.out.println("Address--");
//        System.out.println("Enter Street Number:");
//        int steetNum = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Enter Street Name:");
//        String streetName = sc.nextLine();
//        System.out.println("Enter Suburb:");
//        String suburb = sc.nextLine();
//        System.out.println("Enter City:");
//        String city = sc .nextLine();
//        Address newAddress=new Address(steetNum, streetName, suburb, city);
        Address newAddress = UITools.getAddress();
        User user = mobileCompany.findUser(userID);
        if (user != null) {
            user.setAddress(newAddress);
            System.out.println("New Address:");
            System.out.println(user.getAddress());
        } else {
            System.out.println("User has not been found");
        }
    }

    //User menu options

    public static void createPersonalPlan(User user) throws PlanException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("---Create Personal Plan---");
//        System.out.println("Enter Username:");
//        String userName = sc.nextLine();
//        System.out.println("Enter Plan ID:");
//        int planID = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Mobile Phone--");
//        System.out.println("Enter Model:");
//        String model = sc.nextLine();
//        System.out.println("Enter Type a number between 0-2");
//        System.out.println("0. Android");
//        System.out.println("1. IOS");
//        System.out.println("2. Windows");
//        int type = sc.nextInt();
//        MobileType phoneType = MobileType.values()[type];
//        System.out.println("Enter Memory Size:");
//        int memorySize = sc.nextInt();
//        System.out.println("Enter Price:");
//        double price = sc.nextInt();
//        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);
//        System.out.println("Enter Internet Quota:");
//        int internetQuota = sc.nextInt();
//        System.out.println("Enter Cap Limit:");
//        int capLimit = sc.nextInt();
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        sc.nextLine();
//        MyDate expiryDate = new MyDate(year, month, day);
//        System.out.println("Enter City:");
//        String city = sc.nextLine();
//        UITools.addPersonalPlan(user, userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, city);
        UITools.addPlan(user, UITools.getPersonalPlan());
        //or this if we only send the userID to the menu and using company as a proxy
        //UITools.addPlan(mobileCompany,userID,UITools.getPersonalPlan());
    }

    public static void createBusinessPlan(User user) throws PlanException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("---Create Business Plan---");
//        System.out.println("Enter Username:");
//        String userName = sc.nextLine();
//        System.out.println("Enter Plan ID:");
//        int planID = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Mobile Phone--");
//        System.out.println("Enter Model:");
//        String model = sc.nextLine();
//        System.out.println("Enter Type a number between 0-2");
//        System.out.println("0. Android");
//        System.out.println("1. IOS");
//        System.out.println("2. Windows");
//        int type = sc.nextInt();
//        MobileType phoneType = MobileType.values()[type];
//        System.out.println("Enter Memory Size:");
//        int memorySize = sc.nextInt();
//        System.out.println("Enter Price:");
//        double price = sc.nextInt();
//        MobilePhone mobilePhone = new MobilePhone(model, phoneType, memorySize, price);
//        System.out.println("Enter Internet Quota:");
//        int internetQuota = sc.nextInt();
//        System.out.println("Enter Cap Limit:");
//        int capLimit = sc.nextInt();
//        System.out.println("Expiry Date--");
//        System.out.println("Enter Day:");
//        int day = sc.nextInt();
//        System.out.println("Enter Month:");
//        int month = sc.nextInt();
//        System.out.println("Enter Year:");
//        int year = sc.nextInt();
//        MyDate expiryDate = new MyDate(year, month, day);
//        System.out.println("Enter Number Of Employees:");
//        int numOfEmployees = sc.nextInt();
//        System.out.println("Enter ABN:");
//        int ABN = sc.nextInt();
//        UITools.addBusinessPlan(user, userName, planID, mobilePhone, internetQuota, capLimit, expiryDate, numOfEmployees, ABN);
        UITools.addPlan(user, UITools.getBusinessPlan());
        //or this if we only send the userID to the menu and using company as a proxy
        //UITools.addPlan(mobileCompany,userID,UITools.getBusinessPlan());
    }

    public static void printUser(User user) {
        System.out.println("---User Information---");
        user.print();
    }

    public static void updateAddress(User user) {
//        Scanner sc = new Scanner(System.in);
        System.out.println("---Update Address---");
//        System.out.println("Address--");
//        System.out.println("Enter Street Number:");
//        int steetNum = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Enter Street Name:");
//        String streetName = sc.nextLine();
//        System.out.println("Enter Suburb:");
//        String suburb = sc.nextLine();
//        System.out.println("Enter City:");
//        String city = sc .nextLine();
//        user.setAddress(new Address(steetNum, streetName, suburb, city));
        user.setAddress(UITools.getAddress());
        System.out.println("New Address:");
        System.out.println(user.getAddress());
    }

    public static void pressEnterToContinue() {
        System.out.println("Press Enter Key to Continue...");
        try {
            System.in.read();
        } catch (Exception e) {

        }
    }

    public static void reportPerMobileModel(User user, double flatRate) {
        UITools.reportTotalPaymentsPerMobileModel(user, flatRate);
    }

    public static void reportPerCityAllUsers(MobileCompany mobileCompany) {
        UITools.reportTotalPaymentsPerCityAllUsers(mobileCompany, mobileCompany.getFlatRate());
    }

    public static void reportPerMobileModelForAllUsers(MobileCompany mobileCompany) {
        UITools.reportTotalPaymentsPerMobileModelAllUsers(mobileCompany, mobileCompany.getFlatRate());
    }

    //Lab6
    public static void loadAdmin(MobileCompany mobileCompany) throws PlanException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Loading File...");
        System.out.println("Enter The Name of The File:");
        String fileName = sc.nextLine();
        fileName = fileName + ".ser";
        UITools.loadAdmin(mobileCompany, fileName);
        System.out.println(mobileCompany);
    }

    public static void saveAdmin(MobileCompany mobileCompany) throws PlanException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Saving File...");
        System.out.println("Enter The Name of The File:");
        String fileName = sc.nextLine();
        fileName = fileName + ".ser";
        UITools.saveAdmin(mobileCompany, fileName);
    }
}
