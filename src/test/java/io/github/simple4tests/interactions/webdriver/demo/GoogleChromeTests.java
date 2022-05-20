package io.github.simple4tests.interactions.webdriver.demo;

import io.github.simple4tests.interactions.webdriver.WebDriverInteractions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class GoogleChromeTests {

    public static final By ACCEPT = By.id("L2AGLb");
    public static final By SEARCH_CRITERIA = By.xpath("//input[@name='q']");
    public static final By TOTO_UNIVERSAL_MUSIC = By.xpath("//h3[normalize-space(.)='Toto - Universal Music France']");
    public static final By TOTO = By.xpath("//a[contains(.,'Toto')]");

    public WebDriver driver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", "c:/dev_tools/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    public void seleniumGoogleChromeTest() {
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
    public void wdiGoogleChromeTest() {
        WebDriverInteractions wdi = new WebDriverInteractions(driver);
        wdi.driver.navigate().to("http://www.google.fr");
        wdi.click(ACCEPT);
        wdi.sendKeys(SEARCH_CRITERIA, "toto universal music", Keys.ENTER);
        wdi.click(TOTO_UNIVERSAL_MUSIC);
        wdi.click(TOTO);
    }
}
