package bb.common;

import bb.utils.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class BasePage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    private static final PointerInput FINGER = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    // ============= DRIVER HELPERS ======================================

    protected AppiumDriver getAppiumDriver() {
        WebDriver driver = getDriver();
        if (driver instanceof WebDriverFacade) {
            WebDriver proxied = ((WebDriverFacade) driver).getProxiedDriver();
            if (proxied instanceof AppiumDriver) {
                return (AppiumDriver) proxied;
            }
        }
        throw new IllegalStateException("Current driver is not an AppiumDriver");
    }

    protected AndroidDriver getAndroidDriver() {
        WebDriver driver = getDriver();
        if (driver instanceof WebDriverFacade) {
            WebDriver proxied = ((WebDriverFacade) driver).getProxiedDriver();
            if (proxied instanceof AndroidDriver) {
                return (AndroidDriver) proxied;
            }
        }
        throw new IllegalStateException("Current driver is not an AndroidDriver");
    }

    protected IOSDriver getIOSDriver() {
        WebDriver driver = getDriver();
        if (driver instanceof WebDriverFacade) {
            WebDriver proxied = ((WebDriverFacade) driver).getProxiedDriver();
            if (proxied instanceof IOSDriver) {
                return (IOSDriver) proxied;
            }
        }
        throw new IllegalStateException("Current driver is not an IOSDriver");
    }

    protected Actions actions() {
        return new Actions(getDriver());
    }

    // ============= WAIT HELPERS ========================================

    public void waitAMilliSeconds(int ms) {
        waitABit(ms);
    }

    protected WebDriverWait explicitWait(int timeoutInSeconds) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
    }

    protected FluentWait<WebDriver> fluentWait(int timeoutInSeconds) {
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public void waitUntilElementInvisibleByXpath(int timeoutInSeconds, String xpath) {
        fluentWait(timeoutInSeconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitUntilElementVisibleByXpath(int timeoutInSeconds, String xpath) {
        explicitWait(timeoutInSeconds)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitUntilElementInvisibleById(int timeoutInSeconds, String id) {
        explicitWait(timeoutInSeconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
    }

    public WebElementFacade waitUntilElementVisible(int timeoutInSeconds, WebElementFacade e) {
        fluentWait(timeoutInSeconds).until(ExpectedConditions.visibilityOf(e));
        return e;
    }

    public WebElementFacade waitUntilElementInvisible(int timeoutInSeconds, WebElementFacade e) {
        fluentWait(timeoutInSeconds).until(ExpectedConditions.invisibilityOf(e));
        return e;
    }

    public WebElementFacade waitElementEnable(int timeoutInSeconds, WebElementFacade e) {
        fluentWait(timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(e));
        return e;
    }

    public void waitUntilElementEnableByXpath(int timeoutInSeconds, String xpath) {
        explicitWait(timeoutInSeconds)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public boolean isElementPresent(WebElementFacade el) {
        try {
            return el.isVisible();
        } catch (Exception e) {
            logger.info("Element not present yet: {}", el, e);
            return false;
        }
    }

    // ============= ALERTS ==============================================

    public void waitForAlertAndAccept() {
        explicitWait(15).until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();
    }

    public String getAlertText() {
        explicitWait(15).until(ExpectedConditions.alertIsPresent());
        return getDriver().switchTo().alert().getText();
    }

    // ============= KEYBOARD / KEYS =====================================

    public void hideKeyboard() {
        try {
            if (Utilities.isCurrentPlatformAndroid()) {
                getAndroidDriver().hideKeyboard();
            } else {
                getIOSDriver().hideKeyboard();
            }
        } catch (Exception e) {
            logger.warn("Error while hiding keyboard", e);
        }
    }

    public void pressTab() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.TAB));
    }

    public void pressEnter() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public void pressBack() {
        getAppiumDriver().navigate().back();
    }

    public void enterValueToNumberKeyBoardIOS(String number) {
        for (char digit : number.toCharArray()) {
            getAppiumDriver().findElement(By.id(String.valueOf(digit))).click();
        }
    }

    /**
     * Lưu ý: tuỳ version java-client mà enum AndroidKey có DIGIT_0...DIGIT_9
     * hoặc kiểu khác. Nếu dòng dưới không compile, bạn có thể giữ lại cách nhập bằng click element ID.
     */
    public void enterPasscodeAndroid(String number) {
        for (char digit : number.toCharArray()) {
            AndroidKey key = AndroidKey.valueOf("DIGIT_" + digit); // chỉnh theo enum nếu cần
            getAndroidDriver().pressKey(new KeyEvent(key));
        }
    }

    public void enterPasscodeByIdAndroid(String number) {
        for (char digit : number.toCharArray()) {
            String id = String.format("authenticationJourney_passcodeKeyboard_%sButton", digit);
            $(By.id(id)).waitUntilClickable().click();
        }
    }

    public void sendKeysByKeyboard(String value) {
        actions().sendKeys(value).perform();
    }

    // ============= GESTURES (TAP / SCROLL / SWIPE) =====================

    private Dimension screenSize() {
        return getDriver().manage().window().getSize();
    }

    public Point getElementCenterPoint(WebElementFacade element) {
        Rectangle reg = element.getRect();
        return new Point(reg.getX() + reg.getWidth() / 2, reg.getY() + reg.getHeight() / 2);
    }

    private Sequence newSequence() {
        return new Sequence(FINGER, new Random().nextInt(1000));
    }

    public void scrollUpByAction() {
        Dimension size = screenSize();
        int height = size.getHeight();
        int width = size.getWidth();

        int startX = width / 2;
        int startY = (int) (height * 0.3);
        int endY   = (int) (height * 0.7);

        Sequence scroll = newSequence();
        scroll.addAction(FINGER.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));
        scroll.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(FINGER.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), startX, endY));
        scroll.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getAppiumDriver().perform(Collections.singletonList(scroll));
    }

    public void scrollUpToElementVisible(int maxScroll, WebElementFacade e) {
        int i = 0;
        while (!e.isCurrentlyVisible() && i < maxScroll) {
            scrollUpByAction();
            i++;
        }
    }


    public void scrollDownByAction() {
        Dimension size = screenSize();
        int height = size.getHeight();
        int width = size.getWidth();

        int startX = width / 2;
        int startY = (int) (height * 0.7);
        int endY   = (int) (height * 0.3);

        Sequence scroll = newSequence();
        scroll.addAction(FINGER.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));
        scroll.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(FINGER.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), startX, endY));
        scroll.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getAppiumDriver().perform(Collections.singletonList(scroll));
    }

    public void tapOnElementByAction(WebElementFacade e) {
        Point c = getElementCenterPoint(e);
        tapOnCoordinate(c.getX(), c.getY());
    }

    public void tapOnCoordinate(int x, int y) {
        Sequence tap = newSequence();
        tap.addAction(FINGER.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), x, y));
        tap.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getAppiumDriver().perform(Collections.singletonList(tap));
    }

    public void longPressOnCoordinate(int x, int y, int durationMs) {
        Sequence longPress = newSequence();
        longPress.addAction(FINGER.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), x, y));
        longPress.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(FINGER.createPointerMove(Duration.ofMillis(durationMs),
                PointerInput.Origin.viewport(), x, y));
        longPress.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getAppiumDriver().perform(Collections.singletonList(longPress));
    }

    public void holdOnElement(WebElementFacade e) {
        Point c = getElementCenterPoint(e);
        longPressOnCoordinate(c.getX(), c.getY(), 300);
    }

    public void tapOnPercentLocation(double xPercent, double yPercent) {
        Dimension size = screenSize();
        int x = (int) (size.getWidth() * xPercent / 100);
        int y = (int) (size.getHeight() * yPercent / 100);
        tapOnCoordinate(x, y);
    }

    public void swipeToLeft(int x, int y) {
        Sequence swipe = newSequence();
        swipe.addAction(FINGER.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), x, y));
        swipe.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(FINGER.createPointerMove(Duration.ofMillis(300),
                PointerInput.Origin.viewport(), 0, y));
        swipe.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getAppiumDriver().perform(Collections.singletonList(swipe));
    }

    public void swipeLeftOnElement(WebElementFacade e, int horizontally) {
        int x = e.getLocation().getX();
        int y = e.getLocation().getY();
        int width  = e.getSize().getWidth();
        int height = e.getSize().getHeight();

        int startX = x + width - 10;
        int startY = y + height / 2;
        int endX   = x + horizontally;

        Sequence swipe = newSequence();
        swipe.addAction(FINGER.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(FINGER.createPointerMove(Duration.ofMillis(300),
                PointerInput.Origin.viewport(), endX, startY));
        swipe.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getAppiumDriver().perform(Collections.singletonList(swipe));
    }

    public void scrollDownToElementVisible(int maxScroll, WebElementFacade e) {
        int i = 0;
        while (!e.isCurrentlyVisible() && i < maxScroll) {
            scrollDownByAction();
            i++;
        }
    }

    // ============= SIMPLE WRAPPERS ====================================

    public void waitAndClickElement(WebElementFacade e) {
        e.waitUntilClickable().click();
    }

    public String waitAndGetTextElement(WebElementFacade e) {
        return e.waitUntilVisible().getText();
    }

    public void waitAndSendKeysElement(WebElementFacade e, String value) {
        e.waitUntilVisible().sendKeys(value);
    }

    // ============= LIST / TEXT HELPERS ================================

    public List<String> getTextListElement(By by) {
        return findAll(by).stream()
                .map(WebElementFacade::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTextListElementFormatXpath(String xpathNeedFormat,
                                                      List<String> listStringFormat) {
        return listStringFormat.stream()
                .map(s -> $(String.format(xpathNeedFormat, s)).getText())
                .collect(Collectors.toList());
    }

    // ============= FORMAT / UTILITIES =================================

    public static String changeCurrencyFormatVND(String balance) {
        double value = Double.parseDouble(balance);
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(value);
    }

    public static String changeAccountNumberFormat(String s) {
        return s.replaceAll("(.{4})", "$1 ").trim();
    }

    public static String formatBlankSpace(String s) {
        return s.replaceAll("[\\s\\u00A0]+", "");
    }

    public String formatNumber(String s) {
        return s.replaceAll("[^0-9]+", "");
    }

    public String getLastMonth(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public String getCurrentMonth(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }

    // ================= APP / PLATFORM HELPERS ===================

    protected String getPlatformName() {
        try {
            Object value = getAppiumDriver().getCapabilities().getCapability("platformName");
            return value == null ? "" : String.valueOf(value);
        } catch (Exception e) {
            logger.warn("Cannot get platformName from capabilities", e);
            return "";
        }
    }

    protected String getCurrentAppId() {
        try {
            AppiumDriver appium = getAppiumDriver();
            String platform = getPlatformName().toLowerCase();

            if (platform.contains("android")) {
                Object pkg = appium.getCapabilities().getCapability("appPackage");
                return pkg == null ? "" : String.valueOf(pkg);
            } else if (platform.contains("ios")) {
                Object bid = appium.getCapabilities().getCapability("bundleId");
                return bid == null ? "" : String.valueOf(bid);
            } else {
                return "";
            }
        } catch (Exception e) {
            logger.warn("Cannot get current appId from capabilities", e);
            return "";
        }
    }

    protected void terminateApp(String appId) {
        if (appId == null || appId.isBlank()) {
            logger.info("[BasePage] terminateApp: appId is blank, skip");
            return;
        }
        try {
            AppiumDriver appium = getAppiumDriver();
            ((InteractsWithApps) appium).terminateApp(appId);
            logger.info("[BasePage] App terminated: {}", appId);
        } catch (Exception e) {
            logger.warn("[BasePage] Failed to terminate app {}: {}", appId, e.getMessage());
        }
    }

    protected void activateApp(String appId) {
        if (appId == null || appId.isBlank()) {
            logger.info("[BasePage] activateApp: appId is blank, skip");
            return;
        }
        try {
            AppiumDriver appium = getAppiumDriver();
            ((InteractsWithApps) appium).activateApp(appId);
            logger.info("[BasePage] App activated: {}", appId);
        } catch (Exception e) {
            logger.warn("[BasePage] Failed to activate app {}: {}", appId, e.getMessage());
        }
    }

    protected boolean isSessionActive() {
        try {
            AppiumDriver appium = getAppiumDriver();
            return appium != null && appium.getSessionId() != null;
        } catch (Exception e) {
            logger.warn("[BasePage] Cannot check session: {}", e.getMessage());
            return false;
        }
    }

    public void clickDoneOnKeyboard() {
       find(By.xpath("//XCUIElementTypeButton[@name='Done']")).waitUntilVisible().click();
    }

}
