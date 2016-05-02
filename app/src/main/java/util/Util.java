package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by gavin on 30/4/16.
 */
public class Util {

    public static String getWeekDayName(Calendar cal) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        String week = weekDays[w];

        return week;
    }

    public static String getDateWeekString(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = sdf.format(cal.getTime());
        return String.format("%s(%s)",strDate, Util.getWeekDayName(cal));
    }

    public static String getDateString(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(cal.getTime());
        return sdf.format(cal.getTime());
    }


    public static String getDateTimeString(Calendar cal) {
        // 这里时间必须填0:00:00，坑爹啊！怎么设计的
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 0:00:00");
        String strDate = sdf.format(cal.getTime());
        return sdf.format(cal.getTime());
    }
}
