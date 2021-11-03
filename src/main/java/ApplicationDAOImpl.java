import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAOImpl implements ApplicationDAO{

    Connection connection;

    public ApplicationDAOImpl() { this.connection = ConnectionFactory.getConnection(); }

    @Override
    public void addApplication(Application application) throws SQLException {
        String sql = "insert into applications (userID, depositAmt, approved) values (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, application.getUserID());
        preparedStatement.setInt(2, application.getDepositAmt());
        preparedStatement.setBoolean(3, application.isApproved());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("application saved");
        else
            System.out.println("Something went wrong");
    }

    @Override
    public void updateApplication(Application application) throws SQLException {
        String sql = "update applications set depositAmt = ?, approved = ? where applicationID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, application.getDepositAmt());
        preparedStatement.setBoolean(2, application.isApproved());
        preparedStatement.setInt(3, application.getId());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("application updated");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public void deleteApplication(int id) throws SQLException {
        String sql = "delete from applications where applicationID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("application deleted");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public List<Application> getApplications() throws SQLException {
        List<Application> applications = new ArrayList<>();
        String sql = "select * from applications";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            int userID = resultSet.getInt(2);
            int depositAmt = resultSet.getInt(3);
            boolean approved = resultSet.getBoolean(4);
            Application application = new Application(id, userID, depositAmt, approved);
            applications.add(application);
        }
        return applications;
    }

    @Override
    public Application getApplicationById(int id) throws SQLException {
        Application application = new Application();
        String sql = "select * from applications where applicationID = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet != null) {
            int appID = resultSet.getInt(1);
            int uID = resultSet.getInt(2);
            int depAmt = resultSet.getInt(3);
            boolean approved = resultSet.getBoolean(4);
            application = new Application(appID, uID, depAmt, approved);
        } else {
            System.out.println("no record found");
            return null;
        }
        return application;
    }

    @Override
    public List<Application> getUnapprovedApplications() throws SQLException {
        List<Application> applications = new ArrayList<>();
        String sql = "select * from applications where approved = false";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            int userID = resultSet.getInt(2);
            int depositAmt = resultSet.getInt(3);
            boolean approved = resultSet.getBoolean(4);
            Application application = new Application(id, userID, depositAmt, approved);
            applications.add(application);
        }
        return applications;
    }
}