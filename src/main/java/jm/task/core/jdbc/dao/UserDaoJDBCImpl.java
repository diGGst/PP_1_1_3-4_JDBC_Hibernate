package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private void executeUpdate(String sql) {
        try (Connection connection = UtilJDBC.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `users` " +
                "(`ID` INT NOT NULL AUTO_INCREMENT," +
                "`Name` VARCHAR(100) NULL," +
                "`LastName` VARCHAR(100) NULL," +
                " `Age` INT NULL," +
                "  PRIMARY KEY (`ID`))";

        executeUpdate(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS `users`";

        executeUpdate(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO `users` (`Name`, `LastName`, `Age`) VALUES ('" + name + "', '" + lastName + "', '" + age + "')";

        executeUpdate(sql);
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM `users` WHERE `ID`=" + id;

        executeUpdate(sql);
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM `users`";
        List<User> userList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = UtilJDBC.getMySQLConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM `users`";

        executeUpdate(sql);
    }
}
