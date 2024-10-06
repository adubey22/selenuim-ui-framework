package org.myProject.testBase;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.epam.healenium.SelfHealingDriver;
import org.apache.log4j.PropertyConfigurator;
import org.myProject.driver.SeleniumDriver;
import org.myProject.pageObject.LandingPage;
import org.myProject.pageObject.LoginPage;
import org.myProject.reportManager.ExtentLogger;
import org.myProject.reportManager.ExtentReportDriver;
import org.myProject.reportManager.ExtentReportNG;
import org.myProject.userActions.CustomUserActions;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;

import static org.myProject.configManager.ConfigFactory.getConfig;
import static org.myProject.frameworkComstants.Constants.*;
import static org.myProject.frameworkComstants.ReportConstants.*;

public class BaseClass {
    private WebDriver driver;
    protected LoginPage loginPage;
    protected LandingPage landingPage;

    static {
        System.out.println("Environment = " + getConfig().environment());
        System.out.println("OS = " + System.getProperty("os.name"));
        System.out.println("Browser = " + getConfig().browserName());

    }

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        PropertyConfigurator.configure(WORKING_DIRECTORY + "/src/main/resources/log4j2.xml"); // Load config
        try {
            ExtentReportNG.extentReportSetup();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentReportNG.flushReports();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "port"})
    public void launchBrowser(ITestResult iTestResult, @Optional("chrome") String browser, @Optional("4444") String port) throws IOException, AWTException {
        ExtentReportNG.createTest(iTestResult.getMethod().getMethodName());
        ExtentLogger.info("Browser to run : " + browser);
        WebDriver delegate = SeleniumDriver.createDriver(browser, port);
        if (getConfig().selfHealing().equalsIgnoreCase("YES")) {
            SelfHealingDriver sDriver = SelfHealingDriver.create((WebDriver) delegate);
            SeleniumDriver.getInstance();
            SeleniumDriver.setDriver(sDriver);
        } else {
            SeleniumDriver.getInstance();
            SeleniumDriver.setDriver(delegate);
        }
        SeleniumDriver.getInstance();
        driver = SeleniumDriver.getDriver();
        CustomUserActions.getURL(getConfig().url());
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(LONG_WAIT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
        driver.manage().window().maximize();
        initializePages(driver);
        logTestStart(iTestResult);
    }

    @AfterMethod(alwaysRun = true)
    public void quitBrowser(ITestResult iTestResult) {
        try {
            handleTestResult(iTestResult);
        } finally {
            SeleniumDriver.closeDriver();
        }
    }

    private void logTestStart(ITestResult iTestResult) {
        ExtentReportDriver.getInstance().getExtent().log(Status.INFO, "Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + driver.getClass().getSimpleName());
        ExtentReportDriver.getInstance().getExtent().log(Status.INFO, "Test Case: [" + iTestResult.getMethod().getMethodName() + "] started!");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(LONG_WAIT));
        driver.manage().window().maximize();
    }

    private void initializePages(WebDriver driver) {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
    }

    private void handleTestResult(ITestResult iTestResult) {
        switch (iTestResult.getStatus()) {
            case ITestResult.FAILURE:
                logFailure(iTestResult);
                break;
            case ITestResult.SUCCESS:
                logSuccess(iTestResult);
                break;
            case ITestResult.SKIP:
                logSkip(iTestResult);
                break;
            default:
                break;
        }
    }

    private void logFailure(ITestResult iTestResult) {
        ExtentReportDriver.getInstance().getExtent().log(Status.FAIL, "Test case: [" + iTestResult.getMethod().getMethodName() + "] failed with failure percentage: " + iTestResult.getMethod().getSuccessPercentage());
        ExtentReportDriver.getInstance().getExtent().log(Status.FAIL, iTestResult.getThrowable());
        try {
            ExtentReportNG.addScreenShot(Status.FAIL, iTestResult.getMethod().getMethodName());
            String logText = "<b>" + iTestResult.getMethod().getMethodName() + " failed.</b>" + "  " + ICON_SMILEY_FAIL;
            Markup markupMessage = MarkupHelper.createLabel(logText, ExtentColor.RED);
            ExtentLogger.fail(markupMessage);
            ExtentReportDriver.getInstance().removeExtent();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void logSuccess(ITestResult iTestResult) {
        ExtentReportDriver.getInstance().getExtent().log(Status.PASS, "Test case: [" + iTestResult.getMethod().getMethodName() + "] passed with success percentage : " + iTestResult.getMethod().getSuccessPercentage());
        String logText = "<b>" + iTestResult.getMethod().getMethodName() + " passed.</b>" + "  " + ICON_SMILEY_PASS;
        Markup markupMessage = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        ExtentLogger.pass(markupMessage);
        ExtentReportDriver.getInstance().removeExtent();
    }

    private void logSkip(ITestResult iTestResult) {
        ExtentReportDriver.getInstance().getExtent().log(Status.SKIP, "Test case: " + iTestResult.getName() + " skipped with success percentage: " + iTestResult.getMethod().getSuccessPercentage());
        ExtentReportDriver.getInstance().getExtent().log(Status.SKIP, iTestResult.getThrowable());
        ExtentReportNG.addScreenShot(Status.SKIP, iTestResult.getMethod().getMethodName());
        String logText = "<b>" + iTestResult.getMethod().getMethodName() + " skipped.</b>" + "  " + ICON_SMILEY_SKIP;
        Markup markupMessage = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        ExtentLogger.skip(markupMessage);
        ExtentReportDriver.getInstance().removeExtent();
    }
}