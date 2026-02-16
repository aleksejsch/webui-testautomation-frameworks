package de.aleksejsch.playwrightjava.tests.unit;

import de.aleksejsch.playwrightjava.config.EnvironmentConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvironmentConfigTest {

    @Test
    @Disabled
	public void testDefaultIsLocal() {
        System.clearProperty("test.env");
        assertEquals("http://localhost:8080", EnvironmentConfig.getBaseUrl());
    }

    @Test
    @Disabled
	public void testQaEnv() {
        System.setProperty("test.env", "qa");
        assertEquals("https://qa.example.com", EnvironmentConfig.getBaseUrl());
    }

}
