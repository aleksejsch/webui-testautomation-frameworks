package de.aleksejsch.playwrightjava.tests.ui;

import de.aleksejsch.playwrightjava.dto.Owner;
import de.aleksejsch.playwrightjava.pages.FindOwnersFormPage;
import de.aleksejsch.playwrightjava.pages.OwnerDetailsPage;
import de.aleksejsch.playwrightjava.pages.OwnerFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.aleksejsch.playwrightjava.utils.OwnerTestUtils.addStandardOwner;
import static de.aleksejsch.playwrightjava.utils.OwnerTestUtils.openFindOwnerFormPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FindOwnerTest extends BaseTest {

    @Test
    @DisplayName("Search owner by last name")
    public void searchOwnerByLastName(){
        // Arrange
        Owner owner = addStandardOwner(page, baseUrl, logger);

        // Act
        FindOwnersFormPage findOwnersFormPage = openFindOwnerFormPage(page, baseUrl);
        findOwnersFormPage.fillSearchForm(owner);
        findOwnersFormPage.submit();

        // Assert
        OwnerDetailsPage detailsPage = new OwnerDetailsPage(page);
        assertEquals(owner.firstName() + " " + owner.lastName(), detailsPage.getOwnerName());
        assertEquals(owner.address(), detailsPage.getAddress());
        assertEquals(owner.city(), detailsPage.getCity());
        assertEquals(owner.telephone(), detailsPage.getTelephone());
    }

    @Test
    @DisplayName("Check that button 'add owner' works")
    public void testButtonAddOwnerWorks(){
        // Arrange
        FindOwnersFormPage findOwnersFormPage = openFindOwnerFormPage(page, baseUrl);

        // Act
        findOwnersFormPage.clickButtonAddOwner();

        // Assert
        OwnerFormPage ownerFormPage = new OwnerFormPage(page);
        assertTrue(ownerFormPage.checkOwnerFormElementsExist(), "Owner form element (s) is(are) not found");
    }


}
