package de.aleksejsch.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static WebDriver createDriver() {
        String browser = System.getProperty("test.browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("browser.headless", "false"));

        logger.info("Creating WebDriver for browser: {}, headless: {}", browser, headless);

        return switch (browser) {
            case "firefox" -> createFirefoxDriver(headless);
            case "chrome" -> createChromeDriver(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
            logger.debug("Chrome started in headless mode");
        }
        logger.info("ChromeDriver initialized");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
            logger.debug("Firefox started in headless mode");
        }
        logger.info("FirefoxDriver initialized");
        return new FirefoxDriver(options);
    }
}

