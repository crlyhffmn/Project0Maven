import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    Connection connection;

    public UserDAOImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "insert into users (firstName, lastName, userName, password) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getUserName());
        preparedStatement.setString(4, user.getPassword());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("User saved");
        else
            System.out.println("Oops! something went wrong");
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "update users set firstName = ?, lastName = ?, userName = ?, password = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getUserName());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(6, user.getId());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("user updated");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String sql = "delete from users where userID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("user deleted");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String userName = resultSet.getString(4);
            String password = resultSet.getString(5);
            Boolean isCustomer = resultSet.getBoolean(6);
            User user = new User(id, firstName, lastName, userName, password);
            users.add(user);
        }
        return users;
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        User user = new User();
        String sql = "select * from users where userID = " + userId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String userName = resultSet.getString(4);
            String password = resultSet.getString(5);
            user = new User(id, firstName, lastName, userName, password);
        } else {
            System.out.println("no record found");
            return null;
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        User user = new User();
        String sql = "select * from users where userName = \"" + username + "\";";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String userName = resultSet.getString(4);
            String password = resultSet.getString(5);
            user = new User(id, firstName, lastName, userName, password);
        } else {
            System.out.println("no record found");
            return null;
        }
        return user;
    }
}