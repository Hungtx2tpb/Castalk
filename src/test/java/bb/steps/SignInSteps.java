package bb.steps;

import bb.pages.HomePage;
import bb.pages.SignInPage;
import bb.pages.UserProfilePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class SignInSteps extends UIInteractionSteps {
    private static final Logger logger = LoggerFactory.getLogger(SignInSteps.class);

    SignInPage SignInPage;
    UserProfilePage userProfilePage;
    HomePage homePage;

    @Step
    public void signInWithEmail(String email, String password) {
        SignInPage.clickOnSignInWithEmail();
        SignInPage.inputEmail(email);
        SignInPage.inputPassword(password);
        SignInPage.clickDoneOnKeyboard();
        SignInPage.clickOnSignIn();
    }

    @Step
    public void verifyErrorInValidPassword(String expectedErrorInvalidPassword) {
        assertThat(SignInPage.getTextInValidPassword()).isEqualTo(expectedErrorInvalidPassword);
    }

    @Step
    public void verifyErrorInValidEmail(String expectedErrorInvalidEmail) {
        assertThat(SignInPage.getTextInValidEmail()).isEqualTo(expectedErrorInvalidEmail);
    }

    @Step
    public void verifySignInButtonIsDisable() {
        assertThat(SignInPage.getAttributeSignInButton()).isEqualTo("false");
    }

    @Step
    public void clickOnIconShowPassword() {
        SignInPage.clickOnIconShowPassword();
    }

    @Step
    public void verifyVisiblePassword(String expectedPassword) {
        assertThat(SignInPage.getVisiblePassword()).isEqualTo(expectedPassword);
    }

    @Step
    public void signInIfGuest(String email, String password) {
        homePage.clickOnMore();
        if (userProfilePage.checkGuest()) {
            userProfilePage.clickOnSignIn();
            signInWithEmail(email, password);
        } else {
            homePage.clickOnHome();
            logger.info("User is not Guest");
        }
    }

}
