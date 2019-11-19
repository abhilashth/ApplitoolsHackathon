package com.applitools.hackathon.traditional;

import com.applitools.hackathon.constants.WebElements;
import com.applitools.hackathon.utils.DataTable;
import com.applitools.hackathon.utils.OrderedComparator;
import com.applitools.hackathon.utils.RecentTransactionsTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.applitools.hackathon.constants.WebElements.*;

/**
 * Created by Abhilash Thaduka on 13-11-2019.
 */
@RunWith(DataProviderRunner.class)
public class TraditionalTests {

    private static WebDriver driver;
    private static final String URL = "https://demo.applitools.com/hackathonV2.html";

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "E:/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1376, 1032));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL);
    }

    @Test
    public void loginTest() {
        //  driver.get(URL);

        // Assert placeholder for username field
        Assert.assertEquals("User name placeholder is not matching !!", "John Smith",
                driver.findElement(USERNAME_INPUT).getAttribute("placeholder").trim());

        // Assert placeholder for password field
        Assert.assertEquals("User name placeholder is not matching !!", "ABC$*1@",
                driver.findElement(WebElements.PASSWORD_INPUT).getAttribute("placeholder").trim());

        // Assert placeholder for password field
        Assert.assertEquals("Log In button text is not matching !!", "Log In",
                driver.findElement(SIGNIN_BUTTON).getText().trim());

        // Assert RememberMe text
        Assert.assertTrue("RememberMe Element is not displayed !!",
                driver.findElement(WebElements.REMEMBER_ME).isDisplayed());

        // Assert RememberMe checkbox
        Assert.assertTrue("RememberMe checkbox is not enabled !!",
                driver.findElement(WebElements.REMEMBER_ME_CHECKBOX).isEnabled());

        // Assert if Twitter image is displayed
        Assert.assertTrue("Twitter image is not displayed !!",
                driver.findElement(WebElements.TWITTER_IMG).isDisplayed());

        // Assert if Facebook image is displayed
        Assert.assertTrue("Facebook Image is not displayed !!",
                driver.findElement(WebElements.FB_IMG).isDisplayed());

        // Assert for Header text
        Assert.assertTrue("Header text is not matching !!",
                driver.findElement(WebElements.HEADER_TEXT).getText().trim().contains("Logout Form"));

        // Assert if username label exist
        Assert.assertTrue("USERNAME_LABEL element is not found !!",
                driver.findElement(WebElements.USERNAME_LABEL) instanceof WebElement);

        // Assert if password label exist
        Assert.assertTrue("PASSWORD_LABEL element is not found !!",
                driver.findElement(WebElements.PASSWORD_LABEL) != null);
    }

    @Test
    @UseDataProvider("testData")
    public void testLoginFunctionality(String userName, String password,
                                       String expectedTextMessage, String locator) {
        driver.get(URL);
        loginWithUserNameAndPassword(userName, password);
        String actual = driver.findElement(By.className(locator)).getText();
        Assert.assertEquals(expectedTextMessage, actual);

    }

    @Test
    public void tableSortTest() {
        loginWithUserNameAndPassword("Dummy", "Dummy");

        driver.findElement(By.xpath("//th[@id='amount']")).click();
        DataTable dataTable = new RecentTransactionsTable();
        // Comparing table data as List of maps
        List<Map<String, String>> actualData = dataTable.getTable();

        List<Map<String, String>> expectedData = getRecentTransactionData();

        OrderedComparator.compare(expectedData, actualData);
    }

    @Test
    public void canvasChartsTest() {
        loginWithUserNameAndPassword("Dummy", "Dummy");

        driver.findElement(COMPARE_EXPENSES).click();
        ArrayList<String> labels = (ArrayList<String>) executeScript("return barChartData.labels");

        List<String> expectedLabels = Arrays.asList("January", "February", "March", "April", "May", "June", "July");
        List<String> expectedYear = Arrays.asList("2017", "2018", "2019");
        Map<String, List<Long>> expectedData = new HashMap<>();
        expectedData.put("2017", Arrays.asList(10L, 20L, 30L, 40L, 50L, 60L, 70L));
        expectedData.put("2018", Arrays.asList(8L, 9L, -10L, 10L, 40L, 60L, 40L));
        expectedData.put("2019", Arrays.asList(5L, 10L, 15L, 20L, 25L, 30L, 35L));

        Assert.assertEquals(labels, expectedLabels);
        for (int i = 0; i < (Long) executeScript("return barChartData.datasets.length"); i++) {
            String year = (String) executeScript("return barChartData.datasets[" + i + "].label");
            ArrayList<Long> yearData = (ArrayList<Long>) executeScript("return barChartData.datasets[" + i + "].data");

            Assert.assertEquals(year, expectedYear.get(i));
            Assert.assertEquals(yearData, expectedData.get(expectedYear.get(i)));
        }

        driver.findElement(ADD_DATA_SET).click();
        Long thirdYear = (Long) executeScript("return barChartData.datasets[2].label");
        ArrayList<Long> thirdYearData = (ArrayList<Long>) executeScript("return barChartData.datasets[2].data");

        Assert.assertEquals(thirdYear, Long.valueOf(expectedYear.get(2)));
        Assert.assertEquals(thirdYearData, expectedData.get(expectedYear.get(2)));
    }


    @Test
    public void dynamicContentTest() {
        driver.get(URL + "?showAd=true");
        loginWithUserNameAndPassword("Dummy", "Dummy");
        Assert.assertTrue(driver.findElement(FLASHSALE_GIF).isDisplayed());
        Assert.assertTrue(driver.findElement(FLASHSALE2_GIF).isDisplayed());
    }

    @DataProvider()
    public static Object[][] testData() {
        return new Object[][]{
                {"", "", "Please enter both username and password", "alert-warning"},
                {"userName", "", "Password must be present ", "alert-warning"},
                {"", "password", "Username must be present", "alert-warning"},
                {"userName", "password", "Jack Gomez", "logged-user-name"}

        };

    }

    private void loginWithUserNameAndPassword(String userName, String password) {
        driver.findElement(USERNAME_INPUT).sendKeys(userName);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(SIGNIN_BUTTON).click();

    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }


    public List<Map<String, String>> getRecentTransactionData() {

        List<Map<String, String>> expectedData = Lists.newArrayList();
        Map<String, String> map5 = Maps.newHashMap();
        map5.put("DATE", "Jan 7th9:51am");
        map5.put("STATUS", "Complete");
        map5.put("DESCRIPTION", "Ebay Marketplace");
        map5.put("AMOUNT", "- 244.00 USD");
        map5.put("CATEGORY", "Ecommerce");
        expectedData.add(map5);

        Map<String, String> map3 = Maps.newHashMap();
        map3.put("DATE", "Yesterday7:45am");
        map3.put("STATUS", "Pending");
        map3.put("DESCRIPTION", "MailChimp Services");
        map3.put("AMOUNT", "- 320.00 USD");
        map3.put("CATEGORY", "Software");
        expectedData.add(map3);


        Map<String, String> map4 = Maps.newHashMap();
        map4.put("DATE", "Jan 23rd2:7pm");
        map4.put("STATUS", "Pending");
        map4.put("DESCRIPTION", "Shopify product");
        map4.put("AMOUNT", "+ 17.99 USD");
        map4.put("CATEGORY", "Shopping");
        expectedData.add(map4);


        Map<String, String> map6 = Maps.newHashMap();
        map6.put("DATE", "Jan 9th7:45pm");
        map6.put("STATUS", "Pending");
        map6.put("DESCRIPTION", "Templates Inc");
        map6.put("AMOUNT", "+ 340.00 USD");
        map6.put("CATEGORY", "Business");
        expectedData.add(map6);

        Map<String, String> map2 = Maps.newHashMap();
        map2.put("DATE", "Jan 19th3:22pm");
        map2.put("STATUS", "Declined");
        map2.put("DESCRIPTION", "Stripe Payment Processing");
        map2.put("AMOUNT", "+ 952.23 USD");
        map2.put("CATEGORY", "Finance");
        expectedData.add(map2);

        Map<String, String> map1 = Maps.newHashMap();
        map1.put("DATE", "Today1:52am");
        map1.put("STATUS", "Complete");
        map1.put("DESCRIPTION", "Starbucks coffee");
        map1.put("AMOUNT", "+ 1,250.00 USD");
        map1.put("CATEGORY", "Restaurant / Cafe");
        expectedData.add(map1);
        return expectedData;

    }

}
