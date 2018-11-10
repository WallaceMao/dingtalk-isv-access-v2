package com.rishiqing.dingtalk.biz.util;

import com.google.common.primitives.Ints;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 15:12
 */
public class DateUtil {
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
}
