package helpers;

import org.apache.commons.lang.RandomStringUtils;

public class StringManager {
    private static final int RANDOM_VALUE_LENGTH_SHORT = 5;
    private static final int RANDOM_VALUE_LENGTH = 8;
    private static final int RANDOM_VALUE_LENGTH_LONG = 16;
    private static final String PREFIX = "AUTO";
    public static final String URL_SEPARATOR = "/";
    public static final String EXTENSION_HTML = ".html";
    public static final String EDITORS_POSTFIX = "editor" + EXTENSION_HTML;
    public static final String SITES_POSTFIX = "sites" + EXTENSION_HTML;
    public static final String ASSETS_POSTFIX = "assets" + EXTENSION_HTML;
    
    public static String getRandomAlphabetic() {
        return PREFIX + RandomStringUtils.randomAlphabetic(RANDOM_VALUE_LENGTH);
    }
    
    public static String getRandomAlphanumeric() {
        return PREFIX + RandomStringUtils.randomAlphanumeric(RANDOM_VALUE_LENGTH);
    }
    
    public static String getRandomAlphanumericLong() {
        return PREFIX + RandomStringUtils.randomAlphanumeric(RANDOM_VALUE_LENGTH_LONG);
    }
    
    public static String getRandomNumeric() {
        return RandomStringUtils.randomNumeric(RANDOM_VALUE_LENGTH);
    }
    
    public static String getRandomNumericShort() {
        return RandomStringUtils.randomNumeric(RANDOM_VALUE_LENGTH_SHORT);
    }
}
