package de.aleksejsch.selenium.pages;

import de.aleksejsch.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final long LONG_WAIT_SECONDS = 10;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(LONG_WAIT_SECONDS));
    }

    protected WebElement waitForElement(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException | NoSuchElementException e) {
            String filename = "waitForElementFailed_" + locator.toString().replaceAll("[^a-zA-Z0-9]", "_");
            ScreenshotUtil.takeScreenshot(driver, filename);
            throw new RuntimeException("Element not found: " + locator, e);
        }
    }



    protected void click(By locator) {
        waitForElement(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    protected boolean elementExists(By locator) {
        try {
            return wait.until(d -> !d.findElements(locator).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }



}
