package com.krayc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static DateFormatter shared = new DateFormatter();

    private SimpleDateFormat simpleDateFormat;

    public static DateFormatter getDateFormatter() {
        return shared;
    }

    private DateFormatter() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public Date dateFromString(String string) {
        try {
            return simpleDateFormat.parse(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public String stringFromDate(Date date) {
        return simpleDateFormat.format(date);
    }

}
