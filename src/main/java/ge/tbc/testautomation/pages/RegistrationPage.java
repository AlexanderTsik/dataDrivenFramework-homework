package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    // Input fields
    public SelenideElement firstNameInput = $x("//input[@value='First Name']");
    public SelenideElement lastNameInput = $x("//input[@value='Last Name']");
    public SelenideElement address1Input = $x("//input[@value='Address1']");
    public SelenideElement address2Input = $x("//input[@value='Address2']");
    public SelenideElement cityInput = $x("//input[@value='City']");
    public SelenideElement contact1Input = $x("//input[@value='Contact1']");
    public SelenideElement contact2Input = $x("//input[@value='Contact2']");

    // Radio buttons
    public SelenideElement genderMaleRadio = $x("//input[@name='gender'][@value='male']");
    public SelenideElement genderFemaleRadio = $x("//input[@name='gender'][@value='female']");

    // Dropdowns
    public SelenideElement modelSelect = $x("//select[@name='model']");

    // Checkbox
    public SelenideElement deliveryCheckbox = $x("//input[@name='DeliveryLoc'][@value='Yes']");

    // Submit button
    public SelenideElement registerButton = $x("//input[@value='Register']");
}
