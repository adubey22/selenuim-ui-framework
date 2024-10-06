package org.myProject.userActions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.myProject.driver.SeleniumDriver;
import org.myProject.enums.WaitStrategy;
import org.myProject.reportManager.ExtentLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.myProject.driver.SeleniumDriver.getDriver;
import static org.myProject.enums.WaitStrategy.VISIBLE;
import static org.myProject.frameworkComstants.Constants.LONG_WAIT;
import static org.myProject.userActions.CustomWait.performExplicitWait;
import static org.myProject.userActions.CustomWait.staticWait;


public class CustomUserActions {
    private static final String MESSAGE_PREFIX = "<b><i>";
    private static final String MESSAGE_SUFFIX = "</b></i>";
    private static final Logger logger = LogManager.getLogger(CustomUserActions.class.getSimpleName());

    public static void getURL(String url) {
        try {
            WebDriver driver = getDriver();
            if (driver == null) {
                throw new IllegalStateException("Driver is null");
            }
            driver.get(url);
            staticWait(1);
            WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_WAIT));
            driverWait.until(ExpectedConditions.urlContains(url));
            ExtentLogger.info("Navigated to: " + url);
            logger.info("Navigated to: " + url);
        } catch (Exception e) {
            String errorMessage = "Unable to navigate to: [" + url + "] due to exception: " + e.getMessage();
            ExtentLogger.fail(errorMessage);
            logger.error(errorMessage, e);
        }
    }
    public static void navigateTo(String url) {
        try {
            WebDriver driver = getDriver();
            getDriver().navigate().to(url);
            staticWait(1);
            WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_WAIT));
            driverWait.until(ExpectedConditions.urlContains(url));
            ExtentLogger.info("Navigated  to :=>" + url);
            logger.info("Navigated  to :=>" + url);

        } catch (Exception e) {
            //log failure in extent
            ExtentLogger.fail("Unable to navigate to :=>[" + url + "] due to exception: " + e);
            logger.error("Unable to navigate to :=>[" + url + "] due to exception: " + e);

        }
    }

    public static void goToCustom(String url) {
        try {
            WebDriver driver = getDriver();
            driver.get(url);
            staticWait(1);
            WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_WAIT));
            driverWait.until(ExpectedConditions.urlToBe(url));
            ExtentLogger.info("Navigated to: " + url);
        } catch (Exception e) {
            ExtentLogger.fail("Unable to navigate to: [" + url + "] due to exception: " + e);
        }
    }

    public static void navigateBack() {
        String currentUrl = getDriver().getCurrentUrl();
        try {
            ExtentLogger.info("Current URL before navigation back: " + currentUrl);
            getDriver().navigate().back();
            staticWait(1);
            ExtentLogger.info("Navigated back to: " + getDriver().getCurrentUrl());
        } catch (Exception e) {
            //log failure in extent
            ExtentLogger.fail("Unable to navigate to back from [" + currentUrl + "] due to exception: " + e);
        }
    }

    public static void navigateAndWaitUntilElementVisible(String url, WebElement element) {
        try {
            WebDriver driver = getDriver();
            ExtentLogger.info("Navigating  to :=>" + url);
            getDriver().get(url);
            staticWait(1);
            WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_WAIT));
            driverWait.until(ExpectedConditions.urlToBe(url));
            performExplicitWait(element, VISIBLE);
            ExtentLogger.info("Navigated  to :=>" + url);
        } catch (Exception e) {
            //log failure in extent
            ExtentLogger.fail("Unable to navigate to :=>[" + url + "] due to exception: " + e);
        }
    }

    public static boolean clickCustom(WebElement element, String fieldName) {
        boolean status = false;
        try {
            performExplicitWait(element, WaitStrategy.CLICKABLE).click();
            ExtentLogger.info("User clicked on :=>" + "[" + fieldName + "]");
            logger.info("User clicked on :=>" + "[" + fieldName + "]");

            status = true;
        } catch (Exception e) {
            //log failure in extent
            ExtentLogger.fail("Unable to click on field: " + fieldName + " due to exception: " + e);
            logger.error("User clicked on :=>" + "[" + fieldName + "]");

        }
        return status;
    }

    public static void clickElementWithActions(WebElement element, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            performExplicitWait(element, WaitStrategy.CLICKABLE);
            actions.moveToElement(element).click(element).build().perform();
            ExtentLogger.info("User clicked on field: [" + fieldName + "]");
            logger.info("User clicked on field: [" + fieldName + "]");

        } catch (Exception e) {
            //log failure in extent
            ExtentLogger.fail("Unable to click on field: " + fieldName + " due to exception: " + e);
            logger.error("Unable to click on field: " + fieldName + " due to exception: " + e);
        }
    }

    public static void highlightElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) SeleniumDriver.getDriver();
//            jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
//            Thread.sleep(1000); // Wait for 1 second to highlight
//            jsExecutor.executeScript("arguments[0].style.border=''", element);
        jsExecutor.executeScript("arguments[0].style.border='3px solid red'; setTimeout(() => arguments[0].style.border='', 1000)", element);

    }

    public static void clickElementWithJS(WebElement element, String fieldName) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
            WebElement elementWithWait = performExplicitWait(element, WaitStrategy.CLICKABLE);
            jsExecutor.executeScript("arguments[0].click();", elementWithWait);
            ExtentLogger.info("User clicked on field: [" + fieldName + "]");
            logger.info("User clicked on field: [" + fieldName + "]");
        } catch (Exception e) {
            ExtentLogger.fail("Unable to click on field: " + fieldName + " due to exception: " + e);
            logger.error("Unable to click on field: " + fieldName + " due to exception: " + e);
        }
    }

    public static void clickElementEnterButton(WebElement element, String fieldName) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).sendKeys(Keys.ENTER).perform();
            ExtentLogger.info("User clicked on field: [" + fieldName + "]");
            logger.info("User clicked on field: [" + fieldName + "]");
        } catch (Exception e) {
            ExtentLogger.fail("Unable to click on field: " + fieldName + " due to exception: " + e);
            logger.error("Unable to click on field: " + fieldName + " due to exception: " + e);
        }
    }

    public static void submitCustom(WebElement element, String fieldName) {
        try {
            WebElement visibleFormElement = performExplicitWait(element, VISIBLE);
            visibleFormElement.submit();
            ExtentLogger.info("User clicked on :=>" + "[" + fieldName + "]");
            logger.info("User clicked on :=>" + "[" + fieldName + "]");
        } catch (Exception e) {
            //log failure in extent
            ExtentLogger.fail("Unable to click on field: " + fieldName + " due to exception: " + e);
            logger.error("User clicked on :=>" + "[" + fieldName + "]");
        }
    }

    public static void sendKeysCustom(WebElement element, String inputVal, String fieldName) {
        try {
            WebElement elementWithWait = performExplicitWait(element, VISIBLE);
            elementWithWait.sendKeys(inputVal);
            ExtentLogger.info("User entered [" + inputVal + "] on: " + fieldName);
            logger.info("User entered [" + inputVal + "] on: " + fieldName);
        } catch (Exception e) {
            String errorMessage = "Value enter in field: " + fieldName + " failed due to exception: " + e;
            ExtentLogger.fail(errorMessage);
            logger.error(errorMessage);
        }
    }
    public static void sendTextCharaterByCharacter(WebElement element, String inputVal, String fieldName) {
        try {
            for (char c : inputVal.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                Thread.sleep(100);
            }
            ExtentLogger.info("User entered [" + inputVal + "] on: " + fieldName);
            logger.info("User entered [" + inputVal + "] on: " + fieldName);
        } catch (Exception e) {
            String errorMessage = "Value enter in field: " + fieldName + " failed due to exception: " + e;
            ExtentLogger.fail(errorMessage);
            logger.error(errorMessage);
        }
    }


    public static void setValueWithJS(WebElement element, String inputVal, String fieldName) {
        try {
            performExplicitWait(element, VISIBLE);
            JavascriptExecutor js = (JavascriptExecutor) SeleniumDriver.getDriver();
            //js.executeScript("arguments[0].value='" + inputVal + "';", element);
            js.executeScript("arguments[0].setAttribute('value','" + inputVal + "')", element);
            ExtentLogger.info("User enter [" + inputVal + "] on :=>" + fieldName);
            logger.info("User enter [" + inputVal + "] on :=>" + fieldName);

        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: " + fieldName + " is failed due to exception: " + e);
            logger.error("Value enter in field: " + fieldName + " is failed due to exception: " + e);

        }
    }

    public static void setValueByTypingCharAndSelectFirstSuggestion(WebElement element, String inputVal, String fieldName) {
        try {
            clickElementWithJS(element, fieldName);
            clearCustom(element, fieldName);
            // element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE); // Clear existing text
            for (char c : inputVal.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                Thread.sleep(100);
            }
            ExtentLogger.info("User enter [" + inputVal + "] on :=>" + fieldName); // Add delay between each character
            Actions actions = new Actions(getDriver());
            WebElement e = getDriver().findElement(By.xpath("//div[text()='" + inputVal + "']"));
            actions.moveToElement(element).moveToElement(e).click().build().perform();
            ExtentLogger.info("User enter [" + inputVal + "] on :=>" + fieldName);
            logger.info("User enter [" + inputVal + "] on :=>" + fieldName);

        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: " + fieldName + " failed due to exception: " + e);
            logger.error("Value enter in field: " + fieldName + " failed due to exception: " + e);
        }
    }

    public static void setValueWithActions(WebElement element, String inputVal, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).click().build().perform();
            actions.sendKeys(inputVal).build().perform();
            ExtentLogger.info("User entered [" + inputVal + "] on: " + fieldName);
            logger.info("User entered [" + inputVal + "] on: " + fieldName);
        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: " + fieldName + " failed due to exception: " + e);
            logger.error("Value enter in field: " + fieldName + " failed due to exception: " + e);
        }
    }

    public static void setOffsetWithActions(WebElement element, String inputVal, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            actions.moveByOffset(element.getRect().x, element.getRect().y).click().sendKeys(inputVal, Keys.ENTER).build().perform();
            ExtentLogger.info("User entered [" + inputVal + "] on: " + fieldName);
            logger.info("User entered [" + inputVal + "] on: " + fieldName);
        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: " + fieldName + " failed due to exception: " + e);
            logger.error("Value enter in field: " + fieldName + " failed due to exception: " + e);
        }
    }

    public static void setValueWithKey(WebElement element, String inputVal, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).click().sendKeys(Keys.NUMPAD4).build().perform();
            ExtentLogger.info("User entered [" + inputVal + "] on: " + fieldName);
            logger.info("User entered [" + inputVal + "] on: " + fieldName);
        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: " + fieldName + " failed due to exception: " + e);
            logger.error("Value enter in field: " + fieldName + " failed due to exception: " + e);
        }
    }
//    public static void setValueWithActions(WebElement element, String inputVal, String fieldName) {
//        try {
//            Actions actions = new Actions(getDriver());
//            actions.moveToElement(element).click().sendKeys(inputVal).build().perform();
//            ExtentLogger.info("User enter [" + inputVal + "] on :=>" + fieldName);
//            logger.info("User enter [" + inputVal + "] on :=>" + fieldName);
//        } catch (Exception e) {
//            ExtentLogger.fail("Value enter in field: [" + fieldName + "] is failed due to exception: " + e);
//          logger.error("Value enter in field: [" + fieldName + "] is failed due to exception: " + e);
//        }
//    }


    public static void clickInputAndSelectFromSuggestion(WebElement element, String inputVal, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element)
                    .click()
                    .sendKeys(inputVal).sendKeys(Keys.ARROW_DOWN, Keys.ENTER)
                    .build()
                    .perform();
            staticWait(2);
//            actions.moveToElement(element)
//                    .sendKeys(Keys.ARROW_DOWN, Keys.ENTER)
//                    .build()
//                    .perform();
            ExtentLogger.info("User enter [" + inputVal + "] on :=>" + fieldName);
            logger.info("User enter [" + inputVal + "] on :=>" + fieldName);
        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: [" + fieldName + "] is failed due to exception: " + e);
            logger.error("Value enter in field: [" + fieldName + "] is failed due to exception: " + e);
        }
    }

    public static void setAndClickOptionsWithActions(WebElement element, String inputVal, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element)
                    .click()
                    .sendKeys(inputVal)
                    .build()
                    .perform();
            staticWait(2);
            actions.moveToElement(element)
                    .sendKeys(Keys.ARROW_DOWN, Keys.ENTER)
                    .build()
                    .perform();
//            actions.moveToElement(element).click().sendKeys(inputVal).build().perform();
//            staticWait(4);
//            actions.moveToElement(element).sendKeys(Keys.ARROW_DOWN,Keys.ENTER).build().perform();
            ExtentLogger.info("User enter [" + inputVal + "] on :=>" + fieldName);
            logger.info("User enter [" + inputVal + "] on :=>" + fieldName);
        } catch (Exception e) {
            ExtentLogger.fail("Value enter in field: [" + fieldName + "] is failed due to exception: " + e);
            logger.error("Value enter in field: [" + fieldName + "] is failed due to exception: " + e);
        }
    }

    public static void setValueByTypingCharacterByCharacter(WebElement element, String inputVal, String fieldName) {
        try {
            Actions actions = new Actions(getDriver());
            for (char c : inputVal.toCharArray()) {
                actions.moveToElement(element).click().sendKeys(String.valueOf(c)).build().perform();
                Thread.sleep(50);
            }
            ExtentLogger.info("User entered [" + inputVal + "] on :=>" + fieldName);
            logger.info("User entered [" + inputVal + "] on :=>" + fieldName);
        } catch (InterruptedException e) {
            String errorMessage = "Value enter in field: " + fieldName + " is failed due to exception: " + e;
            ExtentLogger.fail(errorMessage);
            logger.error(errorMessage);
            throw new RuntimeException(e);
        }
    }

    //    public static void setValueByTypingCharacterByCharacter(WebElement element, String inputVal, String fieldName) {
//        try {
//            Actions actions = new Actions(getDriver());
//            ExtentLogger.info("User is going to enter [" + inputVal + "] on :=>" + fieldName);
//            for (char c : inputVal.toCharArray()) {
//                actions.moveToElement(element).sendKeys(String.valueOf(c));
//                Thread.sleep(50); // Adjust pause time as needed (in milliseconds)
//                ExtentLogger.info("User entered [" + c + "] on :=>" + fieldName);
//                logger.info("User entered [" + c + "] on :=>" + fieldName);
//
//            }
//        } catch (InterruptedException e) {
//            ExtentLogger.fail("Value enter in field: " + fieldName + " is failed due to exception: " + e);
//            logger.error("Value enter in field: " + fieldName + " is failed due to exception: " + e);
//            throw new RuntimeException(e);
//        }
//    }
// Clear data from a field
    public static void clearCustom(WebElement element, String fieldName) {
        try {
            performExplicitWait(element, VISIBLE);
            String fieldValue = element.getAttribute("value");
            for (int i = 0; i < fieldValue.length(); i++) {
                element.sendKeys(Keys.BACK_SPACE);
            }
            ExtentLogger.pass(fieldName + " ==> Data Cleared Successfully!");
            logger.info(fieldName + " ==> Data Cleared Successfully!");
        } catch (Exception e) {
            ExtentLogger.fail(fieldName + " ==> Data Cleared due to exception: " + e);
            logger.error(fieldName + " ==> Data Cleared due to exception: " + e);
        }
    }

    //clear data from field
//    public static void clearCustom(WebElement element, String fieldName) {
//        try {
//            performExplicitWait(element, WaitStrategy.VISIBLE);
//            // Clear the text box using clear() and sendKeys
////            Actions actions = new Actions(SeleniumDriver.getDriver());
////            actions.sendKeys(element, Keys.chord(Keys.CONTROL, "a")).sendKeys(Keys.DELETE).perform();
////            element.clear();
////            element.sendKeys(Keys.CONTROL + "a");
////            element.sendKeys(Keys.DELETE);
//            String text = element.getAttribute("value");
//            for (int i = 0; i < text.length(); i++) {
//                element.sendKeys(Keys.BACK_SPACE);
//            }
//            ExtentLogger.pass(fieldName + "==> Data Cleared Successfully! ");
//            logger.info(fieldName + "==> Data Cleared Successfully! ");
//        } catch (Exception e) {
//            ExtentLogger.fail(fieldName + "==> Data Cleared due to exception: " + e);
//            logger.error(fieldName + "==> Data Cleared due to exception: " + e);
//        }
//    }

    public static void clearCustomJs(WebElement element, String fieldName) {
        try {
            performExplicitWait(element, VISIBLE);
            JavascriptExecutor js = (JavascriptExecutor) SeleniumDriver.getDriver();
            js.executeScript("arguments[0].value = '';", element);
            ExtentLogger.pass(fieldName + "==> Data Cleared Successfully! ");
        } catch (Exception e) {
            ExtentLogger.fail(fieldName + "==> Data Cleared due to exception: " + e);
        }
    }

    public static void selectDropDownByVisibleTextCustom(WebElement element, String ddVisibleText, String fieldName) {
        try {
            Select s = new Select(performExplicitWait(element, VISIBLE));
            s.selectByVisibleText(ddVisibleText);
            ExtentLogger.pass(fieldName + "==> Dropdown Value Selected by visible text: " + ddVisibleText);
            logger.info(fieldName + "==> Dropdown Value Selected by visible text: " + ddVisibleText);
        } catch (Exception e) {
            ExtentLogger.fail("Dropdown value not selected for field: [" + fieldName + "]  due to exception: " + e);
            logger.error("Dropdown value not selected for field: [" + fieldName + "]  due to exception: " + e);
        }
    }

    public static void selectDropDownByValueCustom(WebElement element, String ddValue, String fieldName) {
        try {
            Select s = new Select(performExplicitWait(element, VISIBLE));
            s.selectByValue(ddValue);
            ExtentLogger.pass(fieldName + "==> Dropdown Value Selected by visible value: " + ddValue);
        } catch (Exception e) {
            ExtentLogger.fail("Dropdown value not selected for value: [" + fieldName + "]  due to exception: " + e);
        }
    }

    public static void selectDropDownByIndexCustom(WebElement element, int ddIndex, String fieldName) throws Throwable {
        try {
            Select s = new Select(performExplicitWait(element, VISIBLE));
            s.selectByIndex(ddIndex);
            ExtentLogger.pass(fieldName + "==> Dropdown Value Selected by visible index: " + ddIndex);
        } catch (Exception e) {
            ExtentLogger.fail("Dropdown value not selected for index: [" + fieldName + "]  due to exception: " + e);
        }
    }

    public static void selectSuggestionFromDropDown(WebElement element) {
        Actions actions = new Actions(SeleniumDriver.getDriver());
        actions.moveToElement(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        ExtentLogger.info("Suggestion selected");
        logger.info("Suggestion selected");
    }

    public static void selectSuggestedTextFromDropDown(String suggestionText) {
        List<WebElement> suggestions = SeleniumDriver.getDriver().findElements(By.xpath("//tbody/tr[@id='1']/td[contains(@role,'cell')]//div[contains(text(),'" + suggestionText + "')]")); // Update the XPath to match your suggestion list
        if (!suggestions.isEmpty()) {
            // Actions actions = new Actions(SeleniumDriver.getDriver());
            //actions.moveToElement(suggestions.get(0)).keyDown(Keys.ARROW_DOWN).keyDown(Keys.BACK_SPACE).perform();
            suggestions.get(0).click();
            ExtentLogger.info("Suggestion selected");
            logger.info("Suggestion selected");
        } else {
            ExtentLogger.fail("No suggestions found for: " + suggestionText);
            logger.error("No suggestions found for: " + suggestionText);
            return;
        }
    }

    //Get text from webelement
    public static String getTextCustom(WebElement element, String fieldName) {
        String text = "";
        try {
            text = performExplicitWait(element, VISIBLE).getText();
            ExtentLogger.info(fieldName + " : Text retried is:==> [" + MESSAGE_PREFIX + text + MESSAGE_SUFFIX + "]");
            logger.info(fieldName + " : Text retried is:==> [" + MESSAGE_PREFIX + text + MESSAGE_SUFFIX + "]");
            return text;
        } catch (Exception e) {
            ExtentLogger.fail(fieldName + "==> Text not retried due to exception: " + e);
            logger.error(fieldName + "==> Text not retried due to exception: " + e);

        }
        return text;
    }

    public static String getTextByCssValue(WebElement element, String fieldName) {
        String text = "";
        try {
            text = performExplicitWait(element, VISIBLE).getAttribute("value");
            ExtentLogger.info(fieldName + " : Text retried is:==> [" + MESSAGE_PREFIX + text + MESSAGE_SUFFIX + "]");
            return text;
        } catch (Exception e) {
            ExtentLogger.fail(fieldName + "==> Text not retried due to exception: " + e);

        }
        return text;
    }

    public static String getTextTextByJs(WebElement element, String fieldName) {
        String text = "";
        try {
            JavascriptExecutor js = (JavascriptExecutor) SeleniumDriver.getDriver();
            text = (String) js.executeScript("return arguments[0].value;", performExplicitWait(element, VISIBLE));
            System.out.println("Value: " + text);
            ExtentLogger.info(fieldName + " : Text retried is:==> [" + MESSAGE_PREFIX + text + MESSAGE_SUFFIX + "]");
            return text;
        } catch (Exception e) {
            ExtentLogger.fail(fieldName + "==> Text not retried due to exception: " + e);

        }
        return text;
    }

    public static WebElement getLocatorByText(String text) {
        return SeleniumDriver.getDriver().findElement(By.xpath("//*[normalize-space(text())='" + text + "']"));

    }


    public static void scrollToView(WebElement element) {
        //((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', inline:'center', block: 'nearest' });", element);

    }

    public static void scrollByPixel(int xOffset, int yOffset) {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(" + xOffset + "," + yOffset + ");", getDriver());
    }

    public static void scrollToElementTop(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void ScrollToBottom(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void ScrollToTop() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 0)");
        Actions actions = new Actions(getDriver());
        actions.sendKeys(Keys.HOME).perform();
    }

    public static void scrollTo(WebElement element) {
        // Point location=element.getLocation();
        int deltaY = element.getRect().y;
        new Actions(SeleniumDriver.getDriver())
                .scrollByAmount(0, deltaY)
                .perform();
        // ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(" + location.getX() + ", " + location.getY() + ')');
    }

    public static void scrollToElement(WebElement element) {
        new Actions(SeleniumDriver.getDriver())
                .scrollToElement(element)
                .perform();
    }
    public static void scrollFromOrigin(WebElement element) {
        if (!isElementInView(getDriver(), element)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', inline:'center', block: 'nearest' });", element);
            WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element);
            Actions actions = new Actions(getDriver());
            actions.scrollFromOrigin(scrollOrigin, 0, 250).perform();
        }
    }
    public static boolean isElementInView(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Point elementLocation = element.getLocation();
        int viewportHeight = (int) js.executeScript("return window.innerHeight;");
        int scrollPosition = (int) js.executeScript("return window.scrollY;");
        int elementBottom = elementLocation.y + element.getSize().getHeight();
        return elementLocation.y >= scrollPosition && elementBottom <= scrollPosition + viewportHeight;
    }

    public static void scrollToElement1(WebElement element) {

        if (!isElementInView(getDriver(), element)) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', inline:'center', block: 'nearest' });", element);
            WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element, 0, -50);
            new Actions(SeleniumDriver.getDriver())
                    .scrollFromOrigin(scrollOrigin, 0, 250)
                    .perform();
        }


    }

    public static void scrollToElementUsingActions(WebElement element, double scrollAmount) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollTo({ top: 0, behavior: 'smooth' });");
        // Get the viewport height
        long viewportHeight = (Long) jsExecutor.executeScript("return window.innerHeight");
        // Calculate the scroll amount (3/4 of the viewport height)
        long finalScrollAmount = (long) (viewportHeight * scrollAmount);
        // Scroll down by the calculated amount
        jsExecutor.executeScript("window.scrollBy(0,arguments[0]);", finalScrollAmount);
        jsExecutor.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', inline:'center', block: 'nearest' });", element);
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'nearest', inline: 'center', top: arguments[1]})", element, -100);

        Actions actions = new Actions(getDriver());
        actions.scrollToElement(element).perform();
    }

    //Scroll Inside an Element :  an element that has its own scrollable region.
    public static void ScrollInsideElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
// Scroll down within the element
        js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", element);
        // Scroll up within the element
        js.executeScript("arguments[0].scrollTop = 0", element);
    }

    public static void ScrollHorizontallyRight(WebElement element) {
        // Scroll right by 1000 pixels
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(1000,0)");
    }

    public static void ScrollHorizontallyLeft(WebElement element) {
        // Scroll left by 1000 pixels
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(-1000,0)");
    }

    public static void smoothScrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        boolean isElementInView = (Boolean) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return (rect.top >= 0 && " +
                        "rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                element);

        if (!isElementInView) {
            scrollToElementUsingActions(element, 0.5);

            long currentScrollPosition = (long) js.executeScript("return window.pageYOffset;");
            long newScrollPosition = currentScrollPosition + 200;
            js.executeScript("window.scrollTo(0, arguments[0]);", newScrollPosition);

            js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', inline:'center', block: 'nearest' });", element);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'nearest', inline: 'center', top: arguments[1]})", element, -100);

            new Actions(getDriver()).moveToElement(element).perform();
        }
    }

    public static void SmoothScrollToElement(WebElement element) {
        // Perform smooth scrolling to an element.
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        // Check if the element is in the viewport
        boolean isElementInView = (Boolean) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return (" +
                        "rect.top >= 0 && " +
                        "rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth)" +
                        ");",
                element);

        // If the element is not in the viewport, scroll to it
        if (!isElementInView) {
            scrollToElementUsingActions(element, 0.5);
            // Get the current scroll position of the window
            long currentScrollPosition = (long) js.executeScript("return window.pageYOffset;");
            // Calculate the new scroll position
            long newScrollPosition = currentScrollPosition + 200;
            // Scroll to the new position
            js.executeScript("window.scrollTo(0, arguments[0]);", newScrollPosition);
            // Optionally, you can scroll to the element if it's not fully in view
//            js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", element);

            js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', inline:'center', block: 'nearest' });", element);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'nearest', inline: 'center', top: arguments[1]})", element, -100);

            new Actions(getDriver())
                    .moveToElement(element)
                    .perform();

        }
    }

    public void scrollToDirection(WebElement element, String direction) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }

        JavascriptExecutor js = (JavascriptExecutor) SeleniumDriver.getDriver();

        try {
            if (direction.equalsIgnoreCase("up")) {
                js.executeScript("arguments[0].scrollIntoView(true);", element);
            } else if (direction.equalsIgnoreCase("down")) {
                js.executeScript("arguments[0].scrollIntoView(false);", element);
            } else if (direction.equalsIgnoreCase("left")) {
                js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", element);
            } else if (direction.equalsIgnoreCase("right")) {
                js.executeScript("arguments[0].scrollIntoViewIfNeeded(false);", element);
            }
        } catch (WebDriverException e) {
            // Handle the exception here or rethrow it
        }
    }

    public static void CombinedScrolling(WebElement element) {
        // Perform smooth scrolling to an element.
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        // Scroll down by 500 pixels
        js.executeScript("window.scrollBy(0,1000)");
        js.executeScript("arguments[0].scrollIntoView(true);", element);

    }

}


