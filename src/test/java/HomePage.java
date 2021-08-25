import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLoginIcon(){
        var loginProfile = driver.findElement(By.cssSelector("[aria-label=users]"));
        loginProfile.click();
    }
    public void clickLoginButton() {
        var loginDialog = driver.findElement(By.className("v-dialog--active"));
        loginDialog.findElement(By.id("loginButton")).click();
    }

    public WebElement getAlertMsgElement() {
        return driver.findElement(By.className("v-messages__message"));
    }

    public String getAlertMessageText() {
       return getAlertMsgElement().getText();
    }

    public void waitUntilAlertMsgIsVisible() {
        new WebDriverWait(driver, 1000).until(ExpectedConditions.visibilityOf(getAlertMsgElement()));
    }
}
