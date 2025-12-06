package bb.utils;

import bb.utils.DataUtils;
import com.google.common.base.Strings;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.model.environment.ConfiguredEnvironment;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Utilities {

    public static boolean isCurrentPlatformAndroid() {
        return DataUtils.getValueConf("appium.platformName").equalsIgnoreCase("android");
    }

    public static boolean isPlatformIOS() {
        return DataUtils.getValueConf("appium.platformName").equalsIgnoreCase("ios");
    }


    public static String getCurrentLanguage() {
        String currentLanguage = Serenity.sessionVariableCalled("currentLanguage");
        if (Strings.isNullOrEmpty(currentLanguage)) {
            return DataUtils.getValueConf("serenity.lang");
        } else {
            return currentLanguage;
        }
    }

    public static boolean isCurrentLanguage() {
        return getCurrentLanguage().equalsIgnoreCase("vi");
    }

    public static String getPropertiesLangValue(String ws, String key) {
        Properties props = new Properties();
        String filePath = "language" + File.separator + ws + File.separator + getCurrentLanguage().toLowerCase() + ".properties";

        try {
            InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(filePath);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            props.load(isr);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return props.getProperty(key);
    }

    public static String formatAccountNumber(String account, String length) {
        return account.replaceAll("(.{" + length + "})", "$0 ").trim();
    }

    public static String getConfigValue(String key) {
        return ConfiguredEnvironment.getEnvironmentVariables().getProperty(key);
    }

    public static String getFileAsString(String relativePath) {
        try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(relativePath)) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String getTimestamp() {
        return java.time.LocalDateTime.now().toString().replace("T", " ").substring(0, 19);
    }

}
