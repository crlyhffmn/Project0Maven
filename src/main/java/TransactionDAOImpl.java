import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO{

    Connection connection;

    public TransactionDAOImpl() { this.connection = ConnectionFactory.getConnection(); }

    @Override
    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "insert into transactions (sourceAcctID, destAcctID, amount, transDescription, approved) values (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transaction.getSourceAcctID());
        preparedStatement.setInt(2, transaction.getDestAcctID());
        preparedStatement.setInt(3, transaction.getAmount());
        preparedStatement.setString(4, transaction.getTransDescription());
        preparedStatement.setBoolean(5, transaction.getApproved());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("transaction saved");
        else
            System.out.println("Something went wrong");
    }

    @Override
    public void updateTransaction(Transaction transaction) throws SQLException {
        String sql = "update transactions set sourceAcctID = ?, destAcctID = ?, amount = ?, transDescription = ?, approved = ? where transID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transaction.getSourceAcctID());
        preparedStatement.setInt(2, transaction.getDestAcctID());
        preparedStatement.setInt(3, transaction.getAmount());
        preparedStatement.setString(4, transaction.getTransDescription());
        preparedStatement.setInt(5, transaction.getId());
        preparedStatement.setBoolean(6, transaction.getApproved());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("transaction updated");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public void deleteTransaction(int id) throws SQLException {
        String sql = "delete from transactions where transID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("transaction deleted");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public List<Transaction> getTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transactions";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            int srcID = resultSet.getInt(2);
            int dstID = resultSet.getInt(3);
            int amt = resultSet.getInt(4);
            String tDes = resultSet.getString(5);
            Boolean appr = resultSet.getBoolean(6);
            Transaction transaction = new Transaction(id, srcID, dstID, amt, tDes, appr);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(int id) throws SQLException {
        Transaction transaction = new Transaction();
        String sql = "select * from transactions where transID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int tID = resultSet.getInt(1);
            int srcID = resultSet.getInt(2);
            int dstID = resultSet.getInt(3);
            int amt = resultSet.getInt(4);
            String tDes = resultSet.getString(5);
            Boolean appr = resultSet.getBoolean(6);
            transaction = new Transaction(tID, srcID, dstID, amt, tDes, appr);
        } else {
            System.out.println("no record found");
            return null;
        }
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsBySourceId(int id) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transactions where sourceAcctID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int tID = resultSet.getInt(1);
            int srcID = resultSet.getInt(2);
            int dstID = resultSet.getInt(3);
            int amt = resultSet.getInt(4);
            String tDes = resultSet.getString(5);
            boolean appr = resultSet.getBoolean(6);
            Transaction transaction = new Transaction(tID, srcID, dstID, amt, tDes, appr);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByDestId(int id) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transactions where destAcctID = " + id + " and approved = false;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int tID = resultSet.getInt(1);
            int srcID = resultSet.getInt(2);
            int dstID = resultSet.getInt(3);
            int amt = resultSet.getInt(4);
            String tDes = resultSet.getString(5);
            boolean app = resultSet.getBoolean(6);
            Transaction transaction = new Transaction(tID, srcID, dstID, amt, tDes, app);
            transactions.add(transaction);
        }
        return transactions;
    }
}
