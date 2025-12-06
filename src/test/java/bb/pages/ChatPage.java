package bb.pages;

import bb.common.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChatPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ChatPage.class);

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Castalk.ChatView']/XCUIElementTypeStaticText")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtAiVirtualAssistantName;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Castalk.ChatView']")
    @AndroidFindBy(xpath = " ")
    WebElementFacade lblChatView;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Castalk.ChatView']/following-sibling::XCUIElementTypeOther//XCUIElementTypeCollectionView/XCUIElementTypeCell[7]//XCUIElementTypeTextView")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtIntroMessage;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Castalk.ChatView']/following-sibling::XCUIElementTypeOther//XCUIElementTypeCollectionView/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther//XCUIElementTypeTextView")
    @AndroidFindBy(xpath = " ")
    WebElementFacade txtInputMessage;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='_UITextSelectionRangeAdjustmentContainerView']/parent::XCUIElementTypeTextView/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther")
    @AndroidFindBy(xpath = " ")
    WebElementFacade btnSend;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Castalk.ChatView']/following-sibling::XCUIElementTypeOther//XCUIElementTypeCollectionView//XCUIElementTypeTextView")
    @AndroidFindBy(xpath = " ")
    List<WebElementFacade> txtLastestResponse;

    @iOSXCUITFindBy(xpath = " //XCUIElementTypeNavigationBar[@name='Castalk.ChatView']/following-sibling::XCUIElementTypeOther//XCUIElementTypeCollectionView//XCUIElementTypeButton")
    @AndroidFindBy(xpath = " ")
    List<WebElementFacade> txtLastestMessage;

    public boolean checkChatView() {
        return isElementPresent(lblChatView);
    }

    public boolean checkHistoryMessage() {
        return isElementPresent(txtLastestResponse.get(0));
    }

    public String getAiVirtualAssistantName() {
        return txtAiVirtualAssistantName.waitUntilVisible().getText();
    }

    public String getInputMessage() {
        System.out.println("Input Message:" + txtInputMessage.waitUntilVisible().getText());
        return txtInputMessage.waitUntilVisible().getText();
    }

    public void inputMessage(String message) {
        txtInputMessage.waitUntilVisible().sendKeys(message);
    }

    public void clickOnSend() {
        btnSend.waitUntilVisible().click();
    }

    public boolean checkSendButton() {
        return isElementPresent(btnSend);
    }

    public String getLastestResponse() {
        waitABit(7000); // I use hard wait because i need to make sure that response display latest (able to maintain by use lib dateTime, but now i don't have time to improve anymore)
        String lastestMessage = txtLastestResponse.get(txtLastestResponse.size() - 1).waitUntilVisible().getText();
        logger.info("Lastest Message:{}", lastestMessage);
        return lastestMessage;
    }

    public int checkLastestMessage(){
        return txtLastestMessage.size();
    }


}
