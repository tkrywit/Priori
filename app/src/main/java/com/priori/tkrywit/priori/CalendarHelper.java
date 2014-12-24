package com.priori.tkrywit.priori;

import android.app.Activity;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Thomas on 12/21/2014.
 *
 * Creates current date strings formatted as: Day of Week, Month, Day
 * as well as current time strings
 */
public final class CalendarHelper {

    public static String getDateString(Calendar cal) {
        String s = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ", "
                + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "
                + cal.get(Calendar.DAY_OF_MONTH) + ", "
                + cal.get(Calendar.YEAR);
        return s;
    }

    public static String getTimeString(Calendar cal) {
        String s;
        if (String.valueOf(cal.get(Calendar.MINUTE)).length() == 1) {
            s = String.valueOf(cal.get(Calendar.HOUR)) + ":0"
                    + String.valueOf(cal.get(Calendar.MINUTE)) + " " +
                    cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH);
        } else {
            s = String.valueOf(cal.get(Calendar.HOUR)) + ":"
                    + String.valueOf(cal.get(Calendar.MINUTE)) + " " +
                    cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH);
        }
        return s;
    }

    //return full date and time string if they are set, and nothing if not
    public static String getDateTimeString(Activity act, Calendar cal) {

        String s = "";

        if (cal != null) {
            s = act.getResources().getString(R.string.due) + " "
                    + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ", "
                    + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "
                    + cal.get(Calendar.DAY_OF_MONTH) + ", "
                    + cal.get(Calendar.YEAR);
            if (String.valueOf(cal.get(Calendar.MINUTE)).length() == 1) {
                s += " - " + String.valueOf(cal.get(Calendar.HOUR)) + ":0"
                        + String.valueOf(cal.get(Calendar.MINUTE)) + " " +
                        cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH);
            } else {
                s += " - " + String.valueOf(cal.get(Calendar.HOUR)) + ":"
                        + String.valueOf(cal.get(Calendar.MINUTE)) + " " +
                        cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH);
            }
        }
        return s;
    }
}
