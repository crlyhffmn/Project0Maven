public class Application {
    private int id;
    private int userID;
    private int depositAmt;
    private boolean approved;

    public Application() {

    }

    public Application(int id, int userID, int depositAmt, boolean approved) {
        this.id = id;
        this.userID = userID;
        this.depositAmt = depositAmt;
        this.approved = approved;
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

    public int getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(int depositAmt) {
        this.depositAmt = depositAmt;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Application{" +
                "Application ID: " + id +
                ", User ID: " + userID +
                ", Deposit Amount: $" + depositAmt +
                ", Approved: " + approved +
                '}';
    }

}
