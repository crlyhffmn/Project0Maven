import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class BankApplicationMain {
    private static Connection connection;
    private static Scanner scanner;
    private static EmployeeDAO eDAO = DAOFactory.getEmployeeDAO();
    private static UserDAO uDAO = DAOFactory.getUserDAO();
    private static AccountDAO acctDAO = DAOFactory.getAcctDAO();
    private static ApplicationDAO appDAO = DAOFactory.getAppDAO();
    private static TransactionDAO tDAO = DAOFactory.getTransactionDAO();

    public static void main(String[] args) throws SQLException {
        //Create tables
        connection = ConnectionFactory.getConnection();
        createTables();

        //Initialize scanner
        scanner = new Scanner(System.in);

        welcome();
    }

    public static void welcome() throws SQLException {
        System.out.println("Welcome to the bank!");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("Please select an option:\n1: Login\n2: Apply for User Account\n3: Quit");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You have entered an invalid value. Please enter an integer from 1 to 3.");
            System.out.println("Press Enter to continue");
            try{System.in.read();}
            catch(Exception e2){}
        }
        switch(choice) {
            case 1:
                userLogin();
                break;
            case 2:
                applyForUserAccount();
                break;
            case 3:
                break;
            default:
                welcome();
                break;
        }
    }

    public static void userLogin() throws SQLException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        User user = uDAO.getUserByUsername(username);
        while(user == null) {
            System.out.println("Sorry, that username does not exist.");
            System.out.println("Please try again.");
            System.out.print("Username: ");
            username = scanner.nextLine();
            user = uDAO.getUserByUsername(username);
        }
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        while(!pass.equals(user.getPassword())) {
            System.out.println("Incorrect password");
            System.out.println("Please try again.");
            System.out.print("Password: ");
            pass = scanner.nextLine();
        }
        if(eDAO.getEmployeeByUserId(user.getId()) != null) {
            employeeMenu();
        } else {
            System.out.println("Welcome " + user.getFirstName());
            customerMenu(user);
        }
    }

    public static void applyForUserAccount() throws SQLException {
        System.out.print("First name: ");
        String fName = scanner.nextLine();
        System.out.print("Last name: ");
        String lName = scanner.nextLine();
        System.out.print("Username: ");
        String user = scanner.nextLine();
        String repeats = "select count(*) from users where userName = \"" + user + "\";";
        Statement statement = connection.createStatement();
        ResultSet r = statement.executeQuery(repeats);
        r.next();
        int exists = r.getInt("count(*)");
        while(exists > 0) {
            System.out.print("Sorry, that username is taken. Please choose a different one: ");
            user = scanner.nextLine();
            repeats = "select count(*) from users where userName = \"" + user + "\";";
            r = statement.executeQuery(repeats);
            r.next();
            exists = r.getInt("count(*)");
        }
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        User nUser = new User();
        nUser.setUserName(user);
        nUser.setPassword(pass);
        nUser.setFirstName(fName);
        nUser.setLastName(lName);
        uDAO.addUser(nUser);
        User addedUser = uDAO.getUserByUsername(user);
        System.out.println("You have successfully created an account!");
        System.out.println("Press Enter to continue");
        try{System.in.read();}
        catch(Exception e){}
        customerMenu(addedUser);
    }

    public static void employeeMenu() throws SQLException {
        //Approve or reject an acct.
        //View customer's bank accts.
        //View log of all transactions
        System.out.println("Employee account menu options: \n1: Manage account applications\n2: View customer bank accounts\n3: View transaction log\n4: Log out\n5: Quit");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You have entered an invalid value. Please enter an integer from 1 to 3.");
            System.out.println("Press Enter to continue");
            try{System.in.read();}
            catch(Exception e2){}
        }
        switch(choice) {
            case 1:
                System.out.println("Here is the list of unapproved applications: ");
                //Fix this to be accounts, not users
                List<Application> apps = appDAO.getUnapprovedApplications();
                for(Application a : apps) {
                    System.out.println(a);
                }
                System.out.println("Please enter the ID number of the application you would like to update(Approve or Deny): ");
                int appID = scanner.nextInt();
                scanner.nextLine();
                Application a = appDAO.getApplicationById(appID);
                while(a == null) {
                    System.out.println("Invalid ID number entered.");
                    System.out.println("Please enter the ID number of the application you would like to update(Approve or Deny): ");
                    appID = scanner.nextInt();
                    scanner.nextLine();
                    a = appDAO.getApplicationById(appID);
                }
                System.out.println("What action would you like to perform?\n1: Approve\n2: Deny");
                int action = scanner.nextInt();
                scanner.nextLine();
                switch(action) {
                    case 1:
                        a.setApproved(true);
                        appDAO.updateApplication(a);
                        System.out.println("Application approved");
                        Account newAccount = new Account();
                        newAccount.setBalance(a.getDepositAmt());
                        newAccount.setUserID(a.getUserID());
                        acctDAO.addAccount(newAccount);
                        System.out.println("Press Enter to continue");
                        try{System.in.read();}
                        catch(Exception e){}
                        break;
                    case 2:
                        appDAO.deleteApplication(appID);
                        System.out.println("Application denied");
                        System.out.println("Press Enter to continue");
                        try{System.in.read();}
                        catch(Exception e){}
                        break;
                }
                employeeMenu();
                break;
            case 2: //View customer bank accounts
                System.out.println("Here is the list of customer accounts: ");
                List<User> customers = uDAO.getUsers();
                for(User c : customers) {
                    System.out.println(c);
                }
                System.out.println("Please enter the ID number of the customer whose accounts you would like to view: ");
                int userID = scanner.nextInt();
                scanner.nextLine();
                User c = uDAO.getUserById(userID);
                List<Account> accounts = acctDAO.getAccountsByUser(c.getId());
                for(Account acct : accounts) {
                    System.out.println(acct);
                }
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e){}
                break;
            case 3: //View transaction log
                List<Transaction> transactions = tDAO.getTransactions();
                for(Transaction t : transactions) {
                    System.out.println(t);
                }
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e){}
                employeeMenu();
                break;
            case 4: //Logout
                welcome();
                break;
            case 5://Quit
                break;
            default:
                employeeMenu();
                break;
        }
    }

    public static void showAccounts(User user) throws SQLException {
        System.out.println("Here is a list of your accounts: ");
        List<Account> accts = acctDAO.getAccountsByUser(user.getId());
        for(Account a : accts) {
            System.out.println(a);
        }
    }

    public static void customerMenu(User user) throws SQLException {
        //Apply for bank acct w/ starting balance
        //View acct. balance
        //Withdrawal from acct.
        //Deposit into acct.
        //Post money transfer to another acct.
        //accept money transfer from another acct.
        System.out.println("Customer account menu options: \n1: Apply for a new account\n2: View account balance(s)\n3: Withdraw from an account\n4: Deposit into an account\n5: Transfer money\n6: Approve money transfers\n7: Log out");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch(choice) {
            case 1: //Apply for new account
                System.out.print("How much money do you wish to use as an initial deposit? $");
                int deposit = scanner.nextInt();
                scanner.nextLine();
                Application application = new Application();
                application.setUserID(user.getId());
                application.setDepositAmt(deposit);
                application.setApproved(false);
                appDAO.addApplication(application);
                System.out.println("Thank you, your account application will be reviewed by an employee...");
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e){}
                customerMenu(user);
                break;
            case 2: //View account balance
                showAccounts(user);
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e){}
                customerMenu(user);
                break;
            case 3: //Withdrawal
                showAccounts(user);
                System.out.println("From which account would you like to make a withdraw? account ID: ");
                int acctID = scanner.nextInt();
                scanner.nextLine();
                Account wAcct = acctDAO.getAccountById(acctID);
                Transaction withdrawal = new Transaction();
                withdrawal.setTransDescription("Withdraw");
                withdrawal.setDestAcctID(1);
                withdrawal.setSourceAcctID(acctID);
                withdrawal.setApproved(true);
                System.out.print("Please enter amount to be withdrawn: $");
                int withdrawAmt = scanner.nextInt();
                scanner.nextLine();
                if(wAcct.getBalance() - withdrawAmt < 0) {
                    System.out.println("Sorry, you cannot overdraw your account.");
                } else if(withdrawAmt < 0) {
                    System.out.println("Sorry, you cannot withdraw negative amounts.");
                } else {
                    withdrawal.setAmount(withdrawAmt);
                    tDAO.addTransaction(withdrawal);
                    wAcct.setBalance(wAcct.getBalance() - withdrawAmt);
                    acctDAO.updateAccount(wAcct);
                    System.out.println("Withdraw success!");
                }
                System.out.println("Press Enter to return to the user menu");
                try{System.in.read();}
                catch(Exception e){}
                customerMenu(user);
                break;
            case 4: //Deposit
                showAccounts(user);
                Transaction dep = new Transaction();
                dep.setTransDescription("Deposit");
                System.out.println("Into which account would you like to make a deposit? account ID: ");
                int depID = scanner.nextInt();
                scanner.nextLine();
                Account dAcct = acctDAO.getAccountById(depID);
                Transaction depositTransaction = new Transaction();
                depositTransaction.setTransDescription("Deposit");
                System.out.print("Please enter amount to be deposited: $");
                int depositAmt = scanner.nextInt();
                scanner.nextLine();
                if(depositAmt < 0) {
                    System.out.println("Cannot deposit negative money");
                } else {
                    depositTransaction.setAmount(depositAmt);
                    depositTransaction.setSourceAcctID(1);
                    depositTransaction.setDestAcctID(depID);
                    depositTransaction.setApproved(true);
                    tDAO.addTransaction(depositTransaction);
                    dAcct.setBalance(dAcct.getBalance() + depositAmt);
                    acctDAO.updateAccount(dAcct);
                    System.out.println("Deposit success!");
                }
                System.out.println("Press Enter to return to the user menu");
                try{System.in.read();}
                catch(Exception e){}
                customerMenu(user);
                break;
            case 5: //Transfer
                showAccounts(user);
                System.out.print("From which account would you like to transfer money? Account ID: ");
                int srcID = scanner.nextInt();
                scanner.nextLine();
                Account sAcct = acctDAO.getAccountById(srcID);
                Transaction transfer = new Transaction();
                transfer.setTransDescription("Transfer");
                transfer.setSourceAcctID(srcID);
                System.out.print("Please enter amount to be transferred: $");
                int transferAmt = scanner.nextInt();
                scanner.nextLine();
                if(transferAmt > sAcct.getBalance()) {
                    System.out.println("You do not have enough money to complete this transfer :(");
                    customerMenu(user);
                } else {
                    System.out.print("Please enter the account ID of where you would like to transfer your money. Account ID: ");
                    int destID = scanner.nextInt();
                    scanner.nextLine();
                    Account dstAcct = acctDAO.getAccountById(destID);
                    transfer.setDestAcctID(destID);
                    transfer.setAmount(transferAmt);
                    transfer.setApproved(false);
                    tDAO.addTransaction(transfer);
                    System.out.println("Transfer pending approval by the destination account user...");
                    System.out.println("Press Enter to return to the user menu");
                    try{System.in.read();}
                    catch(Exception e){}
                    customerMenu(user);
                    break;
                }
                break;
            case 6: //Approve transfers
                List<Account> usersAccounts = acctDAO.getAccountsByUser(user.getId());
                for(Account userAcct: usersAccounts) {
                    List<Transaction> transfers = tDAO.getTransactionsByDestId(userAcct.getId());
                    for(Transaction t: transfers) {
                        System.out.println(t);
                    }
                }
                System.out.print("Enter the transaction ID for the transaction you would like to Approve/Deny: ");
                int tID = scanner.nextInt();
                scanner.nextLine();
                System.out.println("What would you like to do? \n1: Approve\n2: Deny");
                int choice1 = scanner.nextInt();
                scanner.nextLine();
                switch (choice1) {
                    case 1:
                        Transaction relevantTransaction = tDAO.getTransactionById(tID);
                        Account src = acctDAO.getAccountById(relevantTransaction.getSourceAcctID());
                        Account dst = acctDAO.getAccountById(relevantTransaction.getDestAcctID());
                        if(!(src.getBalance() - relevantTransaction.getAmount() < 0)) {
                            src.setBalance(src.getBalance() - relevantTransaction.getAmount());
                            acctDAO.updateAccount(src);
                            dst.setBalance((dst.getBalance() + relevantTransaction.getAmount()));
                            acctDAO.updateAccount(dst);
                            System.out.println("Transaction approved and completed!");
                        } else {
                            System.out.println("Transaction unsuccessful, source account balance insufficient.");
                        }
                        System.out.println("Press Enter to return to the user menu");
                        try{System.in.read();}
                        catch(Exception e){}
                        customerMenu(user);
                        break;
                    case 2:
                        System.out.println("Deleting transaction...");
                        tDAO.deleteTransaction(tID);
                        System.out.println("Transaction deleted.");
                        System.out.println("Press Enter to return to the user menu");
                        try{System.in.read();}
                        catch(Exception e){}
                        customerMenu(user);
                        break;
                    default:
                        break;
                }

                break;
            case 7: //Log out
                welcome();
                break;
            default:
                break;
        }
    }

    //Initializes needed tables in the database (if they aren't already)
    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        if(!tableExists("employees")) {
            String createEmp = "create table employees (empID integer primary key auto_increment, userID integer, isCustomer boolean);";
            statement.executeUpdate(createEmp);
        }

        if(!tableExists("users")) {
            String createUsers = "create table users (userID integer primary key auto_increment, firstName char(20), lastName char(50), userName char(20), password char(50), isCustomer boolean);";
            statement.executeUpdate(createUsers);
            String fkUserID = "alter table employees add foreign key (userID) references users(userID);";
            statement.executeUpdate(fkUserID);
        }

        if(!tableExists("applications")) {
            String createApps = "create table applications (applicationID integer primary key auto_increment, userID integer, depositAmt integer, approved boolean);";
            statement.executeUpdate(createApps);
            String fkUserID = "alter table applications add foreign key (userID) references users(userID);";
            statement.executeUpdate(fkUserID);
        }

        if(!tableExists("accounts")) {
            String createAccounts = "create table accounts (acctID integer primary key auto_increment, userID integer, balance integer);";
            statement.executeUpdate(createAccounts);
            String fkUserID = "alter table accounts add foreign key (userID) references users(userID);";
            statement.executeUpdate(fkUserID);
        }

        if(!tableExists("transactions")) {
            String createTrans = "create table transactions (transID integer primary key auto_increment, sourceAcctID integer, destAcctID integer, amount integer, transDescription char(50), approved boolean);";
            statement.executeUpdate(createTrans);
            String fkAcctID = "alter table transactions add foreign key (sourceAcctID) references accounts(acctID);";
            statement.executeUpdate(fkAcctID);
            fkAcctID = "alter table transactions add foreign key (destAcctID) references accounts(acctID);";
            statement.executeUpdate(fkAcctID);
        }

        String query = "select count(*) from users where userName = \"admin\";";
        ResultSet r = statement.executeQuery(query);
        r.next();
        int admin = r.getInt("count(*)");
        if(admin == 0) {
            String sql = "insert into users (userName, password) values (\"admin\", \"supersecure\");";
            statement.executeUpdate(sql);
            sql = "insert into employees (userID) values (1);";
            statement.executeUpdate(sql);
        }
    }

    //Checks if a table is already present in the database
    //Returns: true if it exists, false otherwise
    public static boolean tableExists(String tableName) throws SQLException {
        String query = "select count(*) from information_schema.tables where table_schema = database() and table_name = \"" + tableName + "\";";
        Statement statement = connection.createStatement();
        ResultSet r = statement.executeQuery(query);
        r.next();
        int exists = r.getInt("count(*)");
        return exists == 1;
    }
}
