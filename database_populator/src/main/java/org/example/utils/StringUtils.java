package org.example.utils;

public class StringUtils {

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1)
                .toUpperCase() + str.substring(1);
    }

    public static String removeNonAlphaNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static boolean isCleanedEqual(String str1, String str2) {
        return removeNonAlphaNumeric(str1.toLowerCase()).equals(removeNonAlphaNumeric(str2.toLowerCase()));
    }

    public static int safeConvertOrDefault(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
