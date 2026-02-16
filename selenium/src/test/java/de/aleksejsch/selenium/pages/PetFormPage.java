package de.aleksejsch.selenium.pages;

import de.aleksejsch.selenium.dto.Pet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PetFormPage extends BasePage {

    private final By ownerNameLabel = By.xpath("//label[text()='Owner']/following-sibling::div/span");
    private final By nameInput = By.id("name");
    private final By birthDateInput = By.id("birthDate");
    private final By typeSelect = By.id("type");
    private final By addPetButton = By.xpath("//button[@type='submit']");
    private final By nameError = By.xpath("//input[@id='name']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final By birthDateError = By.xpath("//input[@id='birthDate']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public PetFormPage(WebDriver driver) {
        super(driver);
    }

    public void setPetName(String petName) {
        type(nameInput, petName);
    }


    public void setBirthDate(LocalDate localDate) {
        if (localDate != null) {
            type(birthDateInput, dateTimeFormatter.format(localDate));
        }else {
            type(birthDateInput, "");
        }

    }

    public void selectPetType(String type) {
        Select select = new Select(waitForElement(typeSelect));
        select.selectByVisibleText(type);
    }

    public void submit() {
        click(addPetButton);
    }

    public String getNameError() {
        return getText(nameError);
    }

    public String getBirthdayError() {
        return getText(birthDateError);
    }

    public void fillPetForm(Pet pet) {
        setPetName(pet.name());
        setBirthDate(pet.birthdate());
        selectPetType(pet.type());
    }

    public boolean checkPetFormElementsExist(){
        return  elementExists(nameInput)
                && elementExists(nameInput)
                && elementExists(birthDateInput)
                && elementExists(typeSelect);
    }

}
