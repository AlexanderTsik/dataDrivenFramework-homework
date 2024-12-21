package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.RegistrationPage;
import static com.codeborne.selenide.Selenide.open;


import static ge.tbc.testautomation.data.Constants.REGISTRATION_URL;

public class RegistrationSteps {

    private final RegistrationPage registrationPage = new RegistrationPage();

    public void openRegistrationPage() {
        open(REGISTRATION_URL);
    }

    public void fillFirstName(String firstName) {
        registrationPage.firstNameInput.setValue(firstName);
    }

    public void fillLastName(String lastName) {
        registrationPage.lastNameInput.setValue(lastName);
    }

    public void selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            registrationPage.genderMaleRadio.click();
        } else if ("female".equalsIgnoreCase(gender)) {
            registrationPage.genderFemaleRadio.click();
        }
    }

    public void selectModel(String model) {
        registrationPage.modelSelect.selectOptionContainingText(model);
    }

    public void fillAddress1(String address1) {
        registrationPage.address1Input.setValue(address1);
    }

    public void fillAddress2(String address2) {
        registrationPage.address2Input.setValue(address2);
    }

    public void fillCity(String city) {
        registrationPage.cityInput.setValue(city);
    }

    public void fillContact1(String contact1) {
        registrationPage.contact1Input.setValue(contact1);
    }

    public void fillContact2(String contact2) {
        registrationPage.contact2Input.setValue(contact2);
    }



    public void submitForm() {
        registrationPage.registerButton.click();
    }

    public void fillForm(String firstName, String lastName, String gender, String model, String address1, String address2, String city, String contact1, String contact2) {
        fillFirstName(firstName);
        fillLastName(lastName);
        selectGender(gender);
        selectModel(model);
        fillAddress1(address1);
        fillAddress2(address2);
        fillCity(city);
        fillContact1(contact1);
        fillContact2(contact2);
        submitForm();
    }
}
