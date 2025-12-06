package bb.steps;

import bb.pages.ChatPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.UIInteractionSteps;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatSteps extends UIInteractionSteps {

    ChatPage chatPage;

    @Step
    public void verifyChatView(){
        assertThat(chatPage.checkChatView())
                .as("Chat screen should be visible")
                .isTrue();
    }

    @Step
    public void verifyAiVirtualAssistantName(){
        assertThat(chatPage.getAiVirtualAssistantName()).isEqualTo(Serenity.sessionVariableCalled("AI Name"));
    }



    @Step
    public void verifyHistoryMessageIsDisplayed(){
        assertThat(chatPage.checkHistoryMessage())
                .as("History message should be visible")
                .isTrue();
    }

    @Step
    public void verifyInputMessageIsEmpty(){
        assertThat(chatPage.getInputMessage().isEmpty())
                .as("Input message should be empty")
                .isTrue();
    }

    @Step
    public void verifySendButtonIsInVisible(){
        assertThat(chatPage.checkSendButton())
                .as("Send button should be InVisible")
                .isFalse();
    }

    @Step
    public void verifyResponseMessageIsCorrect(List<String> containsGreeting){
        boolean foundGreeting = false;
        String response = chatPage.getLastestResponse().toLowerCase();

        assertThat(response)
                .isNotBlank()
                .hasSizeGreaterThan(5)
                .doesNotContainIgnoringCase("error")
                .doesNotContainIgnoringCase("something went wrong");

        for (String str : containsGreeting) {
            if (response.contains(str.toLowerCase())) {
                foundGreeting = true;
                break;
            }
        }
        assertThat(foundGreeting)
                .as("AI message should contain at least one greeting")
                .isTrue();
    }

    @Step
    public void inputMessage(String message){
        chatPage.inputMessage(message);
    }

    @Step
    public void clickOnSendButton(){
        chatPage.clickOnSend();
    }

    @Step
    public void checkLastestMessage(){
        Serenity.setSessionVariable("Quantity Message").to(chatPage.checkLastestMessage());
    }

    @Step
    public void verifyLastestMessageIsNotSend(){
        System.out.println("Lastest Message Size:" + chatPage.checkLastestMessage() + " Quantity Message:" + Serenity.sessionVariableCalled("Quantity Message"));
        assertThat(chatPage.checkLastestMessage()).isEqualTo(Serenity.sessionVariableCalled("Quantity Message"));
    }



}
