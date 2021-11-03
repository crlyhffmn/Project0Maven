public class Employee {
    private int id;
    private int userID;
    private boolean isCustomer;

    public Employee(){ }

    public Employee(int id, int userID, boolean isCustomer) {
        this.id = id;
        this.userID = userID;
        this.isCustomer = isCustomer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean getIsCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Employee ID =" + id +
                ", User ID='" + userID +
                '}';
    }
}