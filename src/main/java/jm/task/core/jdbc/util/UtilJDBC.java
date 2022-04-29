package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UtilJDBC {

    public static Connection getMySQLConnection() throws SQLException {

        String hostName = "localhost";
        String dbName = "shema1";
        String userName = "root";
        String password = "1q2w3e4r";

        return DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbName, userName, password);
    }
}
