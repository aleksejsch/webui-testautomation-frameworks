package de.aleksejsch.selenium.pages;

import de.aleksejsch.selenium.dto.Visit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VisitFormPage extends BasePage {


    private final By date = By.id("date");
    private final By description = By.id("description");
    private final By addVisitButton = By.xpath("//button[@type='submit']");
    private final By descriptionError = By.xpath("//input[@id='description']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");

    private final By  petName   = By.xpath("//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[1]");
    private final By petBirthDate = By.xpath("//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[2]");
    private final By  petType   = By.xpath("//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[3]");
    private final By  petOwner     = By.xpath("//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[4]");

    private final By  previousVisitDate     = By.xpath("//b[normalize-space()='Previous Visits']/following::table[contains(@class,'table-striped')][1]//tr[2]/td[1]");
    private final By  previousVisitDescription    = By.xpath("//b[normalize-space()='Previous Visits']/following::table[contains(@class,'table-striped')][1]//tr[2]/td[2]");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getPreviousVisitDescription() {
        return getText(previousVisitDescription);
    }

    public LocalDate getPreviousVisitDate() {
        return LocalDate.parse(getText(previousVisitDate));
    }

    public String getPetName() {
        return getText(petName);
    }

    public LocalDate getPetBirthDate() {
        return LocalDate.parse(getText(petBirthDate));
    }

    public String getPetType() {
        return getText(petType);
    }

    public String getPetOwner() {
        return getText(petOwner);
    }

    public VisitFormPage(WebDriver driver) {
        super(driver);
    }

    public void setDescription(String description) {
        type(this.description, description);
    }


    public void setDate(LocalDate localDate) {
        if (localDate != null) {
            type(date, dateTimeFormatter.format(localDate));
        } else {
            type(date, "");
        }
    }

    public void submit() {
        click(addVisitButton);
    }

    public String getDescriptionError() {
        return getText(descriptionError);
    }


    public void fillVisitForm(Visit visit) {
        setDescription(visit.description());
        setDate(visit.date());
    }

    public boolean checkPetFormElementsExists(){
        return  elementExists(date)
                && elementExists(description)
                && elementExists(addVisitButton);
    }

}
