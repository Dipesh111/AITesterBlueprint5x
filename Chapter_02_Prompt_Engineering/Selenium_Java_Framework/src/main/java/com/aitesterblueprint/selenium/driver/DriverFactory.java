package com.aitesterblueprint.selenium.driver;

import com.aitesterblueprint.selenium.config.Browser;
import com.aitesterblueprint.selenium.config.FrameworkConfig;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1920, 1080);

    private DriverFactory() {
    }

    public static WebDriver create(FrameworkConfig config) {
        try {
            WebDriver driver = config.remoteUrl()
                    .map(url -> createRemoteDriver(config, url.toString()))
                    .orElseGet(() -> createLocalDriver(config));
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            driver.manage().timeouts().pageLoadTimeout(config.pageLoadTimeout());
            driver.manage().window().setSize(DEFAULT_WINDOW_SIZE);
            return driver;
        } catch (WebDriverException | IllegalArgumentException exception) {
            LOGGER.log(Level.SEVERE, "Unable to initialize the configured WebDriver.", exception);
            throw new IllegalStateException("Unable to initialize the configured WebDriver.", exception);
        }
    }

    private static WebDriver createRemoteDriver(FrameworkConfig config, String remoteUrl) {
        try {
            return new RemoteWebDriver(
                    java.net.URI.create(remoteUrl).toURL(),
                    options(config.browser(), config.headless()));
        } catch (MalformedURLException exception) {
            throw new IllegalArgumentException("The remote WebDriver URL is invalid.", exception);
        }
    }

    private static WebDriver createLocalDriver(FrameworkConfig config) {
        return switch (config.browser()) {
            case CHROME -> new ChromeDriver(chromeOptions(config.headless()));
            case FIREFOX -> new FirefoxDriver(firefoxOptions(config.headless()));
            case EDGE -> new EdgeDriver(edgeOptions(config.headless()));
        };
    }

    private static Capabilities options(Browser browser, boolean headless) {
        return switch (browser) {
            case CHROME -> chromeOptions(headless);
            case FIREFOX -> firefoxOptions(headless);
            case EDGE -> edgeOptions(headless);
        };
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--window-size=1920,1080");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--window-size=1920,1080");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }
}
