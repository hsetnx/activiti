package com.common.tools.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 20:06
 * @Describe: 时间工具类
 */
public class DateUtil {

    /**
     * Created with: jingyan.
     * Date: 2017/1/12  11:07
     * Description: 年月日int转date
     */
    public static Date getDateByNum(int year, int mouth, int day) {
        String mouthStr = StringUtil.int2Str(mouth, 2);
        String dayStr = StringUtil.int2Str(day, 2);
        String timeStr = year + mouthStr + dayStr;
        return str2Date(timeStr, "yyyyMMdd");
    }

    /**
     * Created with: jingyan.
     * Date: 2016/9/25  19:11
     * Description: str to date
     */
    public static Date str2Date(String strDate, String strFormat) {
        if (PubMethod.isEmpty(strFormat)) {
            strFormat = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/9/25  17:43
     * Description: 获取当前时间字符串
     */
    public static String getDateStr(String formatStr) {
        if (PubMethod.isEmpty(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = new Date();
        String formatDate = sdf.format(date);
        return formatDate;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/10/25  14:43
     * Description: 时间转为str
     */
    public static String date2Str(Date date, String formatStr) {
        if (PubMethod.isEmpty(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String formatDate = sdf.format(date);
        return formatDate;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/11/30  10:52
     * Description: 获取年份
     */
    public static int getYears() {
        Calendar nowDate = Calendar.getInstance();
        return nowDate.get(Calendar.YEAR);
    }

    /**
     * Created with: jingyan.
     * Date: 2017/1/4  17:55
     * Description: 获取月份
     */
    public static int getMonth() {
        Calendar nowDate = Calendar.getInstance();
        return nowDate.get(Calendar.MONTH) + 1;
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 20:11
     * @Describe:获取日
     */
    public static int getDay() {
        Calendar nowDate = Calendar.getInstance();
        return nowDate.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Created with: jingyan.
     * Date: 2017/2/15  20:28
     * Description: 时间变更处理（负数为减）
     */
    public static Date dateAddNum(Date oldDate, int addYear, int addMonth, int addDay) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(oldDate);
        rightNow.add(Calendar.YEAR, addYear);
        rightNow.add(Calendar.MONTH, addMonth);
        rightNow.add(Calendar.DAY_OF_MONTH, addDay);
        Date newDate = rightNow.getTime();
        return newDate;
    }

    /**
     * Created with: jingyan.
     * Date: 2017/2/16  17:13
     * Description: 获取时间年月日数字
     * [年][月][日]
     */
    public static int[] getDateNum(Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        int[] dateNums = new int[3];
        dateNums[0] = rightNow.get(Calendar.YEAR);
        dateNums[1] = rightNow.get(Calendar.MONTH) + 1;
        dateNums[2] = rightNow.get(Calendar.DAY_OF_MONTH);
        return dateNums;
    }
}
