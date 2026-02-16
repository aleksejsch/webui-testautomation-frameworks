package de.aleksejsch.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Pet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PetFormPage extends BasePage {

    private final Locator ownerNameLabel = page.locator("xpath=//label[text()='Owner']/following-sibling::div/span");
    private final Locator nameInput = page.locator("#name");
    private final Locator birthDateInput = page.locator("#birthDate");
    private final Locator typeSelect = page.locator("#type");
    private final Locator addPetButton = page.locator("xpath=//button[@type='submit']");
    private final Locator nameError = page.locator("xpath=//input[@id='name']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");
    private final Locator birthDateError = page.locator("xpath=//input[@id='birthDate']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter isoFormat = DateTimeFormatter.ISO_LOCAL_DATE;

    public PetFormPage(Page page) {
        super(page);
    }

    public void setPetName(String petName) {
        type(nameInput, petName);
    }


    public void setBirthDate(LocalDate localDate) {
        if (localDate != null) {
            type(birthDateInput, isoFormat.format(localDate));
        }else {
            type(birthDateInput, "");
        }

    }

    public void selectPetType(String type) {
        typeSelect.selectOption(type);
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
        return  nameInput.isVisible()
                && nameInput.isVisible()
                && birthDateInput.isVisible()
                && typeSelect.isVisible();
    }

}
