package org.myProject.listeners;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.myProject.emailManager.EmailSendUtils;
import org.myProject.reportManager.ExtentReportDriver;
import org.testng.*;

import java.util.concurrent.atomic.AtomicInteger;


public class TestListener implements ITestListener, ISuiteListener {
    private static final Logger logger = (Logger) LogManager.getLogger(TestListener.class.getSimpleName());

    private static final AtomicInteger count_totalTCs = new AtomicInteger(0);
    private static final AtomicInteger count_passedTCs = new AtomicInteger(0);
    private static final AtomicInteger count_skippedTCs = new AtomicInteger(0);
    private static final AtomicInteger count_failedTCs = new AtomicInteger(0);


    @Override
    public void onStart(ISuite suite) {
        logger.info("** Starting Automation Suite **\n" +
                        "*****************************************************************************\n" +
                        "********************[INSTALLING CONFIGURATION DATA]************************** \n" +
                        "************[Starting Suite: {}] ********************************\n" +
                        "*****************************************************************************\n",
                suite.getName().toUpperCase());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("*****************************************************************************");
        logger.info("********************[TEST COMPLETE]******************************************");
        logger.info("***********[Suite Completed: {}]*********************************************", suite.getName().toUpperCase());
        logger.info("*****************************************************************************");

        EmailSendUtils.sendEmail(count_totalTCs.get(), count_passedTCs.get(), count_failedTCs.get(), count_skippedTCs.get());
    }

    @Override
    public void onTestStart(ITestResult result) {
        count_totalTCs.incrementAndGet();
        logger.info("Test case: {} is starting...", getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        count_passedTCs.incrementAndGet();
        logger.info("{} : Test Passed", getTestName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        count_failedTCs.incrementAndGet();
        Throwable t = result.getThrowable();
        String cause = (t != null) ? t.getMessage() : "";
        logger.error("{} : Test failed. Cause: {}", getTestName(result), cause, t);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        count_skippedTCs.incrementAndGet();
        logger.warn("{} : Test Skipped. Cause: {}", getTestName(result), result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        ExtentReportDriver.getInstance().getExtent()
                .log(Status.FAIL, iTestResult.getName() + " Test failed but it is within the defined success ratio " + iTestResult.getMethod().getSuccessPercentage());
        ExtentReportDriver.getInstance().removeExtent();
        logger.warn(String.format("%s : Test failed with%% ", getTestName(iTestResult)), iTestResult.getThrowable());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result); // Handle timeout as a failure
    }

    @Override
    public void onStart(ITestContext context) {
        // No implementation needed for now
    }

    @Override
    public void onFinish(ITestContext context) {
        // No implementation needed for now
    }

    private String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }
}
