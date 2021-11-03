import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    Connection connection;

    public EmployeeDAOImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        String sql = "insert into employees (userID, isCustomer) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employee.getUserID());
        preparedStatement.setBoolean(2, employee.getIsCustomer());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("Employee saved");
        else
            System.out.println("Oops! something went wrong");
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "update employees set userID = ?, isCustomer = ? where empID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employee.getUserID());
        preparedStatement.setBoolean(2, employee.getIsCustomer());
        preparedStatement.setInt(3, employee.getId());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("employee updated");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public void deleteEmployee(int id) throws SQLException {
        String sql = "delete from employees where empID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("employee deleted");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public List<Employee> getEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "select * from employees";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int userID = resultSet.getInt(2);
            Boolean isCustomer = resultSet.getBoolean(3);
            Employee employee = new Employee(id, userID, isCustomer);
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int empId) throws SQLException {
        Employee employee = new Employee();
        String sql = "select * from employees where empID = " + empId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int id = resultSet.getInt(1);
            int userID = resultSet.getInt(2);
            Boolean isCustomer = resultSet.getBoolean(3);
            employee = new Employee(id, userID, isCustomer);
        } else {
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByUserId(int userID) throws SQLException {
        Employee employee = new Employee();
        String sql = "select * from employees where userID = " + userID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        boolean notEmpty = resultSet.next();
        if (notEmpty) {
            int id = resultSet.getInt(1);
            int uID = resultSet.getInt(2);
            Boolean isCustomer = resultSet.getBoolean(3);
            employee = new Employee(id, uID, isCustomer);
        } else {
            return null;
        }
        return employee;
    }
}