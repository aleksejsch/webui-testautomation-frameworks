package de.aleksejsch.selenium.tests.ui;

import de.aleksejsch.selenium.dto.Owner;
import de.aleksejsch.selenium.pages.FindOwnersFormPage;
import de.aleksejsch.selenium.pages.OwnerDetailsPage;
import de.aleksejsch.selenium.pages.OwnerFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.aleksejsch.selenium.utils.OwnerTestUtils.addStandardOwner;
import static de.aleksejsch.selenium.utils.OwnerTestUtils.openFindOwnerFormPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FindOwnerTest extends BaseTest {

    @Test
    @DisplayName("Search owner by last name")
    public void searchOwnerByLastName(){
        // Arrange
        Owner owner = addStandardOwner(driver, baseUrl, logger);

        // Act
        FindOwnersFormPage findOwnersFormPage = openFindOwnerFormPage(driver, baseUrl);
        findOwnersFormPage.fillSearchForm(owner);
        findOwnersFormPage.submit();

        // Assert
        OwnerDetailsPage detailsPage = new OwnerDetailsPage(driver);
        assertEquals(owner.firstName() + " " + owner.lastName(), detailsPage.geOwnerName());
        assertEquals(owner.address(), detailsPage.getAddress());
        assertEquals(owner.city(), detailsPage.getCity());
        assertEquals(owner.telephone(), detailsPage.getTelephone());
    }

    @Test
    @DisplayName("Check that button 'add owner' works")
    public void testButtonAddOwnerWorks(){
        // Arrange
        FindOwnersFormPage findOwnersFormPage = openFindOwnerFormPage(driver, baseUrl);

        // Act
        findOwnersFormPage.clickButtonAddOwner();

        // Assert
        OwnerFormPage ownerFormPage = new OwnerFormPage(driver);
        assertTrue(ownerFormPage.checkOwnerFormElementsExist(), "Owner form element (s) is(are) not found");
    }


}
