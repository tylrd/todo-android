package com.mtaylord.todo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by taylor on 12/14/16.
 */

public class TimeUtil {

    private TimeUtil() {}

    private static DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

    public static Date toDate(String date) {
        Date datetime;
        try {
            datetime = dateFormat.parse(date);
        } catch (ParseException e) {
            Timber.d("Error parsing date: %s", date);
            throw new RuntimeException("Error parsing date", e);
        }
        return datetime;
    }

    public static String toDateString(Date date) {
        return dateFormat.format(date);
    }
}
