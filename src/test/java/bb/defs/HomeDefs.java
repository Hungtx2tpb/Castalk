package bb.defs;

import bb.steps.HomeSteps;
import net.serenitybdd.annotations.Steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class HomeDefs {

    @Steps
    HomeSteps homeSteps;


    @Given("User is on Home Page")
    public void userIsOnHomePage() {
        homeSteps.clickOnClosePopup();
    }

    @When("User click on button More")
    public void userClickOnBtnMore() {
        homeSteps.clickOnBtnMore();
    }

    @When("User click on button Home")
    public void userClickOnBtnHome() {
        homeSteps.clickOnBtnHome();
    }

    @When("User click on button Chat")
    public void userClickOnBtnChat() {
        homeSteps.clickOnChat();
    }

    @When("Get AI virtual assistant name")
    public void getAiVirtualAssistantName() {
        homeSteps.getAiVirtualAssistantName();
    }


}
