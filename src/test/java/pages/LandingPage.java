package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class LandingPage extends PageBase {
    WebDriverWait wait;
    SoftAssert softAssert;

    public LandingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);


    }

    // Elements and method to perform actions

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/input")
    WebElement characterName_field;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div/select")
    WebElement locationDropdown;

    @FindBy(className = "CharacterList_root__3dgo-")
    List<WebElement> results;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/button[2]")
    WebElement fav_btn;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/button[1]")
    WebElement listItem;

    @FindBy(className = "CharacterListItem_name__2IwyZ")
    WebElement listItemText;

    public boolean searchName(String name, String expectedName) throws InterruptedException {
        click(characterName_field);
        enterText(characterName_field, name);
        characterName_field.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        boolean nameFound = results.stream().map(WebElement::getText).anyMatch(text -> text.contains(expectedName));


        characterName_field.clear();
        return nameFound;
    }


    public List<WebElement> selectLocationAndAssertCharacters(String location) throws InterruptedException {
        new Select(locationDropdown).selectByVisibleText(location);
        locationDropdown.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        return results;

    }

    public boolean clickNameAndValidateFavorites() throws InterruptedException{
        this.selectLocationAndAssertCharacters("All Locations");
        String listItemName = listItemText.getText();
        click(listItem);
        click(fav_btn);
        boolean itemfound = listItemText.getText().contains( listItemName);
        return itemfound;
    }

}
