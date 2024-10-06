package org.myProject.driver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.myProject.configManager.ConfigFactory.getConfig;
import static org.myProject.frameworkComstants.BrowserConstants.*;
import static org.myProject.frameworkComstants.Constants.DOWNLOAD_DIRECTORY;
import static org.myProject.frameworkComstants.Constants.WORKING_DIRECTORY;


public class SeleniumDriver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static SeleniumDriver instance = null;

    private SeleniumDriver() {
        // Prevent instantiation
    }

    public static SeleniumDriver getInstance() {
        if (instance == null) {
            synchronized (SeleniumDriver.class) {
                if (instance == null) {
                    instance = new SeleniumDriver();
                }
            }
        }
        return instance;
    }


    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Call createDriver first.");
        }
        return driver;
    }

    public static void closeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (org.openqa.selenium.NoSuchSessionException e) {
                System.err.println("Session already closed: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    public static WebDriver createDriver(String browser, String port) {
        String runMode = getConfig().runMode().toLowerCase();
        WebDriver driver;

        switch (runMode) {
            case "local":
                driver = getLocalDriver(browser);
                break;
            case "remote":
                driver = getRemoteDriver(browser, port);
                break;
            default:
                throw new IllegalArgumentException("Invalid run mode: " + runMode);
        }

        if (driver == null) {
            throw new IllegalStateException("Failed to create WebDriver instance for browser: " + browser);
        }
        setDriver(driver);
        return driver;
    }

    private static WebDriver getLocalDriver(String browser) {
        if (browser == null) {
            throw new IllegalArgumentException("Browser cannot be null");
        }

        String downloadFilepath = WORKING_DIRECTORY + "/target/download";
        WebDriver driver = null;
        switch (browser.toUpperCase()) {
            case "CHROME":
                driver = createChromeDriver(downloadFilepath);
                break;
            case "FIREFOX":
                driver = new FirefoxDriver();
                break;
            case "EDGE":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        return driver;
    }

    private static WebDriver createChromeDriver(String downloadFilepath) {
        ChromeOptions options = new ChromeOptions();
        String osName = System.getProperty("os.name");
        System.out.println("Operating System Name: " + osName);
        if (osName.equalsIgnoreCase("Linux")) {
            System.setProperty("webdriver.chrome.driver", WORKING_DIRECTORY + "/src/test/resources/drivers/chromedriver");
        }

        options.addArguments(START_MAXIMIZED, DISABLE_NOTIFICATIONS, REMOTE_ALLOW_ORIGINS, DISABLE_INFOBARS,ENABLE_LOGGING);

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("download.default_directory",DOWNLOAD_DIRECTORY);
        prefs.put("profile.default_content_settings.popups",0);


        options.setExperimentalOption("prefs", prefs);

        if (getConfig().isHeadless()) {
            options.addArguments(GENERIC_HEADLESS, SCREEN_SIZE, DISABLE_INFOBARS,DISABLE_NOTIFICATIONS, REMOTE_ALLOW_ORIGINS);
            System.out.println("Running Headless");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver getRemoteDriver(String browser, String port) {
        if (browser == null) {
            throw new IllegalArgumentException("Browser cannot be null");
        }
        WebDriver driver = null;
        URL remoteUrl;
        try {
            remoteUrl = new URL(getConfig().remoteURL() + ":" + port);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid remote URL: " + getConfig().remoteURL() + ":" + port, e);
        }
        switch (browser.toUpperCase()) {
            case "CHROME":
                driver = new RemoteWebDriver(remoteUrl, createRemoteChromeOptions());
                break;
            case "FIREFOX":
                driver = new RemoteWebDriver(remoteUrl, createRemoteFirefoxOptions());
                break;
            default:
                throw new IllegalArgumentException("Unsupported remote browser: " + browser);
        }
        return driver;
    }

    private static ChromeOptions createRemoteChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setEnableDownloads(false);
        if (getConfig().isHeadless()) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    private static FirefoxOptions createRemoteFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (getConfig().isHeadless()) {
            options.addArguments("--headless=new");
        }
        return options;
    }
}