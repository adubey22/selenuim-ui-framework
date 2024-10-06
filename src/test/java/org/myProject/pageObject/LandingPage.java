package org.myProject.pageObject;

import org.myProject.userActions.CustomUserActions;
import org.myProject.userActions.CustomVerifications;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

import static org.myProject.configManager.ConfigFactory.getConfig;
import static org.myProject.frameworkComstants.PageURLs.LOGIN_PAGE_URL;
import static org.myProject.userActions.CustomUserActions.clickCustom;
import static org.myProject.userActions.CustomUserActions.setValueByTypingCharacterByCharacter;
import static org.myProject.userActions.CustomVerifications.isVisible;

// page_url = http://www.automationpractice.pl/index.php
public class LandingPage {
    @FindBy(css = "a[title$='Shop']")
    public WebElement logoMyShop;

    @FindBy(css = "#search_block_top")
    public WebElement textBoxSearchTop;

    @FindBy(css = "button[name='submit_search']")
    public WebElement buttonSubmitSearch;

    @FindBy(css = "a[title$='cart']")
    public WebElement linkViewMyShoppingCart;

    @FindBy(css = "b")
    public WebElement bCart;

    @FindBy(css = "#columns")
    public WebElement divColumns;

    @FindBy(css = "a[class='login']")
    public WebElement linkLogInYourCustomerAccount;

    @FindBy(css = "a[title^='Contact']")
    public WebElement linkContact;

    public LandingPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void verifyLandingPageUI() {
        isVisible(logoMyShop, "logoMyShop");
        isVisible(linkLogInYourCustomerAccount, "linkLogInYourCustomerAccount");
        isVisible(linkContact, "linkContact");
    }

    public void navigateToLoginPage() {
        clickCustom(linkLogInYourCustomerAccount, "linkLogInYourCustomerAccount");
        CustomUserActions.navigateTo(getConfig()+LOGIN_PAGE_URL);

    }
}