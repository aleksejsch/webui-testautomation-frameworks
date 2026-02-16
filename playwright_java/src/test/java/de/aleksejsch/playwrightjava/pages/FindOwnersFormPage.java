package de.aleksejsch.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.aleksejsch.playwrightjava.dto.Owner;

public class FindOwnersFormPage extends BasePage {

    private final Locator lastNameInput = page.locator("#lastName");
    private final Locator findOwnerButton = page.locator("css=button[type='submit']");
    private final Locator addOwnerButton = page.locator("a.btn.btn-primary[href='/owners/new']");

    public FindOwnersFormPage(Page page) {
        super(page);
    }

    public void fillSearchForm(Owner owner) {
        type(lastNameInput, owner.lastName());
    }

    public void submit() {
        click(findOwnerButton);
    }

    public void clickButtonAddOwner(){
        click(addOwnerButton);
    }


}