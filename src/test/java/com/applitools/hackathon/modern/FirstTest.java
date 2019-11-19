package com.applitools.hackathon.modern;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Abhilash Thaduka on 13-11-2019.
 */
@RunWith(JUnit4.class)
public class FirstTest {

    private static BatchInfo batchInfo;
    private static EyesRunner eyesRunner;
    private static Eyes eyes;
    private static WebDriver driver;
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
    }

    @Test
    public void loginTest() {
        driver.get("https://demo.applitools.com/hackathon.html");

        driver.findElement(By.id("log-in")).click();

        // Set AUT's name, test name and viewport size (width X height)
        // We have set it to 800 x 600 to accommodate various screens. Feel free to
        // change it.
        eyes.open(driver, "Login App", "Login Page Test", new RectangleSize(800, 600));

        // Visual checkpoint - take screenshot , SO AI can analyze
        eyes.checkWindow("Login Window");

        // End the test.
        eyes.closeAsync();

    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
        eyes.abort();
    }
}
