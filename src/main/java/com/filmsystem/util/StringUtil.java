package com.filmsystem.util;

public final class StringUtil {
    private StringUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String trimToNull(String value) {
        if (isBlank(value)) {
            return null;
        }
        return value.trim();
    }
}
