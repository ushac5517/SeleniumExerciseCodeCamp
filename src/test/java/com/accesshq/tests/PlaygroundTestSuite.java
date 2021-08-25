package com.accesshq.tests;

import com.accesshq.ui.HomePage;
import com.accesshq.ui.WebDialog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class PlaygroundTestSuite {

    private ChromeDriver driver;

    @BeforeEach
    public void SetUp() {
        driver = new ChromeDriver();
        driver.get("https://d1iw6mb9di5l9r.cloudfront.net/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
    }

    private void RemoveImplicitWait(ChromeDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    private void AddImplicitWait(ChromeDriver driver) {
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
    }

    private void DoWithoutImplicit(ChromeDriver driver, Runnable o) {
        RemoveImplicitWait(driver);
        o.run();
        AddImplicitWait(driver);
    }

    @Test
    public void Forename_UserInput_Test() {
        driver.findElement(By.id("forename")).sendKeys("Usha");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
    }

    @Test
    public void Forename_Getting_Value_Test() {
        driver.findElement(By.id("forename")).sendKeys("Usha Choudhury");
        var forenameValue = driver.findElement(By.id("forename")).getAttribute("value");
        System.out.println(forenameValue);
    }

    @Test
    public void SubmitButtonTest() {
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.cssSelector("[role = button]")).click();
    }

    @Test
    public void Clicking_Hyperlink_Wizards_Test() {
        driver.findElement(By.linkText("WIZARDS")).click();
    }

    @Test
    public void ClickMe_Button_Test() {
        By locator = By.cssSelector("[role=button]");
        WebElement movingButton = driver.findElement((locator));
        movingButton.click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.textToBe(locator, "CLICK ME UP!"));
        Assertions.assertEquals("CLICK ME UP!", movingButton.getText());
    }

    @Test
    public void LoginDialog_Login_ErrorMessages_Test() {
        var profileIcon = driver.findElement(By.cssSelector("[aria-label=users]"));
        profileIcon.click();

        var dialog = driver.findElement(By.className("v-dialog--active"));
        dialog.findElement(By.id("loginButton")).click();

        var errorMessages = dialog.findElements(By.className("v-messages__message"));
        new WebDriverWait(driver, 1000).until(ExpectedConditions.visibilityOf(errorMessages.get(0)));
        for (var messageElement : errorMessages) {
            Assertions.assertEquals("Invalid user and password", messageElement.getText());
        }
    }

    @Test
    public void Clicking_Homepage_LoginButton_Test() {
        driver.findElement((By.cssSelector("[aria-label=login]"))).click();
    }

    @Test
    public void LoginButtonTest() {

        //Arrange
        var homePage = new HomePage(driver);

        //Act
        homePage.clickLoginButton();
        homePage.waitUntilAlertMsgIsVisible();

        //Assert
        Assertions.assertEquals("You clicked the login button", homePage.getAlertMessageText());
    }

    @Test
    public void LoginProfile_ErrorMassage_Test() {

        //Arrange
        var homePage = new HomePage(driver);
        homePage.clickUsersButton();
        var webDialog = new WebDialog(driver);

        //Act
        webDialog.clickLoginButton();
        var errorMessages = webDialog.getErrorMessages();

        //Assert
        for(var messageElement : errorMessages) {
            Assertions.assertEquals("Invalid user and password", messageElement.getText());
        }
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

    @AfterEach
    public void CleanUp() {
        driver.quit();
    }
}
