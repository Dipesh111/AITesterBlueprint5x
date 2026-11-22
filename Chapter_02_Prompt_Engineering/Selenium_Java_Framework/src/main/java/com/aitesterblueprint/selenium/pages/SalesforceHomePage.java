package com.aitesterblueprint.selenium.pages;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class SalesforceHomePage {
    private static final Logger LOGGER = Logger.getLogger(SalesforceHomePage.class.getName());

    private final WebDriverWait wait;
    private final By authenticatedPageMarker;

    public SalesforceHomePage(WebDriver driver, String verifiedXPath, Duration timeout) {
        if (verifiedXPath == null || verifiedXPath.isBlank()) {
            throw new IllegalArgumentException("A verified authenticated-page XPath is required.");
        }
        this.wait = new WebDriverWait(driver, timeout);
        this.authenticatedPageMarker = By.xpath(verifiedXPath);
    }

    public void waitUntilLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(authenticatedPageMarker));
        } catch (TimeoutException exception) {
            LOGGER.log(Level.WARNING, "The authenticated Salesforce page did not become visible.", exception);
            throw exception;
        }
    }
}
