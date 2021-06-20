public class BusinessPlan extends MobilePlan {
    public int numberOfEmployees;
    public int ABN;

    public BusinessPlan(String userName, int id, MobilePhone handset, int internetQuota, int capLimit, MyDate expiryDate, int numberOfEmployees, int ABN) throws PlanException {
        super(userName, id, handset, internetQuota, capLimit, expiryDate);
        this.numberOfEmployees = numberOfEmployees;
        this.ABN = ABN;
    }

    public BusinessPlan(BusinessPlan businessPlan) {
        super(businessPlan);
        this.numberOfEmployees = businessPlan.numberOfEmployees;
        this.ABN = businessPlan.ABN;
    }

    public double calcPayment(double flatRate) {
        double payment = handset.getPrice() / 24 + capLimit / 10 + internetQuota * 10 + flatRate;

        if (numberOfEmployees > 10) {
            payment += (numberOfEmployees - 10) * 50;
        }
        return payment;
    }

    public void print() {
        super.print();
        System.out.println(" Number Of Employees: " + numberOfEmployees + " ABN: " + ABN);
    }

    public String toString() {
        return super.toString() + " Number Of Employees: " + numberOfEmployees + " ABN: " + ABN;
    }

    @Override
    public void setCity(String city) {
    }

    //Lab6
    public String toDelimitedString() {
        return "BP," + super.toDelimitedString() + "," + numberOfEmployees + "," + ABN;
    }
}
