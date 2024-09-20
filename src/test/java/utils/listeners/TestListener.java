package utils.listeners;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.extentReports.ExtentManager;


import java.util.Objects;

import static utils.extentReports.ExtentTestManager.getTest;

public class TestListener extends TestBase implements ITestListener {


    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }


    @Override
    public void onStart(ITestContext iTestContext) {
        if (driver == null) {
            System.out.println("Driver is not initialized.");
        } else {
            iTestContext.setAttribute("Webdriver", driver);
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Starting test: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        getTest().log(Status.PASS, "Test passed: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String failedScreenShot = "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
        getTest().log(Status.FAIL, "Test has failed: " + getTestMethodName(iTestResult),
                getTest().addScreenCaptureFromBase64String(failedScreenShot).getModel().getMedia().get(0));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        getTest().log(Status.SKIP, "Test Skipped: " + getTestMethodName(iTestResult));
    }
}

