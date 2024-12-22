package ge.tbc.testautomation.data;

import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ge.tbc.testautomation.data.Constants.NUMBER_OF_REGISTRATIONS_TO_ADD;

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

    public DatabaseSteps() {
        connection = MSSQLConnection.connect();
    }
    // Step 2.1: Create Phones table
    public DatabaseSteps createPhonesTable() {
        String query = "CREATE TABLE IF NOT EXISTS Phones (" +
                "phoneNumber VARCHAR(15) NOT NULL," +
                "ownerId INT NOT NULL," +
                "FOREIGN KEY (ownerId) REFERENCES RegistrationData(id))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create Phones table", e);
        }
        return this;
    }

    // Step 2.2: Insert multiple registrations
    public DatabaseSteps insertMultipleRegistrations() {
        String query = "INSERT INTO RegistrationData (id, firstName, lastName, gender, model, address1, address2, city, contact1, contact2) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String[] models = {"Mega 123 Large screen", "Mega 123 Medium screen", "Serene Pad 64G", "Serene Pad 32G"};

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 1; i <= NUMBER_OF_REGISTRATIONS_TO_ADD; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "FirstName" + i);
                preparedStatement.setString(3, "LastName" + i);
                preparedStatement.setString(4, i % 2 == 0 ? "male" : "female");
                preparedStatement.setString(5, models[i % models.length]);
                preparedStatement.setString(6, "Address1_" + i);
                preparedStatement.setString(7, "Address2_" + i);
                preparedStatement.setString(8, "City" + i);
                preparedStatement.setString(9, "Contact1_" + i);
                preparedStatement.setString(10, "Contact2_" + i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert multiple registrations", e);
        }
        return this;
    }

    // Step 2.3: Insert phone numbers
    public DatabaseSteps insertPhoneNumbers() {
        String query = "INSERT INTO Phones (phoneNumber, ownerId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 1; i <= 8; i++) {
                preparedStatement.setString(1, "555-000" + i);
                preparedStatement.setInt(2, i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert phone numbers", e);
        }
        return this;
    }

    // Step 2.4: Update two registrations and two phone numbers
    public DatabaseSteps updateRecords() {
        try {
            String updateRegistrationQuery = "UPDATE RegistrationData SET lastName = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateRegistrationQuery)) {
                preparedStatement.setString(1, "UpdatedLastName1");
                preparedStatement.setInt(2, 1);
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "UpdatedLastName2");
                preparedStatement.setInt(2, 2);
                preparedStatement.executeUpdate();
            }

            String updatePhoneQuery = "UPDATE Phones SET phoneNumber = ? WHERE phoneNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updatePhoneQuery)) {
                preparedStatement.setString(1, "555-9991");
                preparedStatement.setString(2, "555-0001");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "555-9992");
                preparedStatement.setString(2, "555-0002");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update records", e);
        }
        return this;
    }

    // Step 2.5: Fetch registrations with phone numbers (INNER JOIN)
    public List<String[]> fetchRegistrationsWithPhoneNumbers() {
        String query = "SELECT firstName, lastName, phoneNumber FROM RegistrationData INNER JOIN Phones ON RegistrationData.id = Phones.ownerId";
        List<String[]> data = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                data.add(new String[]{
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNumber")
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve data", e);
        }
        return data;
    }

    // Step 3: Insert with AutoCommit false and validate
    public DatabaseSteps insertWithTransactionAndValidate() {
        try {
            connection.setAutoCommit(false);

            // Insert a new row
            String query = "INSERT INTO RegistrationData (id, firstName, lastName, gender, model, address1, address2, city, contact1, contact2) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, 11);
                preparedStatement.setString(2, "John");
                preparedStatement.setString(3, "Doe");
                preparedStatement.setString(4, "male");
                preparedStatement.setString(5, "Mega 123 Large screen");
                preparedStatement.setString(6, "SomeAddress1");
                preparedStatement.setString(7, "SomeAddress2");
                preparedStatement.setString(8, "SomeCity");
                preparedStatement.setString(9, "123456789");
                preparedStatement.setString(10, "987654321");
                preparedStatement.executeUpdate();
            }

            // Validate the row wasn't created
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM RegistrationData WHERE id = 11");
            Assert.assertFalse(resultSet.next(), "Row should not exist before commit");

            // Commit the transaction
            connection.commit();

            // Validate the row was created
            resultSet = connection.createStatement().executeQuery("SELECT * FROM RegistrationData WHERE id = 11");
            Assert.assertTrue(resultSet.next(), "Row should exist after commit");

        } catch (SQLException e) {
            throw new RuntimeException("Transaction failed", e);
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException ignored) {
            }
        }
        return this;
    }

    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
