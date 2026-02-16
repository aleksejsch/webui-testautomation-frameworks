package de.aleksejsch.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BasePage {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }


    protected void click(Locator locator) {
        locator.click();
    }

    protected void type(Locator locator, String text) {
        locator.clear();
        locator.fill(text);
    }

    protected String getText(Locator locator) {
        return locator.innerText();
    }
}
