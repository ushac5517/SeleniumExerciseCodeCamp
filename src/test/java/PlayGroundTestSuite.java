import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.*;
import java.util.concurrent.TimeUnit;

public class PlayGroundTestSuite {
    private ChromeDriver driver;

    @BeforeEach
    public void SetUp() {
        driver = new ChromeDriver();
        driver.get("https://d1iw6mb9di5l9r.cloudfront.net");
    }

    @Test
    public void InputFieldTest() {
        driver.findElement(By.id("forename")).sendKeys("Usha");
        String val = driver.findElement(By.id("forename")).getAttribute("value");
        System.out.println(val);
    }

    @Test
    public void SubmitButtonTest() {
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.cssSelector("[role = button]")).click();
    }

    @Test
    public void LoginButtonTest() {
        driver.findElement(By.cssSelector("[aria-label='login']")).click();
    }

    @Test
    public void LoginProfile_ErrorMassage_Test() {

        var homePage = new HomePage(driver);
        homePage.clickLoginIcon();
        homePage.clickLoginButton();
        homePage.waitUntilAlertMsgIsVisible();

        Assertions.assertEquals("Invalid user and password", homePage.getAlertMessageText());
    }

    @Test
    public void FormsDialog_ErrorMessage_Test() {
        driver.findElement(By.cssSelector("[aria-label=forms]")).click();

        var submitButtonAccess = driver.findElement(By.className("v-card__text"));
        var submitButton = submitButtonAccess.findElement(By.cssSelector("[type=button]"));
        submitButton.click();


        Assertions.assertEquals("Your name is required", driver.findElement(By.id("name-err")).getText());
        Assertions.assertEquals("Your email is required", driver.findElement(By.id("email-err")).getText());
        Assertions.assertEquals("You must agree to continue", driver.findElement(By.id("agree-err")).getText());

    }

    @Test
    public void Form_ValidInput_ErrorMessage_Test() {
        driver.findElement(By.cssSelector("[aria-label=forms]")).click();

        var submitButtonCard = driver.findElement(By.className("v-card__text"));
        var submitButton = submitButtonCard.findElement(By.cssSelector("[type=button]"));
        submitButton.click();

        driver.findElement(By.id("name")).sendKeys("Usha");
        driver.findElement(By.id("email")).sendKeys("Ushachoudhury@gmail.com");
        driver.findElement(By.cssSelector("[for=agree]")).click();

        DoWithoutImplicit(driver, () -> {
            Assertions.assertTrue(driver.findElements(By.id("name-err")).size() == 0);
            Assertions.assertTrue(driver.findElements(By.id("email-err")).size() == 0);
            Assertions.assertTrue(driver.findElements(By.id("agree-err")).size() == 0);
        });

    }

    private void RemoveImplicitWait(ChromeDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    private void DoWithoutImplicit(ChromeDriver driver, Runnable o) {
        RemoveImplicitWait(driver);
        o.run();
        AddImplicitWait(driver);
    }

    private void AddImplicitWait(ChromeDriver driver) {
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
    }


    @AfterEach
    public void CleanUp(){
       driver.quit();
    }

}
