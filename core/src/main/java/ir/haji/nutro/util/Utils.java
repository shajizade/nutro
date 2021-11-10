package ir.haji.nutro.util;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Saeedon 1/23/2018.
 */
public class Utils {
    public static List<String> getListOfString(Field[] fields) {
        List<String> result = new ArrayList<>();

        for (Field s : fields) {
            result.add(s.getName());
        }
        return result;
    }

    public static boolean isUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static Date daysPlus(Integer days) {
        return hoursPlus(days * 24);
    }

    public static Date hoursPlus(Integer hours) {
        return hoursPlus(new Date(), hours);
    }

    public static Date hoursPlus(Date start, Integer hours) {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(start);               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, hours);      // adds one hour
        return cal.getTime();
    }
}
