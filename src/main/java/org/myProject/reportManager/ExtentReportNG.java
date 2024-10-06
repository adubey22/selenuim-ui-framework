package org.myProject.reportManager;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.lang.StringUtils;
import org.myProject.driver.SeleniumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.myProject.configManager.ConfigFactory.getConfig;
import static org.myProject.utils.ReusableMethods.getBrowserIcon;


public class ExtentReportNG {
    static ExtentReports extentReports;

    public static void extentReportSetup() throws IOException {
        if (Objects.isNull(extentReports)) {
            String reportPath = System.getProperty("user.dir") + "/Reports/ExecutionReport.html";
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportPath);
            extentSparkReporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);

            extentSparkReporter.config().setDocumentTitle("EMR UI Automaton");
            extentSparkReporter.config().setTheme(Theme.DARK);
            extentSparkReporter.config().setReportName("EMR UI Automaton");

            extentReports.setSystemInfo("Executed on Environment: ", getConfig().environment());
            extentReports.setSystemInfo("Executed on Browser: ", getConfig().browserName());
            extentReports.setSystemInfo("Executed on OS: ", System.getProperty("os.name"));
            extentReports.setSystemInfo("OS Version", System.getProperty("os.version"));
            extentReports.setSystemInfo("Executed on URL: ", getConfig().remoteURL());
            extentReports.setSystemInfo(
                    "Test Group",
                    StringUtils.capitalize(
                            Objects.toString(System.getProperty("groups"), "regression")));

            extentReports.setSystemInfo("Executed by User: ", System.getProperty("user.name"));
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
    }

    public static synchronized void createTest(String testCaseName) {
        ExtentTest extenttest = extentReports.createTest(getBrowserIcon() + " : " + testCaseName);
        ExtentReportDriver.getInstance().setExtent(extenttest);
    }

    public static void addScreenShot(String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.BASE64);
        ExtentReportDriver.getInstance().getExtent().log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    public static void addScreenShot(Status status, String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.BASE64);
        //Base64 from Screenshot of Selenium
        ExtentReportDriver.getInstance().getExtent().log(status, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

}
