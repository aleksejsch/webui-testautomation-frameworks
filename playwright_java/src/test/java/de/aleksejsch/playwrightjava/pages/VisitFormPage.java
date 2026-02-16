package de.aleksejsch.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Visit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class VisitFormPage extends BasePage {


    private final Locator date = page.locator("#date");
    private final Locator description = page.locator("#description");
    private final Locator addVisitButton = page.locator("xpath=//button[@type='submit']");
    private final Locator descriptionError = page.locator("xpath=//input[@id='description']/ancestor::div[contains(@class,'form-group')]/div/span[@class='help-inline']");

    private final Locator  petName   = page.locator("xpath=//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[1]");
    private final Locator petBirthDate = page.locator("xpath=//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[2]");
    private final Locator  petType   = page.locator("xpath=//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[3]");
    private final Locator  petOwner     = page.locator("xpath=//b[normalize-space()='Pet']/following-sibling::table[contains(@class,'table-striped')]/tbody/tr[1]/td[4]");

    private final Locator  previousVisitDate     = page.locator("xpath=//b[normalize-space()='Previous Visits']/following::table[contains(@class,'table-striped')][1]//tr[2]/td[1]");
    private final Locator  previousVisitDescription    = page.locator("xpath=//b[normalize-space()='Previous Visits']/following::table[contains(@class,'table-striped')][1]//tr[2]/td[2]");

    private final DateTimeFormatter dateDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter localDateFormatter = ISO_LOCAL_DATE;

    public VisitFormPage(Page page) {
        super(page);
    }

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

    public void setDescription(String description) {
        type(this.description, description);
    }


    public void setDate(LocalDate localDate) {
        if (localDate != null) {
            type(date, localDateFormatter.format(localDate));
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
        return  date.isVisible()
                && description.isVisible()
                && addVisitButton.isVisible();
    }

}
