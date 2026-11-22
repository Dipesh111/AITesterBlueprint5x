package com.aitesterblueprint.selenium.pages;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class LoginPage {
    private static final Logger LOGGER = Logger.getLogger(LoginPage.class.getName());

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement username;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@id='error']")
    private WebElement authenticationError;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        perform("open the login page", () -> {
            driver.navigate().to(url);
            wait.until(ExpectedConditions.visibilityOf(username));
            wait.until(ExpectedConditions.visibilityOf(password));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
        });
    }

    public boolean isLoginFormVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(username)).isDisplayed()
                    && password.isDisplayed()
                    && loginButton.isDisplayed();
        } catch (WebDriverException exception) {
            LOGGER.log(Level.WARNING, "Unable to verify login form visibility.", exception);
            throw new IllegalStateException("Unable to verify login form visibility.", exception);
        }
    }

    public void enterUsername(String value) {
        perform("enter the username", () -> {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(username));
            field.clear();
            field.sendKeys(value);
        });
    }

    public void enterPassword(String value) {
        perform("enter the password", () -> {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(password));
            field.clear();
            field.sendKeys(value);
        });
    }

    public void submit() {
        perform("submit the login form", () ->
                wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click());
    }

    public String waitForAuthenticationError() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(authenticationError)).getText().trim();
        } catch (WebDriverException exception) {
            LOGGER.log(Level.WARNING, "Authentication error did not become visible.", exception);
            throw new IllegalStateException(
                    "Authentication error did not become visible after login submission.", exception);
        }
    }

    private void perform(String action, Runnable operation) {
        try {
            operation.run();
        } catch (WebDriverException exception) {
            LOGGER.log(Level.WARNING, "Unable to " + action + ".", exception);
            throw new IllegalStateException("Unable to " + action + ".", exception);
        }
    }
}
