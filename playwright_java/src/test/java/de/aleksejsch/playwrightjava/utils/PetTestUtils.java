package de.aleksejsch.playwrightjava.utils;

import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Owner;
import de.aleksejsch.playwrightjava.dto.Pet;
import de.aleksejsch.playwrightjava.pages.OwnerDetailsPage;
import de.aleksejsch.playwrightjava.pages.PetFormPage;
import org.slf4j.Logger;

import java.util.Map;

import static de.aleksejsch.playwrightjava.utils.TestUtils.buildUrl;

public class PetTestUtils {

    public static Pet addStandardPet(Page page, Owner owner, String baseUrl, Logger logger) {
        PetFormPage petFormPage = openPetFormPage(page, owner, baseUrl);
        Pet newPet = Pet.builder().build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();

        OwnerDetailsPage ownerDetailsPage = new OwnerDetailsPage(page);
        Map<String,String> petData = ownerDetailsPage.getPetData();
        int petId = Integer.parseInt(petData.get("id"));

        //recreate the Pet-record with given ID
        newPet = Pet.builder().birthdate(newPet.birthdate()).name(newPet.name()).type(newPet.type()).id(petId).build();

        return newPet;
    }

    public static PetFormPage openPetFormPage(Page page, Owner owner, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.PET_NEW.path().formatted(owner.id()), langCode, baseUrl);
        page.navigate(url);
        return new PetFormPage(page);
    }

    public static PetFormPage openPetFormPage(Page driver, Owner owner, String baseUrl) {
        return openPetFormPage(driver, owner, null, baseUrl);
    }
}
