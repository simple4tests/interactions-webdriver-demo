package io.github.simple4tests.interactions.webdriver.demo;

import io.github.simple4tests.interactions.webdriver.WebDriverInteractions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class SncfFirefoxTests {

    private static final By REFUSE_ALL = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinDeclineAll");
    private static final By DEPARTURE = By.id("departure-place");
    private static final By DEPARTURE_OPTION_1 = By.id("react-autocomplete-departure-place-1");
    private static final By ARRIVAL = By.id("arrival-place");
    private static final By ARRIVAL_OPTION_1 = By.id("react-autocomplete-arrival-place-1");
    private static final By SEARCH = By.xpath("//button[@title='Rechercher un itinéraire']");
    private static final By RESULTS = By.xpath("//section//ul/li");

    private WebDriver driver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.gecko.driver", "c:/dev_tools/selenium/geckodriver.exe");
        driver = new FirefoxDriver(new FirefoxOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    public void seleniumSncfFirefoxTest() {
        driver.navigate().to("http://www.sncf.com/fr");
        waitToBePresent(REFUSE_ALL);
        driver.findElement(REFUSE_ALL).click();
        driver.findElement(DEPARTURE).sendKeys("Paris");
        waitToBePresent(DEPARTURE_OPTION_1);
        driver.findElement(DEPARTURE_OPTION_1).click();
        driver.findElement(ARRIVAL).sendKeys("Lyon");
        waitToBePresent(ARRIVAL_OPTION_1);
        driver.findElement(ARRIVAL_OPTION_1).click();
        driver.findElement(SEARCH).click();
        waitToBePresent(RESULTS);
    }

    private void waitToBePresent(By element) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(element));
    }

    @Test
    public void wdiSncfFirefoxTest() {
        WebDriverInteractions wdi = new WebDriverInteractions(driver);
        wdi.driver.navigate().to("http://www.sncf.com/fr");
        wdi.click(REFUSE_ALL);
        wdi.sendKeys(DEPARTURE, "Paris");
        wdi.click(DEPARTURE_OPTION_1);
        wdi.sendKeys(ARRIVAL, "Lyon");
        wdi.click(ARRIVAL_OPTION_1);
        wdi.click(SEARCH);
        wdi.waitToBePresent(RESULTS);
    }
}
