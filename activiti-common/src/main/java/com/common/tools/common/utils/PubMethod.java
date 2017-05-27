package com.common.tools.common.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with: jingyan.
 * Date: 2016/9/16  23:03
 * Description: 通用工具类
 */
public class PubMethod {

    /**
     * Created with: jingyan.
     * Date: 2016/9/25  18:41
     * Description: 各种非空判断
     */
    public static boolean isEmpty(String Value) {
        if (Value == null || Value.trim().equals(""))
            return true;
        else
            return false;
    }

    public static boolean isEmpty(StringBuffer Value) {
        if (Value == null || (Value.toString().trim()).equals(""))
            return true;
        else
            return false;
    }

    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Set set) {
        if (set == null || set.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Map map) {
        if (map == null || map.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Object Value) {
        if (Value == null)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Double value) {
        if (value == null || value.doubleValue() == 0.0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Long obj) {
        if (obj == null || obj.longValue() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Object[] Value) {
        if (Value == null || Value.length == 0)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        Map<String,String> map =new HashMap<>();
        map.put("a","1");
        map.put("b","7");
        System.out.println(CalculateUtil.calculationEquation("a+b",map));
    }
}
