package com.filmsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private DateUtil() {
    }

    public static Date parseDate(String value) {
        if (StringUtil.isBlank(value)) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(value.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式必须为 yyyy-MM-dd");
        }
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }
}
