package de.aleksejsch.selenium.tests.ui;

import de.aleksejsch.selenium.dto.Owner;
import de.aleksejsch.selenium.dto.Pet;
import de.aleksejsch.selenium.dto.Visit;
import de.aleksejsch.selenium.pages.OwnerDetailsPage;
import de.aleksejsch.selenium.pages.VisitFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.util.List;



import static de.aleksejsch.selenium.utils.OwnerTestUtils.*;
import static de.aleksejsch.selenium.utils.PetTestUtils.addStandardPet;
import static de.aleksejsch.selenium.utils.VisitTestUtils.openVisitFormPage;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class VisitTest extends BaseTest {

    @DisplayName("Add multiple Visits to a Pet and verify both")
    @Test
    public void testAddMultipleVisitsToPetSuccessfully() {
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);
        Pet pet = addStandardPet(driver, owner, baseUrl, logger);

        Visit firstVisit = Visit.builder().build();
        addVisit(driver, owner, pet, baseUrl, firstVisit);

        Visit secondVisit = Visit.builder().build();
        addVisit(driver, owner, pet, baseUrl, secondVisit);

        // Act
        OwnerDetailsPage ownerDetailsPage = new OwnerDetailsPage(driver);
        List<Visit> actualVisits = ownerDetailsPage.getVisitData();

        // Assert
        List<Visit> expectedVisits = List.of(firstVisit, secondVisit);
        assertEquals(expectedVisits, actualVisits);
    }


    @DisplayName("Check the owner data on the visit form page")
    @Test
    public void testOwnerDataOnVisitFormPage() {
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);
        Pet pet = addStandardPet(driver, owner, baseUrl, logger);

        // Act
        VisitFormPage visitFormPage = openVisitFormPage(driver, owner, pet, baseUrl);

        // Assert
        assertEquals(pet.name(), visitFormPage.getPetName());
        assertEquals(pet.birthdate(), visitFormPage.getPetBirthDate());
        assertEquals(pet.type(), visitFormPage.getPetType());
        assertEquals(owner.firstName() + " "+  owner.lastName(), visitFormPage.getPetOwner());
    }

    @DisplayName("Check previous visit")
    @Test
    public void testPreviousVisitOnVisitFormPage() {
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);
        Pet pet = addStandardPet(driver, owner, baseUrl, logger);
        Visit firstVisit = Visit.builder().build();
        addVisit(driver, owner, pet, baseUrl, firstVisit);

        // Act
        VisitFormPage visitFormPage = openVisitFormPage(driver, owner, pet, baseUrl);

        // Assert
        assertEquals(firstVisit.description(), visitFormPage.getPreviousVisitDescription());
        assertEquals(firstVisit.date(), visitFormPage.getPreviousVisitDate());
    }


    @ParameterizedTest(name = "Validation errors for language: {0}")
    @MethodSource("languageAndExpectedMessages")
    @DisplayName("Show validation messages in correct language")
    void testValidationMessagesInCorrectLanguage(
            String langCode
    ) {
        // Arrange
        String requiredMessage = getExpectedMessage(langCode, "field.required");
        Owner owner = addStandardOwner(driver, baseUrl, logger);
        Pet pet = addStandardPet(driver, owner, baseUrl, logger);
        Visit visit = Visit.builder().description("").build();

        // Act
        VisitFormPage visitFormPage = openVisitFormPage(driver, owner, pet,langCode, baseUrl);
        visitFormPage.fillVisitForm(visit);
        visitFormPage.submit();

        // Assert
        assertEquals(requiredMessage, visitFormPage.getDescriptionError());
    }


    private void addVisit(WebDriver driver, Owner owner, Pet pet, String baseUrl, Visit visit) {
        OwnerDetailsPage ownerDetailsPage = openOwnerDetailsPage(driver,owner,baseUrl);
        VisitFormPage visitFormPage = openVisitFormPage(driver, owner, pet, baseUrl);
        visitFormPage.fillVisitForm(visit);
        visitFormPage.submit();
        ownerDetailsPage.waitForVisitsData();
    }

}
