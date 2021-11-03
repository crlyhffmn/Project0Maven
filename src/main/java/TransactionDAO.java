import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {
    void addTransaction(Transaction transaction) throws SQLException;

    void updateTransaction(Transaction transaction) throws SQLException;

    void deleteTransaction(int id) throws SQLException;

    List<Transaction> getTransactions() throws SQLException;

    Transaction getTransactionById(int id) throws SQLException;

    List<Transaction> getTransactionsBySourceId(int id) throws SQLException;

    List<Transaction> getTransactionsByDestId(int id) throws SQLException;
}
