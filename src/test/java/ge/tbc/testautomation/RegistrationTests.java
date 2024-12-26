package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.RegistrationSteps;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class RegistrationTests {

    private final RegistrationSteps registrationSteps = new RegistrationSteps();

    @BeforeMethod
    public void setup() {
        System.setProperty("selenide.browser", "chrome");
    }

    @Test
    public void testRegistrationForm() {
        registrationSteps.processRegistrationForm();
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
    }
}
