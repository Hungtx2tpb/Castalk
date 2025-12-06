package bb.pages;

import bb.common.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class SignInPage extends BasePage {


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Sign in with email']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtSignInWithEmail;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@value='Enter Your Email']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtEmail;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[@value='Enter Your Password']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtPassword;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Sign In']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtSignIn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name='icnHidePassword']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnIconShowPassword;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Please enter a valid password']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtInValidPassword;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Incorrect email address format. Please try again.']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtInValidEmail;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Enter Your Password']/following-sibling::*")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtVisiblePassword;


    public void clickOnSignInWithEmail() {
        waitAndClickElement(txtSignInWithEmail);
    }

    public void inputEmail(String email) {
        waitAndSendKeysElement(txtEmail, email);
    }

    public void inputPassword(String password) {
        waitAndSendKeysElement(txtPassword, password);
    }

    public void clickOnSignIn(){
        txtSignIn.waitUntilVisible().click();
    }

    public void clickOnIconShowPassword(){
        btnIconShowPassword.waitUntilVisible().click();
        waitABit(500);
    }

    public String getTextInValidEmail(){
        return waitAndGetTextElement(txtInValidEmail);
    }

    public String getTextInValidPassword(){
        return waitAndGetTextElement(txtInValidPassword);
    }

    public String getAttributeSignInButton(){
        return txtSignIn.waitUntilVisible().getElement().getAttribute("enabled");
    }

    public String getVisiblePassword(){
        return waitAndGetTextElement(txtVisiblePassword);
    }


}
