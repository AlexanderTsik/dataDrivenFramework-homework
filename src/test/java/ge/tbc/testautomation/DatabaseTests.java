package ge.tbc.testautomation;

import ge.tbc.testautomation.data.DatabaseSteps;
import ge.tbc.testautomation.data.UsersDataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DatabaseTests {

    private DatabaseSteps dbSteps;

    @BeforeMethod
    public void setup() {
        dbSteps = new DatabaseSteps(); // Initialize the DatabaseSteps object
    }

    // Test 1: Create tables and populate data
    @Test
    public void testCreateAndPopulateData() {
        dbSteps//.createPhonesTable()
                .insertMultipleRegistrations()
                .insertPhoneNumbers()
                .updateRecords(); // Create table, insert records, and update records
    }

    // Test 2: Fetch and print registrations with phone numbers using DataProvider
    @Test(dataProvider = "registrationDataProvider", dataProviderClass = UsersDataProvider.class)
    public void testRegistrationsWithPhoneNumbers(String firstName, String lastName, String phoneNumber) {
        System.out.printf("Name: %s %s, Phone: %s%n", firstName, lastName, phoneNumber);

        // Assertions for validation
        assert firstName != null && !firstName.isEmpty() : "First name should not be null or empty";
        assert lastName != null && !lastName.isEmpty() : "Last name should not be null or empty";
        assert phoneNumber != null && !phoneNumber.isEmpty() : "Phone number should not be null or empty";
    }

    // Test 3: Test transaction logic with auto-commit disabled
    @Test
    public void testTransactionAndValidation() {
        dbSteps.insertWithTransactionAndValidate(); // Insert a row with transaction handling
    }

    @Test
    public void testUpdateLastNameAndValidate() {
        dbSteps.updateLastNameAndValidate(1, "UpdatedName1");
    }

    @AfterMethod
    public void tearDown() {
        dbSteps.closeConnection(); // Close the database connection after each test
    }
}
