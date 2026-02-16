package de.aleksejsch.selenium.utils;

import de.aleksejsch.selenium.dto.Owner;
import de.aleksejsch.selenium.pages.FindOwnersFormPage;
import de.aleksejsch.selenium.pages.OwnerDetailsPage;
import de.aleksejsch.selenium.pages.OwnerFormPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import static de.aleksejsch.selenium.utils.TestUtils.buildUrl;

public class OwnerTestUtils {

    public static Owner addStandardOwner(WebDriver driver, String baseUrl, Logger logger){
        OwnerFormPage formPage = openOwnerFormPage(driver, null, baseUrl);
        Owner newOwner = Owner.builder().build();

        formPage.fillOwnerForm(newOwner);
        formPage.submit();

        OwnerDetailsPage detailsPage = new OwnerDetailsPage(driver);
        //assertEquals("New Owner Created", detailsPage.getSuccesMessage());

        Owner created = detailsPage.getOwnerDetails();

        logger.info("New Owner Created : " + created.toString());
        return created;
    }

    public static OwnerFormPage openOwnerFormPage(WebDriver driver, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.OWNER_NEW.path(), langCode, baseUrl);
        driver.get(url);
        return new OwnerFormPage(driver);
    }

    public static OwnerFormPage openOwnerFormPage(WebDriver driver, String baseUrl) {
        return openOwnerFormPage(driver, null, baseUrl);
    }

    public static FindOwnersFormPage openFindOwnerFormPage(WebDriver driver, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.OWNER_FIND.path(), langCode, baseUrl);
        driver.get(url);
        return new FindOwnersFormPage(driver);
    }

    public static FindOwnersFormPage openFindOwnerFormPage(WebDriver driver, String baseUrl) {
        return openFindOwnerFormPage(driver, null, baseUrl);
    }

    public static OwnerDetailsPage openOwnerDetailsPage(WebDriver driver, Owner owner,  String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.OWNER_SHOW.path().formatted(owner.id()), langCode, baseUrl);
        driver.get(url);
        return new OwnerDetailsPage(driver);
    }

    public static OwnerDetailsPage openOwnerDetailsPage(WebDriver driver, Owner owner, String baseUrl) {
        return openOwnerDetailsPage(driver, owner, null, baseUrl);
    }

}
