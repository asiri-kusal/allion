package com.allion.APIadapter.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateTimeUtil {

    public static Map<String, String> getDatesByYearAndWeek(int year, int week) throws ParseException {

        Map<String, String> map = new HashMap<>();
        // Get calendar set to current date and time
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        map.put("startDate", df.format(c.getTime()));

        for (int i = 0; i < 6; i++) {
            c.add(Calendar.DATE, 1);
        }
        map.put("endDate",df.format(c.getTime()));

        return map;
    }

    public static Map<String, Integer> getYearAndDate(Date date) {
        Map<String, Integer> yearAndDateMap = new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int year = c.get(Calendar.YEAR);
        yearAndDateMap.put("week", dayOfWeek);
        yearAndDateMap.put("year", year);
        return yearAndDateMap;
    }
}
