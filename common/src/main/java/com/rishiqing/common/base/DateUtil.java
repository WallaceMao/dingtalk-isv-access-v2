package com.rishiqing.common.base;

import com.google.common.primitives.Ints;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 15:12
 */
public class DateUtil {
    public static Long MILLS_ONE_DAY = 1000L * 60L * 60L * 24L;
    /**
     * 返回当前时间，加上指定的秒数，得出的时间
     * @param seconds
     * @return
     */
    public static Date addSeconds(Long seconds){
        //  使用google的guava进行安全转换，当溢出时抛出异常
        int intSec = Ints.checkedCast(seconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, intSec);
        return calendar.getTime();
    }

    public static String format(Long mills) {
        return format(mills, null);
    }

    public static String format(Long mills, String format) {
        if (format == null) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(new Date(mills));
    }
}
