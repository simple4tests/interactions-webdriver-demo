package io.github.simple4tests.interactions.webdriver.demo;

import io.github.simple4tests.interactions.webdriver.WebDriverInteractions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class GoogleFirefoxTests {

    private final By ACCEPT = By.id("L2AGLb");
    private final By SEARCH_CRITERIA = By.xpath("//input[@name='q']");
    private final By TOTO_UNIVERSAL_MUSIC = By.xpath("//h3[normalize-space(.)='Toto - Universal Music France']");
    private final By TOTO = By.xpath("//a[contains(.,'Toto')]");

    private WebDriver driver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.gecko.driver", "c:/dev_tools/selenium/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        firefoxOptions.setCapability("marionette", true);
        firefoxOptions.setCapability("unexpectedAlertBehaviour", "ignore");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    public void seleniumGoogleFirefoxTest() {
        driver.navigate().to("http://www.google.fr");
        driver.findElement(ACCEPT).click();
        driver.findElement(SEARCH_CRITERIA)
                .sendKeys("toto universal music", Keys.ENTER);
        waitToBePresent(TOTO_UNIVERSAL_MUSIC);
        driver.findElement(TOTO_UNIVERSAL_MUSIC).click();
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();",
                driver.findElement(TOTO));
        driver.findElement(TOTO).click();
    }

    private void waitToBePresent(By element) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(element));
    }

    @Test
    public void wdiGoogleFirefoxTest() {
        WebDriverInteractions wdi = new WebDriverInteractions(driver);
        wdi.driver.navigate().to("http://www.google.fr");
        wdi.click(ACCEPT);
        wdi.sendKeys(SEARCH_CRITERIA, "toto universal music", Keys.ENTER);
        wdi.click(TOTO_UNIVERSAL_MUSIC);
        wdi.click(TOTO);
    }
}
