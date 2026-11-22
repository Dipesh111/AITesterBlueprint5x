package com.aitesterblueprint.selenium.tests;

import com.aitesterblueprint.selenium.config.FrameworkConfig;
import com.aitesterblueprint.selenium.pages.LoginPage;
import com.aitesterblueprint.selenium.pages.SalesforceHomePage;
import com.aitesterblueprint.selenium.support.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

@Epic("Salesforce UI")
@Feature("Login")
public final class SalesforceLoginTest extends BaseTest {

    @Test(description = "Displays the Salesforce login form", groups = {"ui", "smoke"})
    @Story("Login page")
    public void loginPageDisplaysRequiredControls() {
        LoginPage loginPage = new LoginPage(driver(), waitFor());
        loginPage.open(config().baseUrl().toString());

        Assert.assertTrue(loginPage.isLoginFormVisible(), "The login controls should be visible.");
    }

    @Test(description = "Rejects credentials for a reserved invalid email domain", groups = {"ui", "negative"})
    @Story("Invalid credentials")
    public void invalidCredentialsDisplayAnAuthenticationError() {
        LoginPage loginPage = new LoginPage(driver(), waitFor());
        loginPage.open(config().baseUrl().toString());
        loginPage.enterUsername("selenium.invalid-user@example.invalid");
        loginPage.enterPassword("InvalidAutomationPassword!");
        loginPage.submit();

        String errorMessage = loginPage.waitForAuthenticationError();
        Assert.assertFalse(errorMessage.isBlank(), "The authentication error should contain a message.");
    }

    @Test(description = "Authenticates configured test credentials and loads the verified Salesforce page",
            groups = {"ui", "positive"})
    @Story("Valid credentials")
    public void validCredentialsOpenTheAuthenticatedSalesforcePage() {
        FrameworkConfig config = config();
        if (config.username().isBlank()
                || config.password().isBlank()
                || config.authenticatedXPath().isBlank()) {
            throw new SkipException(
                    "Set SALESFORCE_USERNAME, SALESFORCE_PASSWORD, and a verified "
                            + "SALESFORCE_AUTHENTICATED_XPATH to run the authenticated-flow test.");
        }

        LoginPage loginPage = new LoginPage(driver(), waitFor());
        loginPage.open(config.baseUrl().toString());
        loginPage.enterUsername(config.username());
        loginPage.enterPassword(config.password());
        loginPage.submit();

        new SalesforceHomePage(
                driver(), config.authenticatedXPath(), config.explicitWaitTimeout()).waitUntilLoaded();
    }
}
