package com.applitools.hackathon.utils;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public abstract class DataTable {

    public List<Map<String, String>> getTable() {
        final List<Map<String, String>> table = new ArrayList<>();
        final List<String> headers = getHeaders().stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
        final List<List<WebElement>> rows = getTableRows();
        rows.subList(getStartRow(), rows.size()).stream().forEach(htmlRow -> {
            final Map<String, String> row = new HashMap<>();
            IntStream.range(0, htmlRow.size()).forEach(i -> {
                row.put(headers.get(i), htmlRow.get(i).getText());
            });
            table.add(row);
        });
        return table;
    }


    protected abstract List<List<WebElement>> getTableRows();


    protected abstract List<WebElement> getHeaders();


    protected int getStartRow() {
        return 0;
    }


}
