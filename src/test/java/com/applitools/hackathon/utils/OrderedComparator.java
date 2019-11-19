package com.applitools.hackathon.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderedComparator {

    public static <T> void compareObjects(List<T> expectedObjectList, List<T> actualObjectList, Boolean ignoreTwoWay) {
        List<Map<String, String>> actualMapList = actualObjectList.stream().map(ObjectConverter::convertObjectToMap).collect(Collectors.toList());

        List<Map<String, String>> expectedMapList = expectedObjectList.stream().map(ObjectConverter::convertObjectToMap).collect(Collectors.toList());
        compare(expectedMapList, actualMapList, ignoreTwoWay);
    }

    public static <T> void compareObjects(List<T> expectedObjectList, List<T> actualObjectList) {
        compareObjects(expectedObjectList, actualObjectList, false);
    }

    public static void compare(List<Map<String, String>> expectedData, List<Map<String, String>> actualData) {
        compare(expectedData, actualData, false);
    }

    public static void compare(List<Map<String, String>> expectedData, List<Map<String, String>> actualData, boolean ignoreTwoWayComparison) {
        List<Map<String, String>> expected = expectedData.stream().map(HashMap::new).collect(Collectors.toList());
        List<Map<String, String>> actual = actualData.stream().map(HashMap::new).collect(Collectors.toList());

        if (!ignoreTwoWayComparison) {
            if (actual.size() != expected.size()) {
                System.out.println("Mismatch in size of expected data and actual data");
                throw new AssertionError(String.format("Mismatch in size of expected data %s and actual data %s", expected.size(), actual.size()));
            }
        }
        int rowNumber = 0;
        for (Map<String, String> expectedRowData : expected) {
            Map<String, String> actualRow = actual.get(rowNumber);
            if (actualRow == null) {
                System.out.println("Below row is not found in actual  data list");
                throw new AssertionError(String.format("Row %s is not found in expected Data provided", expectedRowData));
            }
            for (String key : expectedRowData.keySet()) {
                String actualValue = actualRow.get(key);
                String expectedValue = expectedRowData.get(key);
                actualValue = (StringUtils.isBlank(actualValue) || StringUtils.equals(actualValue, "null")) ? "" : actualValue;
                expectedValue = (StringUtils.isBlank(expectedValue) || StringUtils.equals(expectedValue, "null")) ? "" : expectedValue;
                Assert.assertEquals(String.format("Mismatch found at row number %s and column %s", rowNumber + 1, key), actualValue, expectedValue);
            }
            rowNumber++;
        }
    }


}
