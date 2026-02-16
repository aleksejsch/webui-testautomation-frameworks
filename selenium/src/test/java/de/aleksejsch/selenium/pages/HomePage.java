package de.aleksejsch.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
    private final By welcomeSelector = By.tagName("h2");

    public HomePage(WebDriver driver) {
        super(driver);
    }

	public String getWelcomeText() {
        return getText(welcomeSelector);
	}

}
