package de.aleksejsch.playwrightjava.tests.ui;

import com.microsoft.playwright.Locator;
import de.aleksejsch.playwrightjava.dto.Owner;
import de.aleksejsch.playwrightjava.dto.Pet;
import de.aleksejsch.playwrightjava.pages.OwnerDetailsPage;
import de.aleksejsch.playwrightjava.pages.OwnerFormPage;
import de.aleksejsch.playwrightjava.pages.PetFormPage;
import de.aleksejsch.playwrightjava.pages.VisitFormPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.aleksejsch.playwrightjava.utils.OwnerTestUtils.addStandardOwner;
import static de.aleksejsch.playwrightjava.utils.OwnerTestUtils.openOwnerFormPage;
import static de.aleksejsch.playwrightjava.utils.PetTestUtils.openPetFormPage;
import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest extends BaseTest {


    @Test
    @DisplayName("Add new Owner and verify success")
    public void testAddNewOwner() {

        // Arrange
        OwnerFormPage formPage = openOwnerFormPage(page,  baseUrl);
        Owner owner = Owner.builder().build();
        formPage.fillOwnerForm(owner);
        formPage.submit();

        // Act
        OwnerDetailsPage detailsPage = new OwnerDetailsPage(page);

        //Assert
        assertEquals(owner.firstName() + " " + owner.lastName(), detailsPage.getOwnerName());
        assertEquals(owner.address(), detailsPage.getAddress());
        assertEquals(owner.city(), detailsPage.getCity());
        assertEquals(owner.telephone(), detailsPage.getTelephone());

        logger.info("Owner ID is: {}", detailsPage.getId());
    }

    @Test
    @DisplayName("Empty fields are not allowed")
    public void testEmptyFields() {
        // Arrange
        OwnerFormPage formPage = openOwnerFormPage(page, baseUrl);
        Owner owner = Owner.builder().firstName("").lastName("").city("").address("").telephone("").build();

        // Act
        formPage.fillOwnerForm(owner);
        formPage.submit();

        // Assert
        assertAll("Field validation errors",
                () -> assertEquals(getExpectedMessage("en", "field.required"), formPage.firstNameError()),
                () -> assertEquals(getExpectedMessage("en", "field.required"), formPage.lastNameError()),
                () -> assertEquals(getExpectedMessage("en", "field.required"), formPage.addressError()),
                () -> assertEquals(getExpectedMessage("en", "field.required"), formPage.cityError()),
                () -> {
                    String telephoneError = formPage.telephoneError();
                    assertTrue(telephoneError.contains(getExpectedMessage("en", "field.required")));
                    assertTrue(telephoneError.contains(getExpectedMessage("en", "telephone.invalid")));
                }
        );

        System.out.println("classpath=" + System.getProperty("java.class.path"));
    }

    @Test
    @DisplayName("Telephone field accepts only digits")
    public void testTelephoneAcceptsOnlyDigits() {

        // Arrange
        OwnerFormPage formPage = openOwnerFormPage(page, baseUrl);
        Owner owner = Owner.builder().telephone("aaaaaaaaaa").build();

        // Act
        formPage.fillOwnerForm(owner);
        formPage.submit();

        // Assert
        final String telephoneError = formPage.telephoneError();
        assertTrue(telephoneError.contains(getExpectedMessage("en", "telephone.invalid")));
    }

    @Test
    @DisplayName("Telephone field accepts only 10 digits")
    public void testTelephoneLength() {
        // Arrange
        OwnerFormPage formPage = openOwnerFormPage(page,  baseUrl);
        Owner owner = Owner.builder().telephone("000").build();

        // Act
        formPage.fillOwnerForm(owner);
        formPage.submit();

        //Assert
        final String telephoneError = formPage.telephoneError();
        assertTrue(telephoneError.contains(getExpectedMessage("en", "telephone.invalid")));
    }

    @ParameterizedTest(name = "Validation errors for language: {0}")
    @MethodSource("languageAndExpectedMessages")
    @DisplayName("Show validation messages in correct language")
    void testValidationMessagesInCorrectLanguage(
            String langCode
    ) {
        // Arrange
        String requiredMessage = getExpectedMessage(langCode, "field.required");
        String telephoneMessage = getExpectedMessage(langCode, "telephone.invalid");

        OwnerFormPage formPage = openOwnerFormPage(page, langCode, baseUrl);

        Owner owner = Owner.builder()
                .firstName("").lastName("").city("").address("").telephone("")
                .build();

        // Act
        formPage.fillOwnerForm(owner);
        formPage.submit();

        // Assert
        assertAll("Validation messages for lang: " + langCode,
                () -> assertEquals(requiredMessage, formPage.firstNameError()),
                () -> assertEquals(requiredMessage, formPage.lastNameError()),
                () -> assertEquals(requiredMessage, formPage.addressError()),
                () -> assertEquals(requiredMessage, formPage.cityError()),
                () -> {
                    String telError = formPage.telephoneError();
                    Set<String> expected = Set.of(telephoneMessage, requiredMessage);
                    Set<String> actualSet = Arrays.stream(telError.split("\\n"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toSet());
                    assertEquals(expected, actualSet, "Mismatch in unordered validation messages.");

                }
        );
    }


    @Test
    @DisplayName("Check that button 'add owner' works")
    public void testButtonAddOwnerWorks(){
        // Arrange
        OwnerFormPage formPage = openOwnerFormPage(page,  baseUrl);
        Owner owner = Owner.builder().build();
        formPage.fillOwnerForm(owner);
        formPage.submit();

        // Act
        OwnerDetailsPage detailsPage = new OwnerDetailsPage(page);
        detailsPage.clickEditOwner();

        // Assert
        OwnerFormPage ownerFormPage = new OwnerFormPage(page);
        assertTrue(ownerFormPage.checkOwnerFormElementsExist(), "Owner form element (s) is(are) not found");
    }

    @Test
    @DisplayName("Check that button 'add new pet' works")
    public void testButtonAddNewPetWorks(){
        // Arrange
        OwnerFormPage formPage = openOwnerFormPage(page,  baseUrl);
        Owner owner = Owner.builder().build();
        formPage.fillOwnerForm(owner);
        formPage.submit();

        // Act
        OwnerDetailsPage detailsPage = new OwnerDetailsPage(page);
        detailsPage.clickAddNewPet();

        // Assert
        PetFormPage petFormPage = new PetFormPage(page);
        assertTrue(petFormPage.checkPetFormElementsExist(), "Pet form element (s) is(are) not found");
    }

    @Test
    @DisplayName("Check that link 'Edit Pet' on the Owner details page works")
    public void testEditPetLink() {
        // Arrange
        Owner owner = addStandardOwner(page, baseUrl, logger);

        // Act
        PetFormPage petFormPage = openPetFormPage(page, owner, baseUrl);
        Pet newPet = Pet.builder().build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();
        OwnerDetailsPage ownerDetailsPage = new OwnerDetailsPage(page);
        Map<String, Locator> links = ownerDetailsPage.getEditPetAndAddVisitLinks();

        // Assert
        Locator link = links.get("Edit Pet");
        link.click(); //link Edit Pet
        assertTrue(petFormPage.checkPetFormElementsExist(), "Pet form elements are not found");

    }

    @Test
    @DisplayName("Check that link 'Add Visit' on the Owner details page works")
    public void testAddVisitLink() {
        // Arrange
        Owner owner = addStandardOwner(page, baseUrl, logger);

        // Act
        PetFormPage petFormPage = openPetFormPage(page, owner, baseUrl);
        Pet newPet = Pet.builder().build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();
        OwnerDetailsPage ownerDetailsPage = new OwnerDetailsPage(page);
        Map<String, Locator> links = ownerDetailsPage.getEditPetAndAddVisitLinks();

        // Assert
        Locator link = links.get("Add Visit");
        link.click(); //link Edit Pet
        assertTrue(new VisitFormPage(page).checkPetFormElementsExists(), "Visit form elements are not found");

    }
}

