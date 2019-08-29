package servises.dateTimeManager;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateTimeManager {

    public static java.sql.Date fromUtilDateToSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date fromSqlDateToUtilDate(java.sql.Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date uDate = null;
        try {
            uDate = formatter.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return uDate;
    }

    public static Date fromStringToUtilDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = new Date(df.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static java.sql.Date fromStringToSqlDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date d = null;
        try {
            d = new java.sql.Date(df.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String fromDateToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
        return df.format(date);
    }

    public static Time fromStringToTime(String time) {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Time t = null;
        try {
            t = new Time(df.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String fromTimeToString(Time time) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(time);
    }
}
