package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.*;
import utils.ReadTestData;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestBase {



    public static ReadTestData readTestData = new ReadTestData();
    public static String baseUrl = readTestData.setApplicationURL();
    public static WebDriver driver;
    public static JavascriptExecutor jsExecutor;


    public void sleep(int time) {
        try {
            Thread.sleep(time * 1000);//content of sleep is in MS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void captureScreen(WebDriver driver, String tname) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
        FileUtils.copyFile(source, target);
        System.out.println("Screenshot taken");
    }


    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {

        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setAcceptInsecureCerts(true);
                    chromeOptions.setExperimentalOption("excludeSwitches",new String[] {"enable-automation"});
                    driver = new ChromeDriver(chromeOptions);
                    break;
//                case "edge":
//                    WebDriverManager.edgedriver().setup();
//                    EdgeOptions edgeOptions = new EdgeOptions();
//                    edgeOptions.setAcceptInsecureCerts(true);
//                    edgeOptions.setExperimentalOption("excludeSwitches",new String[] {"enable-automation"});
//                    driver = new EdgeDriver(edgeOptions);
//                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }


            driver.get(baseUrl);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            jsExecutor = (JavascriptExecutor) driver;
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
