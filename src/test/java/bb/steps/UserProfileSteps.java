package bb.steps;

//import bb.common.AppHooks;
import bb.pages.UserProfilePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class UserProfileSteps extends UIInteractionSteps {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileSteps.class);

    UserProfilePage userProfilePage;

    @Step
    public void verifyUserProfileIsDisplayed(){
        assertThat(userProfilePage.checkUserProfile())
                .as("User Profile Title should be visible")
                .isTrue();
    }

    @Step
    public void performSignIn(){
        if(!userProfilePage.checkGuest()){
            userProfilePage.clickOnSignOut();
            userProfilePage.clickOnConfirmSignOut();
        }else {
            userProfilePage.clickOnSignIn();
            logger.info("User is Guest");
        }
    }

    @Step
    public void verifyUserProfileIsDisplayedAfterSignIn(String expectedEmail){
        assertThat(userProfilePage.getEmailUserProfile()).isEqualTo(expectedEmail);
    }

    @Step
    public void clickOnMyAccount(){
        userProfilePage.clickOnMyAccount();
    }


}
