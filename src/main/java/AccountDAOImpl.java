import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO{

    Connection connection;

    public AccountDAOImpl() { this.connection = ConnectionFactory.getConnection(); }

    @Override
    public void addAccount(Account account) throws SQLException {
        String sql = "insert into accounts (userID, balance) values (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account.getUserID());
        preparedStatement.setInt(2, account.getBalance());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("account saved");
        else
            System.out.println("Something went wrong");
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        String sql = "update accounts set userID = ?, balance = ? where acctID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account.getUserID());
        preparedStatement.setInt(2, account.getBalance());
        preparedStatement.setInt(3, account.getId());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("account updated");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public void deleteAccount(int id) throws SQLException {
        String sql = "delete from users where acctID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("account deleted");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public List<Account> getAccounts() throws SQLException {
        List<Account> accts = new ArrayList<>();
        String sql = "select * from accounts";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            int uID = resultSet.getInt(2);
            int bal = resultSet.getInt(3);
            Account account = new Account(id, uID, bal);
            accts.add(account);
        }
        return accts;
    }

    @Override
    public Account getAccountById(int id) throws SQLException {
        Account acct = new Account();
        String sql = "select * from accounts where acctID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int acctID = resultSet.getInt(1);
            int uID = resultSet.getInt(2);
            int bal = resultSet.getInt(3);
            acct = new Account(id, uID, bal);
        } else {
            System.out.println("no record found");
            return null;
        }
        return acct;
    }

    @Override
    public List<Account> getAccountsByUser(int userID) throws SQLException {
        List<Account> accts = new ArrayList<>();
        String sql = "select * from accounts where userID = " + userID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            int uID = resultSet.getInt(2);
            int bal = resultSet.getInt(3);
            Account account = new Account(id, uID, bal);
            accts.add(account);
        }
        return accts;
    }
}
