package banking;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountsDatabase {

    private final String sqlUrl;

    public AccountsDatabase(String sqlUrl) {
        this.sqlUrl = sqlUrl;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            statement.executeUpdate(
                     "CREATE TABLE IF NOT EXISTS accounts (\n" + "    id INTEGER,\n" +
                     "    number TEXT,\n" + "    pin TEXT,\n" +
                     "    balance INTEGER DEFAULT 0\n" + ");\n");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(String number, String pin) {
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            statement.executeUpdate(
                     "INSERT INTO accounts (number, pin) VALUES ('" + number + "', '" +
                     pin + "');");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Account getById(int id) {
        Account acc = null;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement();
                 var result = statement.executeQuery(
                          "SELECT * FROM accounts WHERE id = ;" + id)) {
            while (result.next()) {
                acc = new Account(id, new Card(result.getString("number"),
                                               result.getString("pin")),
                                  result.getInt("balance"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return acc;
    }

    public void update(int id, Account data) {
        //TODO implement update
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Account getByNumber(String number) {
        //TODO implement getByNumber
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
