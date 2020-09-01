package banking;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountsDatabase {

    private final String sqlUrl;

    public AccountsDatabase(String sqlUrl) {
        this.sqlUrl = sqlUrl;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            statement.execute(
                     "CREATE TABLE IF NOT EXISTS accounts (\n" + "    id INTEGER,\n" +
                     "    number TEXT,\n" + "    pin TEXT,\n" +
                     "    balance INTEGER DEFAULT 0\n" + ");\n");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(Account account) {
        //TODO implement add
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void getById(int id) {
        //TODO implement getById
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void update(int id, Account data) {
        //TODO implement update
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
