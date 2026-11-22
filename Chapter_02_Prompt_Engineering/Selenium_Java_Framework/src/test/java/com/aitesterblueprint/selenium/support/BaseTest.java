package com.aitesterblueprint.selenium.support;

import com.aitesterblueprint.selenium.config.FrameworkConfig;
import com.aitesterblueprint.selenium.driver.DriverFactory;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();
    private static final ThreadLocal<FrameworkConfig> CONFIG = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {
        FrameworkConfig config = FrameworkConfig.load();
        WebDriver driver = DriverFactory.create(config);
        CONFIG.set(config);
        DRIVER.set(driver);
        WAIT.set(new WebDriverWait(driver, config.explicitWaitTimeout()));
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser(ITestResult result) {
        WebDriver driver = DRIVER.get();
        RuntimeException teardownFailure = null;
        try {
            if (driver != null && result.getStatus() == ITestResult.FAILURE) {
                attachFailureEvidence(driver);
            }
        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (WebDriverException exception) {
                    LOGGER.log(Level.SEVERE, "Unable to close the WebDriver session.", exception);
                    if (result.getStatus() != ITestResult.FAILURE) {
                        teardownFailure = new IllegalStateException(
                                "Unable to close the WebDriver session.", exception);
                    }
                }
            }
            DRIVER.remove();
            WAIT.remove();
            CONFIG.remove();
        }
        if (teardownFailure != null) {
            throw teardownFailure;
        }
    }

    protected WebDriver driver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for this test.");
        }
        return driver;
    }

    protected WebDriverWait waitFor() {
        WebDriverWait wait = WAIT.get();
        if (wait == null) {
            throw new IllegalStateException("WebDriver wait is not initialized for this test.");
        }
        return wait;
    }

    protected FrameworkConfig config() {
        FrameworkConfig config = CONFIG.get();
        if (config == null) {
            throw new IllegalStateException("Framework configuration is not initialized for this test.");
        }
        return config;
    }

    private void attachFailureEvidence(WebDriver driver) {
        try {
            Allure.addAttachment(
                    "Failure screenshot",
                    "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)),
                    ".png");
            Allure.addAttachment("Failure URL", driver.getCurrentUrl());
        } catch (WebDriverException exception) {
            LOGGER.log(Level.WARNING, "Unable to attach browser failure evidence to Allure.", exception);
        }
    }
}
