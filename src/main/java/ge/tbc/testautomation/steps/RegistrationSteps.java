package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.DatabaseSteps;
import ge.tbc.testautomation.pages.RegistrationPage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ge.tbc.testautomation.data.Constants.REGISTRATION_URL;

public class RegistrationSteps {

    private final DatabaseSteps dbSteps = new DatabaseSteps();


    public void processRegistrationForm() {
        ResultSet resultSet = null;
        try {
            resultSet = dbSteps.getAllRegistrationData();
            if (!resultSet.isBeforeFirst()) {
                System.err.println("No data found in RegistrationData table.");
                return;
            }
            while (resultSet.next()) {
                openRegistrationPage();
                fillForm(
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

    public void openRegistrationPage() {
        open(REGISTRATION_URL);
    }

    public void fillForm(String firstName, String lastName, String gender, String model, String address1, String address2, String city, String contact1, String contact2) {
        RegistrationPage registrationPage = new RegistrationPage();

        registrationPage.firstNameInput.setValue(firstName);
        registrationPage.lastNameInput.setValue(lastName);

        if ("male".equalsIgnoreCase(gender)) {
            registrationPage.genderMaleRadio.click();
        } else if ("female".equalsIgnoreCase(gender)) {
            registrationPage.genderFemaleRadio.click();
        }

        registrationPage.modelSelect.selectOptionContainingText(model);
        registrationPage.address1Input.setValue(address1);
        registrationPage.address2Input.setValue(address2);
        registrationPage.cityInput.setValue(city);
        registrationPage.contact1Input.setValue(contact1);
        registrationPage.contact2Input.setValue(contact2);

        registrationPage.registerButton.click();
    }

}
