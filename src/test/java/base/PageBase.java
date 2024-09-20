package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PageBase {

    public WebDriver driver;
    public WebDriverWait wait;
    public JavascriptExecutor jsExecutor;

    public PageBase(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void waitForElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitForElement(element);
        element.click();
    }

    public void refresh(WebElement element) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }

    public void enterText(WebElement element, String text) {
        waitForElement(element);
        element.sendKeys(text);
    }

    public void clickElementWithJavaScript(WebElement element) {
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoViewWithJavaScript(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
