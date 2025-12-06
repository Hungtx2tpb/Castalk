package bb.steps;

import bb.pages.HomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.UIInteractionSteps;

public class HomeSteps extends UIInteractionSteps {

    HomePage homePage;

    @Step
    public void clickOnBtnMore(){
        homePage.clickOnMore();
    }

    @Step
    public void clickOnBtnHome(){
        homePage.clickOnHome();
    }

    @Step
    public void clickOnClosePopup(){
        homePage.clickOnClosePopup();
    }

    @Step
    public void clickOnChat(){
        homePage.clickOnChat();
    }

    @Step
    public void getAiVirtualAssistantName(){
        Serenity.setSessionVariable("AI Name").to(homePage.getAiName());
    }
}
