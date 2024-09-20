package tests;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LandingPage;

import java.lang.reflect.Method;
import java.util.List;

import static utils.extentReports.ExtentTestManager.startTest;

public class TestCases extends TestBase {
    LandingPage landingPage;
    SoftAssert softAssert;
    private void initializer() {
        landingPage = new LandingPage(driver);
    }

    @Test( priority = 1, description = "  TC_02 - Validate that user can search for a first name and name appears in the search results")
    public void TC_02 (Method method) throws InterruptedException {
        startTest(method.getName(), method.getAnnotation(Test.class).description(), "go to url");
        initializer();
        Assert.assertTrue(landingPage.searchName("rick", "Rick"), "The search results do not contain the expected name");
        Assert.assertTrue(landingPage.searchName("morty", "Morty"), "The search results do not contain the expected name");
    }

    @Test( priority = 2, description = "  TC_05 - Validate that user can search for a last name and name appears in the search results")
    public void TC_05 (Method method) throws InterruptedException {
        startTest(method.getName(), method.getAnnotation(Test.class).description(), "go to url");
        Assert.assertTrue(landingPage.searchName("sanchez", "Sanchez"), "The search results do not contain the expected name");
        Assert.assertTrue(landingPage.searchName("smith", "Smith"), "The search results do not contain the expected name");
    }

    @Test( priority = 3, description = "  TC_07 - Validate characters from a location are displayed when user filter by location")
    public void TC_07 (Method method) throws InterruptedException {
        startTest(method.getName(), method.getAnnotation(Test.class).description(), "go to url");
        String testData = "Citadel of Ricks";
        List<WebElement> results = landingPage.selectLocationAndAssertCharacters(testData);
        Assert.assertFalse(results.isEmpty(), "No characters found for location: " + testData);
    }


    @Test( priority = 4, description = "  TC_14 - Validate name appears in the favorites list when a user clicks on any of the names")
    public void TC_14 (Method method) throws InterruptedException {
        startTest(method.getName(), method.getAnnotation(Test.class).description(), "go to url");
        Assert.assertTrue(landingPage.clickNameAndValidateFavorites(),"The search results do not contain the expected name");
        Thread.sleep(5000);
    }

}
