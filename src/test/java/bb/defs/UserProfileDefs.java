package bb.defs;

import bb.steps.UserProfileSteps;
import net.serenitybdd.annotations.Steps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class UserProfileDefs {

    @Steps
    UserProfileSteps userProfileSteps;

    @Then("Verify User Profile page should be displayed")
    public void verifyUserProfileIsDisplayed(){
        userProfileSteps.verifyUserProfileIsDisplayed();
    }

    @When("User click on Sign In")
    public void clickOnSignIn(){
        userProfileSteps.performSignIn();
    }

    @Then("Verify user login successfully with email {string}")
    public void verifyUserProfileIsDisplayedAfterSignIn(String expectedEmail){
        userProfileSteps.verifyUserProfileIsDisplayedAfterSignIn(expectedEmail);
    }

    @When("User click on My Account")
    public void clickOnMyAccount(){
        userProfileSteps.clickOnMyAccount();
    }

}
