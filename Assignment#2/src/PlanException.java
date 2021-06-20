public class PlanException extends Exception {
    private int id;
    private int oldID;

    public PlanException(int id) {
        oldID = id;
        this.id = generateID();
    }

    public int getNewID() {
        return id;
    }

    public int generateID() {
        return (int) ((Math.random() * ((3999999 - 3000000) + 1)) + 3000000);
    }

    public String toString() {
        return " The Plan ID " + oldID + " was not valid and a new ID (" + id + ") is generated by admin and assigned for the Plan";
    }
}