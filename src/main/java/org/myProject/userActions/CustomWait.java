package org.myProject.userActions;

import org.myProject.driver.SeleniumDriver;
import org.myProject.enums.WaitStrategy;
import org.myProject.reportManager.ExtentLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.myProject.frameworkComstants.Constants.EXPLICIT_WAIT_TIME;
import static org.myProject.frameworkComstants.Constants.LONG_WAIT;

public class CustomWait {
    private CustomWait() {
        // Private constructor to prevent instantiation
    }

    public static void staticWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            ExtentLogger.info("User waited for [" + seconds + "] seconds");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            ExtentLogger.fail("Static wait interrupted: " + e);
        }
    }

    public static void waitTillElementVisible(WebDriver driver, String element) {
        WebElement elementWithWait;
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(LONG_WAIT))
                .pollingEvery(Duration.ofSeconds(5L))
                .ignoring(NoSuchElementException.class);
        if (element.contains("//")) {
            elementWithWait = wait.until(driver1 -> driver1.findElement(By.xpath(element)));
        } else {
            elementWithWait = wait.until(driver1 -> driver1.findElement(By.cssSelector(element)));
        }
        highlightElement(SeleniumDriver.getDriver(), elementWithWait);
    }

    public static WebElement performExplicitWait(WebElement element, WaitStrategy waitStrategy) {
        WebElement elementWithWait = null;
        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), Duration.ofSeconds(EXPLICIT_WAIT_TIME));
        Actions actions = new Actions(SeleniumDriver.getDriver());
        actions.scrollToElement(element).perform();
        try {
            switch (waitStrategy) {
                case CLICKABLE:
                    elementWithWait = wait.until(ExpectedConditions.elementToBeClickable(element));
                    //  ExtentLogger.info("User waited for [" + EXPLICIT_WAIT_TIME + "] seconds until element was clickable");
                    break;
                case VISIBLE:
                    elementWithWait = wait.until(ExpectedConditions.visibilityOf(element));
                    //ExtentLogger.info("User waited for [" + EXPLICIT_WAIT_TIME + "] seconds until element was visible");
                    break;
                case PRESENCE:
                    elementWithWait = wait.until(ExpectedConditions.visibilityOf(element));
                    // ExtentLogger.info("User waited for [" + EXPLICIT_WAIT_TIME + "] seconds until element was visible");
                    break;
                case NONE:
                    elementWithWait = element;
                    break;
                default:
                    ExtentLogger.info("Please add a correct waiting strategy");
                    break;
            }
            highlightElement(SeleniumDriver.getDriver(), elementWithWait);
        } catch (Exception e) {
            ExtentLogger.fail(element + "=>> Element not found within the wait time, hence returning null as locator, " +
                    "related action might not work: " + e.fillInStackTrace());
        }
        return elementWithWait;
    }

    private static void highlightElement(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.border='4px solid red'", element);
            Thread.sleep(100); // Optional: keep the highlight for 1 second
            jsExecutor.executeScript("arguments[0].style.border=''", element);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            ExtentLogger.fail("Highlighting interrupted: " + e);
        } catch (WebDriverException e) {
            ExtentLogger.fail("Error during element highlighting: " + e);
        }
    }

}
