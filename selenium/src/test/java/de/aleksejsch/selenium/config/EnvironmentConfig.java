package de.aleksejsch.selenium.config;

public class EnvironmentConfig {
    public static String getBaseUrl() {
        String env = System.getProperty("test.env", "local");
        return switch (env) {
            case "github" -> "https://spring-framework-petclinic-qctjpkmzuq-od.a.run.app";
            case "qa" -> "https://qa.example.com";
            case "docker" -> "http://petclinic:8080";
            default -> "http://localhost:8080";
        };
    }
}
