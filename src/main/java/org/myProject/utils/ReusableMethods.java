package org.myProject.utils;

import org.apache.commons.io.FileUtils;

import org.myProject.driver.SeleniumDriver;
import org.myProject.reportManager.ExtentLogger;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.myProject.configManager.ConfigFactory.getConfig;
import static org.myProject.frameworkComstants.ReportConstants.*;


public class ReusableMethods {
    public static void takeScreenshot(WebDriver driver, String filePath) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zoomScreen(int percentNumber) {
        JavascriptExecutor js = (JavascriptExecutor) SeleniumDriver.getDriver();
        // Execute JavaScript to zoom out the page (90% of the original size)
        js.executeScript("document.body.style.zoom='" + percentNumber + "%'");

    }

    public static String getBase64Image() {
        return ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static String getBase64ElementImage(WebElement element) {
        return element.getScreenshotAs(OutputType.BASE64);
    }

    public static void countRows(List<WebElement> elementList, String rowBelongsTo) {
        ExtentLogger.info("Total No of " + rowBelongsTo + " table :=>" + elementList.size());
    }

    public static String getBrowserIcon() {
        if (getConfig().browserName().equalsIgnoreCase("chrome")) {
            return ICON_BROWSER_PREFIX + ICON_BROWSER_CHROME + ICON_BROWSER_SUFFIX;
        } else {
            return ICON_BROWSER_PREFIX + getConfig().browserName().toLowerCase() + ICON_BROWSER_SUFFIX;
        }
    }

    public static String getCurrentDate() {
        Date date = new Date();
        return date.toString().replace(":", "_").replace(" ", "_");
    }

    public static String todayDate(String dateFormat) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    public static String nthDateAfterCurrentDate(int nth) {
        // Get today's date
        LocalDate today = LocalDate.now();
        // Get the date two days after today
        LocalDate dateAfterTwoDays = today.plusDays(nth);
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH);
        // Format the date
        String formattedDate = dateAfterTwoDays.format(formatter);
        // Print the formatted date
        System.out.println("Date two days after today: " + formattedDate);
        return formattedDate;
    }

    public static void main(String[] args) {
        System.out.println(todayDate("dd-MMMM-yyyy"));
        nthDateAfterCurrentDate(2);
    }
}
