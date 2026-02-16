package de.aleksejsch.playwrightjava.utils;

import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Owner;
import de.aleksejsch.playwrightjava.pages.FindOwnersFormPage;
import de.aleksejsch.playwrightjava.pages.OwnerDetailsPage;
import de.aleksejsch.playwrightjava.pages.OwnerFormPage;
import org.slf4j.Logger;

import static de.aleksejsch.playwrightjava.utils.TestUtils.buildUrl;

public class OwnerTestUtils {

    public static Owner addStandardOwner(Page page, String baseUrl, Logger logger){
        OwnerFormPage formPage = openOwnerFormPage(page, null, baseUrl);
        Owner newOwner = Owner.builder().build();

        formPage.fillOwnerForm(newOwner);
        formPage.submit();

        OwnerDetailsPage detailsPage = new OwnerDetailsPage(page);
        //assertEquals("New Owner Created", detailsPage.getSuccesMessage());

        Owner created = detailsPage.getOwnerDetails();

        logger.info("New Owner Created : " + created.toString());
        return created;
    }

    public static OwnerFormPage openOwnerFormPage(Page page, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.OWNER_NEW.path(), langCode, baseUrl);
        page.navigate(url);
        return new OwnerFormPage(page);
    }

    public static OwnerFormPage openOwnerFormPage(Page page, String baseUrl) {
        return openOwnerFormPage(page, null, baseUrl);
    }

    public static FindOwnersFormPage openFindOwnerFormPage(Page page, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.OWNER_FIND.path(), langCode, baseUrl);
        page.navigate(url);
        return new FindOwnersFormPage(page);
    }

    public static FindOwnersFormPage openFindOwnerFormPage(Page page, String baseUrl) {
        return openFindOwnerFormPage(page, null, baseUrl);
    }

    public static OwnerDetailsPage openOwnerDetailsPage(Page page, Owner owner,  String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.OWNER_SHOW.path().formatted(owner.id()), langCode, baseUrl);
        page.navigate(url);
        return new OwnerDetailsPage(page);
    }

    public static OwnerDetailsPage openOwnerDetailsPage(Page page, Owner owner, String baseUrl) {
        return openOwnerDetailsPage(page, owner, null, baseUrl);
    }

}
