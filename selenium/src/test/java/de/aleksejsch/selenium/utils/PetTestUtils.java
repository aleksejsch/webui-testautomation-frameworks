package de.aleksejsch.selenium.utils;

import de.aleksejsch.selenium.dto.Owner;
import de.aleksejsch.selenium.dto.Pet;
import de.aleksejsch.selenium.pages.OwnerDetailsPage;
import de.aleksejsch.selenium.pages.PetFormPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;

import static de.aleksejsch.selenium.utils.TestUtils.buildUrl;

public class PetTestUtils {

    public static Pet addStandardPet(WebDriver driver, Owner owner, String baseUrl, Logger logger) {
        PetFormPage petFormPage = openPetFormPage(driver, owner, baseUrl);
        Pet newPet = Pet.builder().build();
        petFormPage.fillPetForm(newPet);
        petFormPage.submit();

        OwnerDetailsPage ownerDetailsPage = new OwnerDetailsPage(driver);
        Map<String,String> petData = ownerDetailsPage.getPetData();
        int petId = Integer.parseInt(petData.get("id"));

        //recreate the Pet-record with given ID
        newPet = Pet.builder().birthdate(newPet.birthdate()).name(newPet.name()).type(newPet.type()).id(petId).build();

        return newPet;
    }

    public static PetFormPage openPetFormPage(WebDriver driver, Owner owner, String langCode, String baseUrl) {
        String url = buildUrl(UrlPathsUtil.PET_NEW.path().formatted(owner.id()), langCode, baseUrl);
        driver.get(url);
        return new PetFormPage(driver);
    }

    public static PetFormPage openPetFormPage(WebDriver driver, Owner owner, String baseUrl) {
        return openPetFormPage(driver, owner, null, baseUrl);
    }
}
