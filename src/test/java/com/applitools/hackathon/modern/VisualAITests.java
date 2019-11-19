package com.applitools.hackathon.modern;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static com.applitools.hackathon.constants.WebElements.*;

/**
 * Created by Abhilash Thaduka on 18-11-2019.
 */
@RunWith(DataProviderRunner.class)
public class VisualAITests {

    private static BatchInfo batchInfo;
    private static EyesRunner eyesRunner;
    private static Eyes eyes;
    private static WebDriver driver;
    private static final String URL = "https://demo.applitools.com/hackathonV2.html";
    private static final String API_KEY = "nNWMXXBwAn1534nBojIJvECmZJ7jZJ78wC2vM31057TuU110";


    @BeforeClass
    public static void setBatch() {
        batchInfo = new BatchInfo("Hackathon");
        // Initialize the Runner for your test.
        eyesRunner = new ClassicRunner();
        eyes = new Eyes(eyesRunner);
        eyes.setApiKey(API_KEY);
        eyes.setBatch(batchInfo);

        System.setProperty("webdriver.chrome.driver", "E:/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1376, 1032));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL);
    }

    @Test
    public void loginTest() {
        driver.findElement(By.id("log-in")).click();
        checkWindow("Login App", "Login Page Test", "Login Window");
    }

    @Test
    @UseDataProvider("testData")
    public void testLoginFunctionality(String userName, String password, String testName) {
        driver.get(URL);
        eyes.open(driver, "Demo - App", testName, new RectangleSize(800, 600));
        loginWithUserNameAndPassword(userName, password);
        eyes.checkWindow("Login Window");
        eyes.closeAsync();

    }

    @Test
    public void tableSortTest() {
        loginWithUserNameAndPassword("Dummy", "Dummy");
        driver.findElement(By.xpath("//th[@id='amount']")).click();
        checkWindow("Demo - App", "Table Sort Test", "Table Sort Window");
    }

    @Test
    public void canvasChartsTest() {
        loginWithUserNameAndPassword("Dummy", "Dummy");
        driver.findElement(COMPARE_EXPENSES).click();
        checkWindow("Charts App", "Chart Test 1", "Chart Window 1");

        driver.findElement(ADD_DATA).click();
        checkWindow("Charts App", "Chart Test 2", "Chart Window 2");
    }

    @Test
    public void dynamicContentTest() {
        driver.get(URL + "?showAd=true");
        loginWithUserNameAndPassword("Dummy", "Dummy");
        checkWindow("Dynamic App", "Dynamic Content Test 2", "Dynamic Window");
    }


    private void checkWindow(String appName, String testName, String tag) {
        eyes.open(driver, appName, testName, new RectangleSize(800, 600));
        eyes.checkWindow(tag);
        eyes.closeAsync();
    }

    private void loginWithUserNameAndPassword(String userName, String password) {
        driver.findElement(USERNAME_INPUT).sendKeys(userName);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(SIGNIN_BUTTON).click();
    }

    @DataProvider()
    public static Object[][] testData() {
        return new Object[][]{
                {"", "", "Empty UserName and Password scenario"},
                {"userName", "", "Password Empty scenario"},
                {"", "password", "UserName empty scenario"},
                {"userName", "password", "Valid UserName and Password Scenario"}

        };
    }

}
