package org.myProject.userActions;

import org.myProject.driver.SeleniumDriver;
import org.myProject.enums.LocatorType;
import org.myProject.reportManager.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LocatorHelper {
    public static WebElement getWebElement(LocatorType locatorType, String locator) {
        WebDriver webDriver = SeleniumDriver.getDriver();
        WebElement element = null;
        switch (locatorType) {

            case XPATH:
                element=webDriver.findElement(By.xpath(locator));
                break;
            case CSS:
                element=webDriver.findElement(By.cssSelector(locator));
                break;
            case CLASS_NAME:
                element=webDriver.findElement(By.className(locator));
                break;
            case ID:
                element=webDriver.findElement(By.id(locator));
                break;
            case NAME:
                element=webDriver.findElement(By.name(locator));
                break;
            case LINK_TEXT:
                element=webDriver.findElement(By.linkText(locator));
                break;
            case PARTIAL_LINK_TEXT:
                element=webDriver.findElement(By.partialLinkText(locator));
                break;
            default:
                ExtentLogger.info("Please add correct locator type :=>"+locatorType);
        }
        return element;

    }
    public static List<WebElement> getWebElements(LocatorType locatorType, String locator) {
        WebDriver webDriver = SeleniumDriver.getDriver();
        List<WebElement> element = null;
        switch (locatorType) {

            case XPATH:
                element=webDriver.findElements(By.xpath(locator));
                break;
            case CSS:
                element=webDriver.findElements(By.cssSelector(locator));
                break;
            case CLASS_NAME:
                element=webDriver.findElements(By.className(locator));
                break;
            case ID:
                element=webDriver.findElements(By.id(locator));
                break;
            case NAME:
                element=webDriver.findElements(By.name(locator));
                break;
            case LINK_TEXT:
                element=webDriver.findElements(By.linkText(locator));
                break;
            case PARTIAL_LINK_TEXT:
                element=webDriver.findElements(By.partialLinkText(locator));
                break;
            default:
                ExtentLogger.info("Please add correct locator type :=>"+locatorType);
        }
        return element;

    }
}
