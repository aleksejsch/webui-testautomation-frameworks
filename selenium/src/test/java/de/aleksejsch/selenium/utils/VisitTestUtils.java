package de.aleksejsch.selenium.utils;

import de.aleksejsch.selenium.dto.Owner;
import de.aleksejsch.selenium.dto.Pet;
import de.aleksejsch.selenium.pages.VisitFormPage;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

import static de.aleksejsch.selenium.utils.TestUtils.buildUrl;

public class VisitTestUtils {

    public static VisitFormPage openVisitFormPage(WebDriver driver, Owner owner, Pet pet, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.VISIT_NEW.path().formatted(owner.id(), pet.id()), langCode, baseUrl);
        driver.get(url);
        return new VisitFormPage(driver);
    }

    public static VisitFormPage openVisitFormPage(WebDriver driver, Owner owner, Pet pet, String baseUrl) {
        return openVisitFormPage(driver, owner, pet, null, baseUrl);
    }

}
