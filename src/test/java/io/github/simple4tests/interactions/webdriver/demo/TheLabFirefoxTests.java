package io.github.simple4tests.interactions.webdriver.demo;

import io.github.simple4tests.interactions.webdriver.WebDriverInteractions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class TheLabFirefoxTests {

    public static final By MENU_OPEN = By.xpath("//button[@aria-label='Menu' and @aria-expanded='false']");
    public static final By COLLECT_KITTENS = By.xpath("//a[.='Collecting kittens']");
    public static final By STAR_GAME = By.xpath("//button[text()='Start Game']");
    public static final By GAME_OVER = By.xpath("//*[text()='Game Over!']");
    public static final By KITTENS = By.xpath("//img[@alt='Cat']");

    private WebDriver driver;

    @BeforeEach
    public void before() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        firefoxOptions.setCapability("marionette", true);
        firefoxOptions.setCapability("unexpectedAlertBehaviour", "ignore");
        System.setProperty("webdriver.gecko.driver", "c:/dev_tools/selenium/geckodriver.exe");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    public void seleniumKittensFirefoxTest() {
        driver.navigate().to("http://thelab.boozang.com/");
        driver.findElement(MENU_OPEN).click();
        driver.findElement(COLLECT_KITTENS).click();
        driver.findElement(STAR_GAME).click();
        while (0 == driver.findElements(GAME_OVER).size()) {
            while (0 == driver.findElements(KITTENS).size()
                    && 0 == driver.findElements(GAME_OVER).size()) {
                sleep(250);
            }
            if (0 < driver.findElements(KITTENS).size()
                    && 0 == driver.findElements(GAME_OVER).size()) {
                try {
                    driver.findElement(KITTENS).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            System.out.println("Failed to sleep(millis): " + e);
        }
    }

    @Test
    public void wdiKittensFirefoxTest() {
        WebDriverInteractions wdi = new WebDriverInteractions(driver);
        wdi.driver.navigate().to("http://thelab.boozang.com/");
        wdi.click(MENU_OPEN);
        wdi.click(COLLECT_KITTENS);
        wdi.click(STAR_GAME);
        while (wdi.isAbsent(GAME_OVER)) {
            if (wdi.waitToBePresent(KITTENS, true)
                    && wdi.isAbsent(GAME_OVER)) {
                try {
                    wdi.click(KITTENS);
                } catch (WebDriverException ignored) {
                }
            }
        }
    }
}
