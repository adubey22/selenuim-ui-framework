package org.myProject.frameworkComstants;

public class Constants {
    public static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    public final static String REPORT_DIRECTORY = WORKING_DIRECTORY + "/ExtentReports/AutomationResult.html";
    public final static String PROJECT_NAME = "EMR UI Automation";
    public final static String DOWNLOAD_DIRECTORY = WORKING_DIRECTORY + "/target/Downloads";

    public static final int IMPLICIT_WAIT_TIME=1;//in Second
    public static final int EXPLICIT_WAIT_TIME=10; //in Second
    public static final int SHORT_WAIT=10;//in Second
    public static final int MEDIUM_WAIT=30;//in Second
    public static final int LONG_WAIT=120;//in Second
    public static final String REPORT_SUBJECT="EMR UI Automation REPORT";
    public static final String REPORT_TITLE="EMR UI Automation REPORT";

}
