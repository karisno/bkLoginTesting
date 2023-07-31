import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestLoginForm {
    WebDriver browser;
    WebElement buttonSignInOnSignInPage;


    @BeforeEach
    public void beforeTest() {
        browser = new ChromeDriver();
        browser.get("https://www.bk.com/");
        WebElement cookiesAlert = new WebDriverWait(browser, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='onetrust-accept-btn-handler']")));
        cookiesAlert.click();
        WebElement buttonSignInOnMainPage =
                new WebDriverWait(browser, Duration.ofSeconds(10)).
                        until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='mobile-nav-signup-link']")));
        buttonSignInOnMainPage.click();
        buttonSignInOnSignInPage =
                new WebDriverWait(browser, Duration.ofSeconds(10)).
                        until(ExpectedConditions.
                                elementToBeClickable(By.xpath("//div[@data-testid='signin-button']")));
    }

    @AfterEach
    public void afterTest() {
        browser.quit();
    }

    @Test
    public void signInWithEmptyEmailFieldTest() {
        buttonSignInOnSignInPage.click();
        String expected = "Email is a required field.";
        WebElement actual = browser.findElement(By.xpath("//div[contains(@class,'r-wk8lta')]"));
        Assertions.assertEquals(expected, actual.getText());
    }

    @Test
    public void signInWithIncorrectEmailTest() {
        WebElement emailInput = browser.findElement(By.xpath("//input[@data-testid='signin-email-input']"));
        emailInput.sendKeys("test");
        buttonSignInOnSignInPage.click();
        String expected = "That doesn't look like a valid email.";
        WebElement actual = browser.findElement(By.xpath("//div[contains(@class,'r-wk8lta')]"));
        Assertions.assertEquals(expected, actual.getText());
    }

    @Test
    public void signInWithValidEmailTest() {
        WebElement emailInput = browser.findElement(By.xpath("//input[@data-testid='signin-email-input']"));
        emailInput.sendKeys("test@test.com");
        buttonSignInOnSignInPage.click();
        String expected = "We sent an email with login instructions to test@test.com";
        WebElement actual = new WebDriverWait(browser, Duration.ofSeconds(10)).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='css-1rynq56 r-17l9mgj r-anxyqk r-1b43r93 r-oxtfae r-135wba7']")));
        Assertions.assertEquals(expected, actual.getText());
    }

}
