package ge.tbc.testautomation;

import ge.tbc.testautomation.data.DatabaseSteps;
import ge.tbc.testautomation.steps.RegistrationSteps;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class RegistrationTests {

    private final RegistrationSteps registrationSteps = new RegistrationSteps();
    private final DatabaseSteps dbSteps = new DatabaseSteps();

    @BeforeMethod
    public void setup() {
        System.setProperty("selenide.browser", "chrome");
    }

    @Test
    public void testRegistrationForm() {
        ResultSet resultSet = null;
        try {
            resultSet = dbSteps.getAllRegistrationData();
            if (!resultSet.isBeforeFirst()) {
                System.err.println("No data found in RegistrationData table.");
                return;
            }
            while (resultSet.next()) {
                registrationSteps.openRegistrationPage();
                registrationSteps.fillForm(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("model"),
                        resultSet.getString("address1"),
                        resultSet.getString("address2"),
                        resultSet.getString("city"),
                        resultSet.getString("contact1"),
                        resultSet.getString("contact2")
                );
            }
        } catch (SQLException e) {
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to process registration data", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbSteps.closeConnection();
        }
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
    }
}
