package ir.haji.nutro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.haji.nutro.exception.BadRequestException;
import ir.huri.jcal.JalaliCalendar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Saeed
 *         Date: 11/8/2017
 */
public class CommonUtil {
    public static final String UTM_PLACEHOLDER = "[utm_code]";

    public static String now() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss.SSS");
        return simpleDateFormat.format(new Date());
    }


    public static Object getJalaliDateStr(Date date) {
        Object column;
        JalaliCalendar jalaliCalendar = getJalaliCalendar(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        column = jalaliCalendar.toString() + " " + simpleDateFormat.format(date);
        return column;
    }

    public static String getJalaliStringRtl(Date date) {
        String column;
        JalaliCalendar jalaliCalendar = getJalaliCalendar(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        column = persianNumber(simpleDateFormat.format(date)) + " " + getJalaliDateStringRtl(jalaliCalendar);
        return column;
    }

    public static JalaliCalendar getJalaliCalendar(Date date) {
        if (date == null)
            return null;
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return new JalaliCalendar(gregorianCalendar);
    }

    public static String getJalaliDateString(JalaliCalendar date) {
        if (date == null)
            return null;
        String result = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
        return persianNumber(result);
    }

    public static String getJalaliDateStringRtl(JalaliCalendar date) {
        String result = date.getYear() + "/" + date.getMonth() + "/" + date.getDay();
        return persianNumber(result);
    }

    public static String getJalaliDateString(Date date) {
        return getJalaliDateString(getJalaliCalendar(date));
    }

    public static String simplePriceFormat(Long price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(0);
        ((DecimalFormat) formatter).setPositivePrefix("");
        return formatter.format(price);
    }

    public static String jsonValue(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String response = mapper.writeValueAsString(object);
            return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "BAD FORMAT";
    }

    public static Date teleDateConvert(String query) {
        if (query == null || query.isEmpty())
            return null;
        query = query.trim();
        if ("null".equals(query.toLowerCase())) {
            return null;
        }
        if ("now".equals(query.toLowerCase())) {
            return new Date();
        }
        if (query.startsWith("+") || query.startsWith("-")) {
            String[] split = query.split(" ");
            if (split.length < 2)
                throw new BadRequestException("Bad Format");
            Integer number = Integer.parseInt(split[0]);
            Integer unit = getUnitFromUnitName(split[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.add(unit, number);
            return calendar.getTime();
        }
        String[] split = query.split(" ");
        if (split.length == 1) {
            String[] timeParts = query.replace("-", ":").split(":");
            if (timeParts.length < 2)
                throw new BadRequestException("Bad Date Format");

            Integer hour = Integer.parseInt(timeParts[0]);
            Integer minute = Integer.parseInt(timeParts[1]);
            Date result = new Date();
            result.setHours(hour);
            result.setMinutes(minute);
            return result;
        } else if (split.length == 2) {
            String dateStr = split[0];
            String timeStr = split[1];
            Date result = getDateFromString(dateStr, timeStr);
            return result;
        } else {
            throw new BadRequestException("Bad Format");
        }
    }

    public static Date getDateFromString(String dateStr, String timeStr) {
        if (dateStr == null)
            return null;
        dateStr = dateStr.trim();

        Integer year;
        Integer mounth;
        Integer day;
        String[] dateParts = dateStr.replace("\\", "-").replace("/", "-").split("-");
        if (dateParts.length < 3)
            throw new BadRequestException("Bad Date Format");
        year = Integer.parseInt(dateParts[0]);
        mounth = Integer.parseInt(dateParts[1]);
        day = Integer.parseInt(dateParts[2]);
        boolean isJalali = false;
        if (year < 1300) {
            if (year < 50 && year > 10) {
                year += 2000;
                isJalali = false;
            } else {
                year += (year > 50) ? 1300 : 1400;
                isJalali = false;
            }
        } else {
            isJalali = (year < 2000);
        }
        Date result;
        if (isJalali) {
            JalaliCalendar jalaliCalendar = new JalaliCalendar(year, mounth, day);
            result = jalaliCalendar.toGregorian().getTime();
        } else {
            result = new Date(year - 1900, mounth - 1, day);
        }
        if (timeStr != null) {
            String[] timeParts = timeStr.replace("-", ":").split(":");
            if (timeParts.length < 2)
                throw new BadRequestException("Bad Date Format");
            result.setHours(Integer.parseInt(timeParts[0]));
            result.setMinutes(Integer.parseInt(timeParts[1]));
        }
        return result;
    }

    public static String persianPrice(Long raw) {
        return persianPrice(new Double(raw));
    }

    public static String persianPrice(Double raw) {
        if (raw == null)
            return null;
        String format = new DecimalFormat(",###.##").format(raw);
        return persianNumber(format);
    }

    public static String persianNumber(Long raw) {
        if (raw == null)
            return null;
        return persianNumber(raw.toString());
    }

    public static String persianNumber(Integer raw) {
        if (raw == null)
            return null;
        return persianNumber(raw.toString());
    }

    public static String persianNumber(String raw) {
        if (raw == null)
            return null;
        return raw.replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
    }

    protected static int getUnitFromUnitName(String name) {
        name = name.trim();
        if ("day".equals(name.toLowerCase()) || "d".equals(name.toLowerCase()))
            return Calendar.DAY_OF_YEAR;
        if ("min".equals(name.toLowerCase()) || "m".equals(name.toLowerCase()))
            return Calendar.MINUTE;
        return Calendar.HOUR;
    }

    public static String joinValues(List<String> items) {
        StringBuilder builder = new StringBuilder();
        for (String item : items) {
            builder.append(item).append(",");
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }

    public static Date startOfDay(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }

    public static Date endOfDay(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }

    public static String intToHourString(Double hours) {
        Integer hour = new Double(Math.floor(hours)).intValue();
        Integer minute = new Double((hours - hour) * 60.0).intValue();
        return hour + ":" + minute;
    }

    public static Date addHour(Date date, double hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, new Double(hours * 60).intValue());
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static String englishNumber(String raw) {
        return raw
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
    }


    public static Long parseLong(String text) {
        Long returnVal = null;
        try {
            returnVal = Long.parseLong(text);
        } catch (Exception ignored) {
        }
        return returnVal;
    }

    public static boolean isNumeric(String text) {
        try {
            Long.parseLong(text);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Long castToLong(Object value) {
        if (value == null)
            return null;
        if (value instanceof String)
            value = ((String) value).replace("\"", "").replace(",", "");
        if (value instanceof Long)
            return (Long) value;
        if (value instanceof BigDecimal)
            return ((BigDecimal) value).longValue();
        if (value instanceof String && isNumeric((String) value))
            return parseLong((String) value);
        if (value instanceof Integer)
            return ((Integer) value).longValue();
        if (value instanceof Double)
            return ((Double) value).longValue();
        if (value instanceof Float)
            return ((Float) value).longValue();
        return (Long) value;
    }

    public static Long timeDiff(Date newTime, Date oldTime, TimeUnit hours) {
        return hours.convert(newTime.getTime() - oldTime.getTime(), TimeUnit.MILLISECONDS);
    }

    public static String simleTimeString(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return formatter.format(time);
    }

    public static Date getFirstDayOfCurrentMonth() {
        JalaliCalendar jalaliCalendar = getJalaliCalendar(new Date());
        jalaliCalendar.setDay(1);
        return jalaliToDate(jalaliCalendar);
    }

    public static Date jalaliToDate(JalaliCalendar jalaliCalendar) {
        GregorianCalendar calendar = jalaliCalendar.toGregorian();
        return calendar.getTime();
    }

    public static Date getFirstDayOfLastMonth() {
        JalaliCalendar jalaliCalendar = getJalaliCalendar(new Date());
        jalaliCalendar.setDay(1);
        int month = jalaliCalendar.getMonth();
        jalaliCalendar.setMonth(month == 1 ? 12 : (month - 1));
        jalaliCalendar.setYear(jalaliCalendar.getYear() - (month == 1 ? 1 : 0));
        return jalaliToDate(jalaliCalendar);

    }

    public static boolean isPhoneNumber(String text) {
        text = preparePhone(text);
        if (text.startsWith("098") && text.length() != 13)
            return false;
        else if (!text.startsWith("098") && text.length() != 11)
            return false;
        return isNumeric(text);
    }

    public static String preparePhone(String text) {
        text = CommonUtil.englishNumber(text.trim()).replace("+", "0").replace("+", "0");
        if (!text.startsWith("0"))
            text = "0" + text;
        if (text.startsWith("098")) {
            text = "0" + text.substring(3);
        }
        return text;
    }

    public static Double parseDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
