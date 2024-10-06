package org.myProject.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static org.myProject.configManager.ConfigFactory.getConfig;

public class Retry implements IRetryAnalyzer {
    private static final Logger LOG    = LogManager.getLogger ("Retry.class");
    private static final int    maxTry = getConfig ().retryCounter();
    private              int    count  = 0;
    @Override
    public boolean retry (final ITestResult iTestResult) {
        if (!iTestResult.isSuccess ()) {
            if (this.count < maxTry) {
                LOG.info ("Retrying test " + iTestResult.getName () + " with status " + getResultStatusName (
                        iTestResult.getStatus ()) + " for the " + (this.count + 1) + " time(s).");
                this.count++;
                return true;
            }
        }
        return false;
    }
    public String getResultStatusName (final int status) {
        String resultName = null;
        if (status == 1) {
            resultName = "SUCCESS";
        }
        if (status == 2) {
            resultName = "FAILURE";
        }
        if (status == 3) {
            resultName = "SKIP";
        }
        return resultName;
    }
}
