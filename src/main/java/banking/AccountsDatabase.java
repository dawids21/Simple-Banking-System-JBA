package banking;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountsDatabase {

    private static final String TABLE_NAME = "card";

    private final String sqlUrl;

    public AccountsDatabase(String sqlUrl) {
        this.sqlUrl = sqlUrl;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                                    "    id INTEGER PRIMARY KEY,\n" +
                                    "    number TEXT,\n" + "    pin TEXT,\n" +
                                    "    balance INTEGER DEFAULT 0\n" + ");\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account add(CardGenerator generator) {
        Account acc = null;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            var result = statement.executeQuery(
                     "SELECT MAX(id) AS max_id FROM " + TABLE_NAME);
            int nextId = result.next() ? result.getInt("max_id") + 1 : 1;
            if (nextId > 0) {
                var card = generator.generate(nextId);
                statement.executeUpdate(
                         "INSERT INTO " + TABLE_NAME + " (number, pin) VALUES ('" +
                         card.getNumber() + "', '" + card.getPin() + "');");
                acc = new Account(nextId, card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public Account getById(int id) {
        Account acc = null;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement();
                 var result = statement.executeQuery(
                          "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id + ";")) {
            while (result.next()) {
                acc = new Account(id, new Card(result.getString("number"),
                                               result.getString("pin")),
                                  result.getInt("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public Account getByNumber(String number) {
        Account acc = null;
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement();
                 var result = statement.executeQuery(
                          "SELECT * FROM " + TABLE_NAME + " WHERE number = '" + number +
                          "';")) {
            while (result.next()) {
                acc = new Account(result.getInt("id"),
                                  new Card(result.getString("number"),
                                           result.getString("pin")),
                                  result.getInt("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public boolean exists(Account data) {

        var exist = false;

        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            var resultSet = statement.executeQuery(
                     "SELECT * FROM " + TABLE_NAME + " WHERE id = " + data.getId() +
                     " AND number = '" + data.getCard()
                                             .getNumber() + "' AND pin = '" +
                     data.getCard()
                         .getPin() + "';");
            exist = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public void update(Account data) {
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            statement.executeUpdate("UPDATE " + TABLE_NAME + "number = '" + data.getCard()
                                                                                .getNumber() +
                                    "', pin = '" + data.getCard()
                                                       .getPin() + "', balance = " +
                                    data.getBalance() + " WHERE id = " + data.getId() +
                                    "; ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try (var conn = DriverManager.getConnection(sqlUrl);
                 var statement = conn.createStatement()) {
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
