package bb.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppHooks extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(AppHooks.class);

//    @Before(order = 0)
//    public void closeAppBeforeScenario() {
//        try {
//            if (!isSessionActive()) {
//                logger.info("[Hook] session not active; skip @Before");
//                return;
//            }
//
//            AppiumDriver appium = getAppiumDriver();
//            String platform = getPlatformName().toLowerCase();
//            String appId = getCurrentAppId();
//
//            System.out.println("platform: " + platform + " appId: " + appId + "");
//
//            if (appId == null || appId.isBlank()) {
//                logger.info("[Hook] no appId; skip @Before");
//                return;
//            }
//
//            if (platform.contains("ios")) {
//                ((InteractsWithApps) appium).terminateApp(appId);
//                logger.info("[Hook] terminated before scenario: {}", appId);
//
//                ((InteractsWithApps) appium).activateApp(appId);
//                logger.info("[Hook] re-activated before scenario: {}", appId);
//            } else {
//                logger.info("[Hook] platform {} - no special @Before action", platform);
//            }
//
//        } catch (Exception e) {
//            logger.warn("[Hook] Failed in @Before: {}", e.getMessage());
//        }
//    }

    @Before(order = 0)
    public void closeAppBeforeScenario() {
        try {
            AppiumDriver appium = getAppiumDriver();

            if (appium == null) {
                logger.info("[Hook] driver null; skip");
                return;
            }
            if (appium.getSessionId() == null) {
                logger.info("[Hook] session closed; skip");
                return;
            }
            String platform = String.valueOf(appium.getCapabilities().getCapability("platformName")).toLowerCase();
            String appId = platform.contains("android")
                    ? String.valueOf(appium.getCapabilities().getCapability("appPackage"))
                    : String.valueOf(appium.getCapabilities().getCapability("bundleId"));
            if (appId == null || appId.isBlank()) {
                logger.info("[Hook] no appId; skip");
                return;
            }
            if(platform.equalsIgnoreCase("iOS")){
                ((InteractsWithApps) appium).terminateApp(appId);
                logger.info("[Hook] terminated before scenario: {}", appId);
                ((InteractsWithApps) appium).activateApp(appId);
                logger.info("[Hook] re-activated before scenario: {}", appId);
            }
        } catch (Exception e) {
            logger.warn("[Hook] Failed in @Before: {}", e.getMessage());
        }
    }

    @After(order = 0)
    public void terminateAfterScenario() {
        try {
            if (!isSessionActive()) {
                logger.info("[Hook] session not active; skip @After");
                return;
            }

            AppiumDriver appium = getAppiumDriver();
            String appId = getCurrentAppId();

            if (appId != null && !appId.isBlank()) {
                ((InteractsWithApps) appium).terminateApp(appId);
                logger.info("[Hook] App terminated after scenario: {}", appId);
            } else {
                logger.info("[Hook] no appId; skip @After");
            }

        } catch (Exception e) {
            logger.warn("[Hook] Could not terminate app in @After: {}", e.getMessage());
        }
    }
}
