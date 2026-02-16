package de.aleksejsch.playwrightjava.tests.ui;

import com.microsoft.playwright.*;
import de.aleksejsch.playwrightjava.config.EnvironmentConfig;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.params.provider.Arguments;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest implements TestWatcher {

    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;
    // New instance for each test method.
    BrowserContext context;
    Page page;
    protected static String baseUrl = EnvironmentConfig.getBaseUrl();
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Map<String, ResourceBundle> cachedBundles = new HashMap<>();
    static boolean headless = Boolean.parseBoolean(System.getProperty("browser.headless", "false"));
    static String browserName = System.getProperty("test.browser", "chromium");


    protected static String getExpectedMessage(String lang, String key) {
        return cachedBundles
                .computeIfAbsent(lang, l -> ResourceBundle.getBundle("messages", Locale.of(l)))
                .getString(key);
    }

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless);

        if (browserName.equals("chromium")) {
            browser = playwright.chromium().launch(launchOptions);
        } else if (browserName.equals("firefox")) {
            browser = playwright.firefox().launch(launchOptions);
        } else if (browserName.equals("webkit")) {
            browser = playwright.webkit().launch(launchOptions);
        }else
            browser = playwright.chromium().launch(launchOptions);

    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("Test failed", cause);
        if (page != null) {
            String testName = context.getDisplayName()
                    .replaceAll("[^a-zA-Z0-9_-]", "_");
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots/" + testName + ".png"))
                    .setFullPage(true));
        }
    }

    protected static Stream<Arguments> languageAndExpectedMessages() {
        return Stream.of(
                Arguments.of("en"),
                Arguments.of("de")
                //,
        );
    }
}
