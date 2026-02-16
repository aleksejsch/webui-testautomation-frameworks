package de.aleksejsch.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage{
    private final Locator welcomeSelector = page.locator("h2");

    public HomePage(Page page) {
        super(page);
    }

	public String getWelcomeText() {
        return getText(welcomeSelector);
	}

}
