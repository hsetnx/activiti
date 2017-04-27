package com.common.tools.common.utils;

import java.util.List;
import java.util.Random;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 20:02
 * @Describe: 字符串工具类
 */
public class StringUtil {

    /**
     * Created with: jingyan.
     * Date: 2016/10/11  16:37
     * Description: 获取验证码
     */
    public static String generateRandomNum(int MAX, int MIN) {
        Random rand = new Random();
        int randNumber = rand.nextInt(MAX - MIN + 1) + MIN;
        return randNumber + "";
    }

    /**
     * 判断字符串s是否包含在数组中
     */
    public static boolean isInArray(String s, String[] array) {
        boolean b = false;
        if (s == null)
            return b;
        try {
            for (int i = 0; array != null && i < array.length; i++) {
                if (s.equals(array[i]))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/9/25  19:06
     * Description: 生成随机字符串
     */
    public static String getRandomStr(int length) {
        if (length < 1) {
            return null;
        }
        StringBuilder result = new StringBuilder("");
        char[] allChars = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'X'};
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(allChars.length);
            result.append(allChars[index]);
        }
        return result.toString();
    }

    /**
     * Created with: jingyan.
     * Date: 2016/12/8  14:36
     * Description: 数字大写
     */
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String u[] = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/9/25  18:42
     * Description: 按要求切割字符串
     */
    public static String[] splitString(String srcStr, String splitter) {
        if (srcStr == null) return new String[]{""};
        String[] tmpArr = srcStr.split(splitter);
        if (tmpArr == null || tmpArr.length == 0) {
            return new String[]{""};
        } else {
            for (int index = 0; index < tmpArr.length; index++) {
                tmpArr[index] = tmpArr[index].trim();
            }
            return tmpArr;
        }
    }

    /**
     * Created with: jingyan.
     * Date: 2016/9/18  20:27
     * Description: list to String 加间隔符
     */
    public static String listToString(List list, char separator) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * Created with: jingyan.
     * Date: 2016/9/25  18:48
     * Description: 数字前面补0 返回字符串
     */
    public static String int2Str(int intValue, int strLength) {
        if (strLength > 0) {
            char padding = '0';
            StringBuilder result = new StringBuilder();
            String fromStr = String.valueOf(intValue);
            for (int i = fromStr.length(); i < strLength; i++) {
                result.append(padding);
            }
            result.append(fromStr);
            return result.toString();
        } else {
            return null;
        }
    }
}
