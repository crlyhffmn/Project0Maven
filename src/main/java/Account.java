public class Account {
    private int id;
    private int userID;
    private int balance;

    public Account () {

    }

    public Account (int id, int userID, int balance) {
        this.id = id;
        this.userID = userID;
        this.balance = balance;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Account ID: " + id +
                ", User ID: " + userID +
                ", Balance: $" + balance +
                '}';
    }
}
