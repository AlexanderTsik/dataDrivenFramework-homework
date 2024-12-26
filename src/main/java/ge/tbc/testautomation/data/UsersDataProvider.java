package ge.tbc.testautomation.data;


import org.testng.annotations.DataProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDataProvider {
    private static final DatabaseSteps dbSteps = new DatabaseSteps();

    // DataProvider for fetching registration data with phone numbers
    @DataProvider(name = "registrationDataProvider")
    public static Object[][] registrationDataProvider() {
        List<String[]> data = dbSteps.fetchRegistrationsWithPhoneNumbers();
        Object[][] dataArray = new Object[data.size()][3]; // 3 columns: firstName, lastName, phoneNumber

        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i); // Populate data from the List
        }
        return dataArray;
    }
}

