package org.myProject.userActions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.myProject.driver.SeleniumDriver;
import org.myProject.enums.ActionType;
import org.myProject.enums.WaitStrategy;
import org.myProject.reportManager.ExtentLogger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static org.myProject.frameworkComstants.Constants.MEDIUM_WAIT;
import static org.myProject.userActions.CustomUserActions.highlightElement;
import static org.myProject.userActions.CustomWait.performExplicitWait;


public class CustomVerifications {
    private static final Logger logger = LogManager.getLogger(CustomVerifications.class.getSimpleName());

    private static final String MESSAGE_PREFIX = "<b><i>";
    private static final String MESSAGE_SUFFIX = "</b></i>";
    public static void customAssertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            ExtentLogger.pass(message + "   |   <b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: </i> </b>" + expected);
        } catch (AssertionError assertionError) {
            ExtentLogger.fail(message + "   |   <b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: </i> </b>" + expected);
            Assert.fail(message);
        }
    }

    public static void customAssertTrue(boolean result, String message) {
        try {
            Assert.assertTrue(result);
            ExtentLogger.pass("<b><i>" + message + "</b></i> ==> is visible");
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + message + "</b></i> ==> not visible"+assertionError);
            Assert.fail(message);
        }
    }
    public static boolean verifyIfElementContainsText(WebElement element,String textElement,String key){
        boolean status=false;
        System.out.println("past Medicine :"+element.getText());
        try {
            if (performExplicitWait(element, WaitStrategy.VISIBLE).isDisplayed()) {
                WebDriverWait webDriverWait=new WebDriverWait(SeleniumDriver.getDriver(), Duration.ofSeconds(MEDIUM_WAIT));
                webDriverWait.until(ExpectedConditions.textToBePresentInElement(element,textElement));
                System.out.println("past Medicine :"+element.getText());
                status=element.getText().contains(textElement);
                ExtentLogger.info("[" + textElement + "] => is visible on =>"+key);
                status = true;
            } else {
                ExtentLogger.info("[" + textElement + "] => is not visible on =>"+key);
            }
        } catch (NoSuchElementException e) {
            ExtentLogger.fail("Element with key [" + key + "] not found   =>"+e);
        }
        return status;
    }


    public static boolean isVisible(WebElement element, String elementKey) {
        try {
            // Perform explicit wait for visibility
            element = performExplicitWait(element, WaitStrategy.VISIBLE);
            // Check if element is displayed and log accordingly
            if (element.isDisplayed()) {
                ExtentLogger.pass(String.format("[%s] element is visible", elementKey));
                logger.info(String.format("[%s] element is visible", elementKey));

                return true;
            } else {
                ExtentLogger.fail(String.format("[%s] element is not visible", elementKey));
                logger.error(String.format("[%s] element is not visible", elementKey));
                return false;
            }
        } catch (NullPointerException e) {
            ExtentLogger.fail(String.format("[%s] Element not found: %s", elementKey, e.getMessage()));
            logger.error(String.format("[%s] Element not found: %s", elementKey, e.getMessage()));
            return false;
        }
    }
    public static void ifVisibleThenAction(WebElement element, String elementKey, ActionType actionKey, String value) {
        try {
            if(element.isDisplayed()) {
                switch (actionKey) {
                    case CLICK:
                        performExplicitWait(element, WaitStrategy.CLICKABLE).click();
                        ExtentLogger.info("User clicked on :=>" + "[" + elementKey + "]");
                        logger.info("User clicked on :=>" + "[" + elementKey + "]");
                        break;
                    case INPUT:
                        element.sendKeys(value);
                        ExtentLogger.info("User inputted [" + value + "\"] on :=>" + "[" + elementKey + "]");
                        logger.info("User inputted [" + value + "\"] on :=>" + "[" + elementKey + "]");
                        break;
                    default:
                        ExtentLogger.info("User clicked on :=>" + "[" + elementKey + "]");
                        logger.info("User clicked on :=>" + "[" + elementKey + "]");
                        break;
                }
            }
        } catch (Exception e) {
            ExtentLogger.info("Unable to perform action [" + actionKey + "] as this element is not visible=>"+elementKey);
            logger.info("Unable to perform action [" + actionKey + "]  as this element is not visible=>"+elementKey);
        }
    }

    public static boolean isEnabled(WebElement element, String key) {
        boolean status=false;
        try {
            highlightElement(element);
           element.isEnabled();
            ExtentLogger.info("[" + key + "] => is enabled");
            status= true;
        } catch (AssertionError e) {
            ExtentLogger.fail("Element with key [" + key + "] is not enabled==>"+e,element);
        }
        return status;
    }
    public static boolean isClickable(WebElement element, String key) {
        boolean status=false;
        try {
            performExplicitWait(element,WaitStrategy.CLICKABLE);
            ExtentLogger.info("[" + key + "] => is clickable");
            status= true;
        } catch (AssertionError e) {
            ExtentLogger.fail("Element with key [" + key + "] is not clickable==>"+e,element);
        }
        return status;
    }

    public static boolean isAvailable(List<WebElement> elements, String key) {
        if (!elements.isEmpty()) {
            ExtentLogger.info("[" + key + "] => is available");
            return true;
        } else {
            ExtentLogger.info("[" + key + "] => is not available");
            return false;
        }
    }
}
