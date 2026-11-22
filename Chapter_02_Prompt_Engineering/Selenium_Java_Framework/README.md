# Selenium Java UI Automation Framework

Maven-based Selenium 4, TestNG, and Allure framework using Page Object Model and XPath locators. Selenium Manager provisions local browser drivers; Chrome, Firefox, Edge, and Selenium Grid are supported.

## Requirements

- JDK 17 or later
- Maven 3.9 or later
- A supported browser installed locally, unless using Selenium Grid

## Configuration

Configuration can be supplied as Java system properties or environment variables. System properties take precedence.

| Property | Environment variable | Default |
| --- | --- | --- |
| `selenium.baseUrl` | `BASE_URL` | `https://login.salesforce.com/?locale=in` |
| `selenium.browser` | `SELENIUM_BROWSER` | `chrome` |
| `selenium.headless` | `SELENIUM_HEADLESS` | `true` |
| `selenium.pageLoadTimeoutSeconds` | `PAGE_LOAD_TIMEOUT_SECONDS` | `45` |
| `selenium.explicitWaitTimeoutSeconds` | `EXPLICIT_WAIT_TIMEOUT_SECONDS` | `15` |
| `selenium.remoteUrl` | `SELENIUM_REMOTE_URL` | unset (local browser) |
| `salesforce.username` | `SALESFORCE_USERNAME` | unset |
| `salesforce.password` | `SALESFORCE_PASSWORD` | unset |
| `salesforce.authenticatedXPath` | `SALESFORCE_AUTHENTICATED_XPATH` | unset |

Set credentials in the environment or your CI secret store; do not commit them. The successful-login test is skipped unless all three Salesforce-specific values are configured. Set `SALESFORCE_AUTHENTICATED_XPATH` to a verified XPath for an element unique to the authenticated page in your Salesforce org. The framework intentionally does not assume an authenticated-page locator or MFA behavior.

Example PowerShell session:

```powershell
$env:SALESFORCE_USERNAME = "your-test-user"
$env:SALESFORCE_PASSWORD = "your-test-password"
$env:SALESFORCE_AUTHENTICATED_XPATH = "//your-verified-xpath"
```

For CI or Selenium Grid, set `SELENIUM_REMOTE_URL` to the authorized Grid endpoint. The URL and test account should target an environment approved for automation.

## Run

```powershell
mvn test
mvn test -Dselenium.browser=firefox -Dselenium.headless=false
mvn test -Dselenium.remoteUrl=http://localhost:4444/wd/hub
mvn allure:report
mvn allure:serve
```

TestNG results are generated under `target/surefire-reports`; Allure results and the generated report are under `target/allure-results` and `target/site/allure-maven-plugin`.
