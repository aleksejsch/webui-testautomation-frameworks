package de.aleksejsch.selenium.tests.ui;

import de.aleksejsch.selenium.config.EnvironmentConfig;
import de.aleksejsch.selenium.utils.DriverFactory;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.WebDriver;
import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected WebDriver driver;

    protected String baseUrl = EnvironmentConfig.getBaseUrl();

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Map<String, ResourceBundle> cachedBundles = new HashMap<>();

    protected static String getExpectedMessage(String lang, String key) {
        return cachedBundles
                .computeIfAbsent(lang, l -> ResourceBundle.getBundle("messages", new Locale(l)))
                .getString(key);
    }

    @BeforeEach
    protected void setup() {
        driver = DriverFactory.createDriver();
    }

    @AfterEach
    protected void tearDown() {
        if (driver != null) driver.quit();
    }



    protected static Stream<Arguments> languageAndExpectedMessages() {
        return Stream.of(
                Arguments.of("en"),
                Arguments.of("de")
                //,
        );
    }
}
