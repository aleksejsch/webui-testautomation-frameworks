package de.aleksejsch.selenium.pages;

import de.aleksejsch.selenium.dto.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OwnerFormPage extends BasePage {

    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By addressField = By.id("address");
    private final By cityField = By.id("city");
    private final By telephoneField = By.id("telephone");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By firstNameError = By.xpath("//input[@id='firstName']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final By lastNameError = By.xpath("//input[@id='lastName']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final By addressError = By.xpath("//input[@id='address']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final By cityError = By.xpath("//input[@id='city']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final By telephoneError = By.xpath("//input[@id='telephone']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");



    public OwnerFormPage(WebDriver driver) {
        super(driver);
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
        return  elementExists(firstNameField)
                && elementExists(lastNameField)
                && elementExists(addressField)
                && elementExists(cityField)
                && elementExists(telephoneField);
    }

}