package de.aleksejsch.playwrightjava.utils;

import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Owner;
import de.aleksejsch.playwrightjava.dto.Pet;
import de.aleksejsch.playwrightjava.pages.VisitFormPage;

import static de.aleksejsch.playwrightjava.utils.TestUtils.buildUrl;

public class VisitTestUtils {

    public static VisitFormPage openVisitFormPage(Page page, Owner owner, Pet pet, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.VISIT_NEW.path().formatted(owner.id(), pet.id()), langCode, baseUrl);
        page.navigate(url);
        return new VisitFormPage(page);
    }

    public static VisitFormPage openVisitFormPage(Page page, Owner owner, Pet pet, String baseUrl) {
        return openVisitFormPage(page, owner, pet, null, baseUrl);
    }

}
