package org.myProject.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

import static org.myProject.userActions.CustomUserActions.*;
import static org.myProject.userActions.CustomVerifications.isVisible;

// page_url = http://www.automationpractice.pl/index.php?controller=authentication&back=my-account
public class LoginPage {
    @FindBy(css = "div[class^='breadcrumb']")
    public WebElement labelAuthentication;

    @FindBy(css = "a[class='home']")
    public WebElement linkReturnHome;

    @FindBy(css = "#create-account_form")
    public WebElement formCreateAccount;

    @FindBy(css = "#create-account_form h3")
    public WebElement labelCreateAccount;

    @FindBy(css = "#login_form")
    public WebElement formLogin;

    @FindBy(css = "#login_form h3")
    public WebElement labelAlreadyRegistered;

    @FindBy(css = "#email")
    public WebElement inputTextBoxEmail;

    @FindBy(css = "#passwd")
    public WebElement inputTextBoxPassword;

    @FindBy(css = "a[title*='forgotten']")
    public WebElement linkRecoverYourForgottenPassword;

    @FindBy(css = "#SubmitLogin")
    public WebElement buttonSubmitLogin;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void verifyLoginPageUI() {
        isVisible(labelAuthentication,"Authentication");
        isVisible(formCreateAccount,"formCreateAccount");
        isVisible(labelCreateAccount,"labelCreateAccount");
        isVisible(formLogin,"formLogin");
        isVisible(labelAlreadyRegistered,"labelAlreadyRegistered");
        isVisible(inputTextBoxEmail,"inputTextBoxEmail");
        isVisible(inputTextBoxPassword,"inputTextBoxPassword");
        isVisible(linkRecoverYourForgottenPassword,"linkRecoverYourForgottenPassword");
        isVisible(buttonSubmitLogin,"buttonSubmitLogin");
    }
    public void loginIntoApplication(String email, String password) {
        setValueByTypingCharacterByCharacter(inputTextBoxEmail, email,"inputTextBoxEmail");
        setValueByTypingCharacterByCharacter(inputTextBoxPassword, password,"inputTextBoxPassword");
        clickCustom(buttonSubmitLogin, "buttonSubmitLogin");
    }
}