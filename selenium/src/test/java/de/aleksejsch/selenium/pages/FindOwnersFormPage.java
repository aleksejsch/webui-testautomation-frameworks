package de.aleksejsch.selenium.pages;

import de.aleksejsch.selenium.dto.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FindOwnersFormPage extends BasePage {

    private final By lastNameInput = By.id("lastName");
    private final By findOwnerButton = By.cssSelector("button[type='submit']");
    private final By addOwnerButton = By.cssSelector("a.btn.btn-primary[href='/owners/new']");

    public FindOwnersFormPage(WebDriver driver) {
        super(driver);
    }

    public FindOwnersFormPage fillSearchForm(Owner owner) {
        type(lastNameInput, owner.lastName());
        return this;
    }

    public void submit() {
        click(findOwnerButton);
    }

    public void clickButtonAddOwner(){
        click(addOwnerButton);
    }


}