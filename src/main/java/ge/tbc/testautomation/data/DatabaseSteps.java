package ge.tbc.testautomation.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSteps {

    private Connection connection;

    public ResultSet getAllRegistrationData() {
        try {
            connection = MSSQLConnection.connect();
            String query = "SELECT * FROM RegistrationData";
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch data from RegistrationData table", e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
