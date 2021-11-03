public class DAOFactory {
    private static EmployeeDAO eDAO;
    private static AccountDAO acctDAO;
    private static ApplicationDAO appDAO;
    private static TransactionDAO tDAO;
    private static UserDAO uDAO;

    private DAOFactory() {
    }

    public static EmployeeDAO getEmployeeDAO(){
        if(eDAO == null){
            eDAO = new EmployeeDAOImpl();
        }
        return eDAO;
    }

    public static UserDAO getUserDAO(){
        if(uDAO == null){
            uDAO = new UserDAOImpl();
        }
        return uDAO;
    }

    public static AccountDAO getAcctDAO(){
        if(acctDAO == null){
            acctDAO = new AccountDAOImpl();
        }
        return acctDAO;
    }

    public static ApplicationDAO getAppDAO(){
        if(appDAO == null){
            appDAO = new ApplicationDAOImpl();
        }
        return appDAO;
    }

    public static TransactionDAO getTransactionDAO(){
        if(tDAO == null){
            tDAO = new TransactionDAOImpl();
        }
        return tDAO;
    }

}
