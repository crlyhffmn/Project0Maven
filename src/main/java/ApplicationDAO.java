import java.sql.SQLException;
import java.util.List;

public interface ApplicationDAO {
    void addApplication(Application application) throws SQLException;

    void updateApplication(Application application) throws SQLException;

    void deleteApplication(int id) throws SQLException;

    List<Application> getApplications() throws SQLException;

    Application getApplicationById(int id) throws SQLException;

    List<Application> getUnapprovedApplications() throws SQLException;
}
