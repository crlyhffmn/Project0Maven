public class Transaction {
    private int id;
    private int sourceAcctID;
    private int destAcctID;
    private int amount;
    private String transDescription;
    private boolean approved;

    public Transaction() {

    }

    public Transaction(int id, int acctID, int destID, int amount, String transDescription) {
        this.id = id;
        this.sourceAcctID = acctID;
        this.destAcctID = destID;
        this.amount = amount;
        this.transDescription = transDescription;
        this.approved = false;
    }

    public Transaction(int id, int acctID, int destID, int amount, String transDescription, boolean approved) {
        this.id = id;
        this.sourceAcctID = acctID;
        this.destAcctID = destID;
        this.amount = amount;
        this.transDescription = transDescription;
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceAcctID() {
        return sourceAcctID;
    }

    public void setSourceAcctID(int acctID) {
        this.sourceAcctID = acctID;
    }

    public int getDestAcctID() { return destAcctID; }

    public void setDestAcctID(int id) { this.destAcctID = id; }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

    public void setApproved (boolean approved) { this.approved = approved; }

    public boolean getApproved() { return approved; }

    @Override
    public String toString() {
        return "Transaction{" +
                "ID: " + id +
                ", Source Account ID: " + sourceAcctID +
                ", Destination Account ID: " + destAcctID +
                ", Amount = $" + amount +
                ", Type: " + transDescription +
                '}';
    }
}
