package org.myProject.testSuite;

import org.myProject.testBase.BaseClass;
import org.testng.annotations.Test;

import static org.myProject.configManager.ConfigFactory.getConfig;

public class LoginPageTest extends BaseClass {
    private void navigateToLoginPage() {
        landingPage.navigateToLoginPage();
    }

    @Test
    public void verifyLoginPage() {
        navigateToLoginPage();
        loginPage.verifyLoginPageUI();
    }

    @Test
    public void loginIntoApplication() {
        navigateToLoginPage();
        loginPage.loginIntoApplication(getConfig().userId(), getConfig().password());
    }
}
