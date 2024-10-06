package org.myProject.reportManager;

import com.aventstack.extentreports.*;

public class ExtentReportDriver {
    private static ExtentReportDriver instance = null;
    ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    private ExtentReportDriver() {

    }

    public static ExtentReportDriver getInstance() {
        if (instance == null) {
            instance = new ExtentReportDriver();
        }
        return instance;
    }

    public synchronized ExtentTest getExtent() {
        ExtentTest extentTest = extentTestThreadLocal.get();
        if (extentTest == null) {
            throw new NullPointerException("ExtentTest is not set for the current thread.");
        }
        return extentTest;
    }

    public synchronized void setExtent(ExtentTest extentTest) {
        if (extentTest == null) {
            throw new IllegalArgumentException("ExtentTest cannot be null.");
        }
        extentTestThreadLocal.set(extentTest);
    }

    public void removeExtent() {
        if (extentTestThreadLocal.get() == null) {
            throw new IllegalStateException("ExtentTest is already removed or not set for the current thread.");
        }
        extentTestThreadLocal.remove();
    }
}
