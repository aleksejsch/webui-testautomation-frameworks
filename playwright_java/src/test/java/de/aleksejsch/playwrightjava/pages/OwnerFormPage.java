package de.aleksejsch.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Owner;

public class OwnerFormPage extends BasePage {

    private final Locator firstNameField = page.locator("#firstName");
    private final Locator lastNameField = page.locator("#lastName");
    private final Locator addressField = page.locator("#address");
    private final Locator cityField = page.locator("#city");
    private final Locator telephoneField = page.locator("#telephone");
    private final Locator submitButton = page.locator("css=button[type='submit']");
    private final Locator firstNameError = page.locator("xpath=//input[@id='firstName']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final Locator lastNameError = page.locator("xpath=//input[@id='lastName']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final Locator addressError = page.locator("xpath=//input[@id='address']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final Locator cityError = page.locator("xpath=//input[@id='city']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final Locator telephoneError = page.locator("xpath=//input[@id='telephone']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");



    public OwnerFormPage(Page page) {
        super(page);
    }

    public void fillOwnerForm(Owner owner) {
        type(firstNameField, owner.firstName());
        type(lastNameField, owner.lastName());
        type(addressField, owner.address());
        type(cityField, owner.city());
        type(telephoneField, owner.telephone());
    }

    public void submit() {
        click(submitButton);
    }

    public String firstNameError() {
        return getText(firstNameError);
    }

    public String lastNameError() {
        return getText(lastNameError);
    }

    public String addressError() {
        return getText(addressError);
    }

    public String cityError() {
        return getText(cityError);
    }

    public String telephoneError() {
        return getText(telephoneError);
    }

    public boolean checkOwnerFormElementsExist(){
        return  firstNameField.isVisible()
                && lastNameField.isVisible()
                && addressField.isVisible()
                && cityField.isVisible()
                && telephoneField.isVisible();
    }

}