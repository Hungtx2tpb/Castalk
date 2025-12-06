package bb.defs;

import bb.steps.SignInSteps;
import net.serenitybdd.annotations.Steps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignInDefs {

    @Steps
    SignInSteps signInSteps;


    @When("User signs in with Email with email {string} and password {string}")
    public void signInWithEmail(String email, String password) {
        signInSteps.signInWithEmail(email, password);
    }

    @Then("Verify error message password {string} is displayed")
    public void verifyErrorMessagePasswordIsDisplayed(String expectedErrorMessage) {
        signInSteps.verifyErrorInValidPassword(expectedErrorMessage);
    }

    @Then("Verify error message email {string} is displayed")
    public void verifyErrorMessageEmailIsDisplayed(String expectedErrorMessage) {
        signInSteps.verifyErrorInValidEmail(expectedErrorMessage);
    }

    @Then("Verify Sign In button should be disable")
    public void verifySignInButtonIsDisable() {
        signInSteps.verifySignInButtonIsDisable();
    }

    @When("User click on icon show password")
    public void userClickOnIconShowPassword() {
        signInSteps.clickOnIconShowPassword();
    }

    @Then("Verify Password should be displayed {string}")
    public void verifyVisiblePassword(String expectedPassword) {
        signInSteps.verifyVisiblePassword(expectedPassword);
    }

    @When("user perform Sign In with email {string} and password {string} if Guest")
    public void userSignInIfGuest(String email, String password) {
        signInSteps.signInIfGuest(email, password);
    }
}
