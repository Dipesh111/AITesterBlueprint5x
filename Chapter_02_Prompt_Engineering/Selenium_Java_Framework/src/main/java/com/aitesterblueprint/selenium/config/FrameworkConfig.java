package com.aitesterblueprint.selenium.config;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;

public record FrameworkConfig(
        URI baseUrl,
        Browser browser,
        boolean headless,
        Duration pageLoadTimeout,
        Duration explicitWaitTimeout,
        Optional<URI> remoteUrl,
        String username,
        String password,
        String authenticatedXPath) {

    @Override
    public String toString() {
        return "FrameworkConfig[baseUrl=" + baseUrl
                + ", browser=" + browser
                + ", headless=" + headless
                + ", pageLoadTimeout=" + pageLoadTimeout
                + ", explicitWaitTimeout=" + explicitWaitTimeout
                + ", remoteUrl=" + remoteUrl
                + ", credentialsConfigured=" + (!username.isBlank() && !password.isBlank())
                + ", authenticatedXPathConfigured=" + !authenticatedXPath.isBlank()
                + "]";
    }

    public static FrameworkConfig load() {
        URI baseUrl = parseHttpUri(value("selenium.baseUrl", "BASE_URL",
                "https://login.salesforce.com/?locale=in"), "base URL");
        String browserValue = value("selenium.browser", "SELENIUM_BROWSER", "chrome");
        String headlessValue = value("selenium.headless", "SELENIUM_HEADLESS", "true");
        String remoteValue = value("selenium.remoteUrl", "SELENIUM_REMOTE_URL", "");
        String authenticatedXPath = value(
                "salesforce.authenticatedXPath", "SALESFORCE_AUTHENTICATED_XPATH", "");

        if (!authenticatedXPath.isBlank()
                && !(authenticatedXPath.startsWith("/") || authenticatedXPath.startsWith("("))) {
            throw new IllegalArgumentException(
                    "SALESFORCE_AUTHENTICATED_XPATH must be an XPath expression.");
        }

        return new FrameworkConfig(
                baseUrl,
                Browser.from(browserValue),
                parseBoolean(headlessValue, "headless"),
                Duration.ofSeconds(positiveLong(
                        value("selenium.pageLoadTimeoutSeconds", "PAGE_LOAD_TIMEOUT_SECONDS", "45"),
                        "page load timeout")),
                Duration.ofSeconds(positiveLong(
                        value("selenium.explicitWaitTimeoutSeconds", "EXPLICIT_WAIT_TIMEOUT_SECONDS", "15"),
                        "explicit wait timeout")),
                remoteValue.isBlank()
                        ? Optional.empty()
                        : Optional.of(parseHttpUri(remoteValue, "remote WebDriver URL")),
                value("salesforce.username", "SALESFORCE_USERNAME", ""),
                secretValue("salesforce.password", "SALESFORCE_PASSWORD"),
                authenticatedXPath);
    }

    private static String value(String property, String environment, String fallback) {
        String propertyValue = System.getProperty(property);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue.trim();
        }
        String environmentValue = System.getenv(environment);
        return environmentValue == null || environmentValue.isBlank()
                ? fallback
                : environmentValue.trim();
    }

    private static String secretValue(String property, String environment) {
        String propertyValue = System.getProperty(property);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }
        String environmentValue = System.getenv(environment);
        return environmentValue == null ? "" : environmentValue;
    }

    private static URI parseHttpUri(String value, String label) {
        try {
            URI uri = URI.create(value);
            if (uri.getHost() == null
                    || !("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()))) {
                throw new IllegalArgumentException();
            }
            return uri;
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "The " + label + " must be an absolute HTTP or HTTPS URL.", exception);
        }
    }

    private static boolean parseBoolean(String value, String label) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw new IllegalArgumentException("The " + label + " setting must be true or false.");
    }

    private static long positiveLong(String value, String label) {
        try {
            long parsed = Long.parseLong(value);
            if (parsed > 0) {
                return parsed;
            }
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("The " + label + " must be a positive number.", exception);
        }
        throw new IllegalArgumentException("The " + label + " must be a positive number.");
    }
}
