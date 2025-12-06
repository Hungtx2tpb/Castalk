package bb.defs;

import bb.steps.ChatSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ChatDefs {
    private static final Logger logger = LoggerFactory.getLogger(ChatDefs.class);

    @Steps
    ChatSteps chatSteps;

    @Then("Verify Chat screen should be displayed")
    public void verifyChatViewIsDisplayed() {
        chatSteps.verifyChatView();
    }

    @Then("Verify AI virtual assistant name is displayed correct")
    public void verifyAiVirtualAssistantNameIsDisplayed() {
        chatSteps.verifyAiVirtualAssistantName();
    }

    @Then("Verify history Message should be displayed")
    public void verifyHistoryMessageIsDisplayed() {
        chatSteps.verifyHistoryMessageIsDisplayed();
    }

    @Then("Verify Input Message should be empty")
    public void verifyInputMessageIsEmpty() {
        chatSteps.verifyInputMessageIsEmpty();
    }

    @Then("Verify Send button should be InVisible")
    public void verifySendButtonIsInVisible() {
        chatSteps.verifySendButtonIsInVisible();
    }

    @Then("Verify response message by AI should be correct")
    public void verifyResponseMessageIsCorrect(DataTable dataTable) {
        List<String> expectedValues = dataTable.asMaps().stream()
                .map(row -> row.get("value"))
                .map(String::trim)
                .collect(Collectors.toList());
        logger.info("Expected Values:{}", expectedValues);
        chatSteps.verifyResponseMessageIsCorrect(expectedValues);
    }

    @When("User input message {string}")
    public void inputMessage(String message) {
        chatSteps.inputMessage(message);
    }

    @When("User click on Send button")
    public void clickOnSendButton() {
        chatSteps.clickOnSendButton();
    }

    @When("Check Message before send white space")
    public void checkMessageBeforeSendWhiteSpace(){
        chatSteps.checkLastestMessage();
    }

    @Then("Verify Message should not be send")
    public void verifyMessageShouldNotBeSend(){
        chatSteps.verifyLastestMessageIsNotSend();
    }

}
