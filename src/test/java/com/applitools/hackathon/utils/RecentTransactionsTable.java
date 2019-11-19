package com.applitools.hackathon.utils;


import com.applitools.hackathon.traditional.TraditionalTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.By.xpath;

public class RecentTransactionsTable extends DataTable {
    private static final String HEADER_ROW = "//table[@id='transactionsTable']//th";
    private static final String DATA_ROW_XPATH = "//table[@id='transactionsTable']//tbody//tr";


    @Override
    public List<List<WebElement>> getTableRows() {
        List<List<WebElement>> rows = new ArrayList<>();
        List<WebElement> htmlRows = TraditionalTests.getDriver().findElements(xpath(DATA_ROW_XPATH));
        htmlRows.forEach(webElement -> {
            List<WebElement> tableDataElememnts = webElement.findElements(By.xpath(".//td"));
            rows.add(tableDataElememnts);
        });
        return rows;
    }


    @Override
    public List<WebElement> getHeaders() {
        return TraditionalTests.getDriver().findElements(xpath(HEADER_ROW));
    }

}
