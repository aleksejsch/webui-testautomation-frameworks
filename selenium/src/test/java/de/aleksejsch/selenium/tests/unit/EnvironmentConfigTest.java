package de.aleksejsch.selenium.tests.unit;

import de.aleksejsch.selenium.config.EnvironmentConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EnvironmentConfigTest {

    private static final String KEY = "test.env";

    @BeforeEach
    void clearEnvBeforeEach() {
        System.clearProperty(KEY);
    }

    @AfterAll
    static void clearEnvAfterAll() {
        System.clearProperty(KEY);
    }

    @Test
    @Disabled
    void defaultIsLocal() {
        assertEquals("http://localhost:8080", EnvironmentConfig.getBaseUrl());
    }

    @ParameterizedTest(name = "env={0}")
    @MethodSource("envs")
    @Disabled
    void baseUrlByEnv(String env, String expectedUrl) {
        System.setProperty(KEY, env);
        assertEquals(expectedUrl, EnvironmentConfig.getBaseUrl());
    }

    static Stream<Arguments> envs() {
        return Stream.of(
                arguments("local", "http://localhost:8080"),
                arguments("qa",     "https://qa.example.com")
        );
    }

}
