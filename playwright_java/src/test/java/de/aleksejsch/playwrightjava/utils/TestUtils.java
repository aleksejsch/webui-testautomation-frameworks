package de.aleksejsch.playwrightjava.utils;

public class TestUtils {

    protected static String buildUrl(String path, String langCode, String baseUrl) {
        if (langCode == null || langCode.isBlank()) {
            return baseUrl + path;
        }
        return baseUrl + path + "?lang=" + langCode;
    }

}
