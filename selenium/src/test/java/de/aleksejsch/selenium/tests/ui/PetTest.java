package de.aleksejsch.selenium.tests.ui;

import de.aleksejsch.selenium.dto.Owner;
import de.aleksejsch.selenium.dto.Pet;
import de.aleksejsch.selenium.pages.OwnerDetailsPage;
import de.aleksejsch.selenium.pages.PetFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static de.aleksejsch.selenium.utils.OwnerTestUtils.*;
import static de.aleksejsch.selenium.utils.PetTestUtils.openPetFormPage;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PetTest extends BaseTest {

    private final static String MESSAGE_IS_REQUIRED = "is required";
    private final static String INVALID_DATE = "invalid date";

    @Test
    @DisplayName("Add new Pet and verify success")
    public void testAddPet() {
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);

        // Act
        PetFormPage petFormPage = openPetFormPage(driver, owner, baseUrl);
        Pet newPet = Pet.builder().build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();

        OwnerDetailsPage ownerDetailsPage = new OwnerDetailsPage(driver);
        Map<String, String> petData = ownerDetailsPage.getPetData();

        // Assert
        assertEquals(newPet.name(), petData.get("Name"));
        assertEquals(newPet.type(), petData.get("Type"));
        assertEquals(newPet.birthdate().toString(), petData.get("Birth Date"));
    }

    @Test
    @DisplayName("Check error messages if input fields are empty")
    public void testFormValidation() {
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);

        // Act
        PetFormPage petFormPage = openPetFormPage(driver, owner, baseUrl);
        Pet newPet = Pet.builder().birthdate(null).name("").build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();

        // Assert
        assertEquals(MESSAGE_IS_REQUIRED, petFormPage.getNameError());
        assertEquals(MESSAGE_IS_REQUIRED, petFormPage.getBirthdayError());
    }

    @Test
    @DisplayName("Check wrong birthdate error message")
    public void testBirthDateValidation() {
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);

        // Act
        PetFormPage petFormPage = openPetFormPage(driver, owner, baseUrl);
        Pet newPet = Pet.builder().birthdate(LocalDate.now().plusYears(1)).name("Pet").build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();

        // Assert
        assertEquals(INVALID_DATE, petFormPage.getBirthdayError());
    }

    @ParameterizedTest(name = "Validation errors for language: {0}")
    @MethodSource("languageAndExpectedMessages")
    @DisplayName("Show validation messages in correct language")
    void testValidationMessagesInCorrectLanguage(
            String langCode
    ) {
        // Arrange
        String requiredMessage = getExpectedMessage(langCode, "required");
        Owner owner = addStandardOwner(driver, baseUrl, logger);

        // Act
        PetFormPage petFormPage = openPetFormPage(driver, owner, langCode, baseUrl);
        Pet pet = Pet.builder().birthdate(null).name("").build();
        petFormPage.fillPetForm(pet);
        petFormPage.submit();

        // Assert
        assertEquals(requiredMessage, petFormPage.getNameError());
        assertEquals(requiredMessage, petFormPage.getBirthdayError());
    }

}
