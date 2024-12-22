package ge.tbc.testautomation.data;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnection {

    public static Connection connect() {
        try {
            DriverManager.registerDriver(new SQLServerDriver());

            String dbUrl = DBConfiguration.getURL();
            String dbUsername = DBConfiguration.getUsername();
            String dbPassword = DBConfiguration.getPassword();

            //System.out.println(dbPassword + dbUsername + dbUrl);

            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }
}
