package bb.pages;

import bb.common.BasePage;
import bb.utils.LoggerUtil;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class HomePage extends BasePage {
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name , 'Conversation upgrade')]")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtConversationUpgrade;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='OK']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnOK;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='More']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnMore;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='Next'])[2]")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnNext;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeScrollView/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeButton")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnClose;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='Home']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnHome;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name, 'Years Old')]/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther//XCUIElementTypeOther[2]")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnChat;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name, 'Years Old')]/parent::XCUIElementTypeOther/preceding-sibling::XCUIElementTypeStaticText")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtAiName;


    public void clickOnClosePopup() {
        if (isElementPresent(txtConversationUpgrade)) {
            waitAndClickElement(btnOK);
            return;
        }
        if (isElementPresent(btnNext)) {
            waitAndClickElement(btnClose);
            return;
        }
        LoggerUtil.logInfo("Popup is not displayed");
    }

    public void clickOnMore() {
        waitAndClickElement(btnMore);
    }

    public void clickOnHome() {
        btnHome.waitUntilVisible().click();
    }

    public void clickOnChat() {
        btnChat.waitUntilVisible().click();
    }

    public String getAiName(){
        return txtAiName.waitUntilVisible().getText();
    }


}
