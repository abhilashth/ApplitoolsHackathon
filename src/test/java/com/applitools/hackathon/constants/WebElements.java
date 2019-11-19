package com.applitools.hackathon.constants;

import org.openqa.selenium.By;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

/**
 * Created by Abhilash Thaduka on 13-11-2019.
 */
public interface WebElements {

    By SIGNIN_BUTTON = id("log-in");
    By USERNAME_INPUT = xpath("//input[@id='username']");
    By PASSWORD_INPUT = xpath("//input[@id='password']");
    By USERNAME_LABEL = xpath("//label[text()='Username']");
    By PASSWORD_LABEL = xpath("//label[text()='Pwd']");
    By REMEMBER_ME = xpath("//label[text()='Remember Me']");
    By REMEMBER_ME_CHECKBOX = xpath("//label[text()='Remember Me']/descendant::input");
    By TWITTER_IMG = xpath("//img[contains(@src,\"twitter\")]");
    By FB_IMG = xpath("//img[contains(@src,\"facebook\")]");
    By HEADER_TEXT = xpath("//h4[@class='auth-header']");
    By COMPARE_EXPENSES = id("showExpensesChart");
    By ADD_DATA = id("addDataset");
    By FLASHSALE_GIF = cssSelector("img[src='img/flashSale.gif']");
    By FLASHSALE2_GIF = cssSelector("img[src='img/flashSale2.gif']");
    By ADD_DATA_SET = cssSelector("button#addDataset");
}
