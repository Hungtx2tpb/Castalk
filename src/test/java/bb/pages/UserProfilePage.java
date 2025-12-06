package bb.pages;

import bb.common.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class UserProfilePage extends BasePage {

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='User Profile']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtUserProfile;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Sign In']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtSignIn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Castalk Guest']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtGuest;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Sign Out']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtSignOut;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Sign Out']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnConfirmSignOut;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Your Email']/following-sibling::XCUIElementTypeTextField")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtEmailUserProfile;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name='icnCameraSmallFill'])[2]")
    @AndroidFindBy(xpath = " ")
    WebElementFacade iconCameraSmallFill;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='My Account']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtMyAccount;


    public void clickOnSignIn() {
        waitAndClickElement(txtSignIn);
    }

    public boolean checkUserProfile() {
        return isElementPresent(txtUserProfile);
    }

    public boolean checkGuest() {
        return isElementPresent(txtGuest);
    }

    public void clickOnSignOut() {
        scrollDownToElementVisible(1, txtSignOut);
        waitAndClickElement(txtSignOut);
    }

    public void clickOnConfirmSignOut() {
        waitAndClickElement(btnConfirmSignOut);
    }

    public String getEmailUserProfile() {
        return waitAndGetTextElement(txtEmailUserProfile);
    }

    public void clickOnMyAccount(){
        scrollUpToElementVisible(1, iconCameraSmallFill);
        waitAndClickElement(txtMyAccount);
    }

}
