package de.aleksejsch.selenium.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

	private static final String SCREENSHOT_DIR = "target/screenshots/";

	public static void takeScreenshot(WebDriver driver, String testName) {
		if (!(driver instanceof TakesScreenshot)) {
			System.err.println("Driver does not support screenshots.");
			return;
		}

		try {

			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

			Path destPath = Paths.get(SCREENSHOT_DIR, testName + "_" + timestamp + ".png");
			Files.createDirectories(destPath.getParent());

			Files.copy(screenshot.toPath(), destPath);

			System.out.println("Screenshot saved to: " + destPath.toAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to capture screenshot: " + e.getMessage());
		}
	}
}
