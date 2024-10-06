package org.myProject.reportManager;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import org.myProject.utils.ReusableMethods;
import org.openqa.selenium.WebElement;

public class  ExtentLogger {
    private ExtentLogger() {

    }
    public static void pass(String message) {
        ExtentReportDriver.getInstance().getExtent().pass(message);
    }
    public static void pass(Markup message) {
          ExtentReportDriver.getInstance().getExtent().pass(message);
    }
    public static void fail(String message) {
        ExtentReportDriver.getInstance().getExtent().fail(message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(ReusableMethods.getBase64Image()).build());    }
    public static void fail(String message, WebElement element) {
        ExtentReportDriver.getInstance().getExtent().fail(message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(ReusableMethods.getBase64ElementImage(element)).build());    }

    public static void fail(Markup message) {
          ExtentReportDriver.getInstance().getExtent().fail(message);
    }
    public static void error(String message) {
        ExtentReportDriver.getInstance().getExtent().fail(message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(ReusableMethods.getBase64Image()).build());    }

    public static void skip(String message) {
          ExtentReportDriver.getInstance().getExtent().skip(message);
    }

    public static void skip(Markup message) {
          ExtentReportDriver.getInstance().getExtent().skip(message);
    }

    public static void info(Markup message) {
          ExtentReportDriver.getInstance().getExtent().info(message);
    }

    public static void info(String message) {
          ExtentReportDriver.getInstance().getExtent().info(message);
    }

}
