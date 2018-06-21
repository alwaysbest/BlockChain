package cn.edu.nju.software.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel
 * @since 2018/5/2 15:08
 */
public class DateUtil {
    private static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd " +
            "HH:mm:ss");

    public static String formatDate(long timeMis) {
        return formatDate(new Date(timeMis));
    }

    public static String formatDate(Date date) {
        return DEFAULT_DATE_FORMAT.format(date);
    }

    public static Date parseDate(String date) {
        try {
            return DEFAULT_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(parseDate("2018-05-02 20:27:12").getTime());
    }


}
