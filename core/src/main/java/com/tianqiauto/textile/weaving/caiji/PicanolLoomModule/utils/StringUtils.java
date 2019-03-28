package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类 bjw
 * @Date 2019/1/19 9:23
 */
public class StringUtils {

    /**
     * 字符串前后补空格
     * @Date 2019/1/19 9:58
     * @param number 正数在前补空格，负数在后补空格
     **/
    public static String addEmpty(int number, String data){
        if(null == data){
            return String.format("%"+number+"s","");
        }else{
            return String.format("%"+number+"s",data);
        }
    }

    /**
     * 数字在前补零 注意：传入的字符串必须是整数数字字符串
     * @Date 2019/1/19 9:59
     * @param number 在前补0
     **/
    public static String add0Before(int number, String data){
        if ((null == data) || (data.length() < 1)) {
            return String.format("%0" + number + "d", 0);
        } else{
            int i = new Integer(data);
            return add0Before(number,i);
        }
    }

    /**
     * 数字在前补零
     * @Date 2019/1/19 10:00
     * param number 在前补0
     **/
    public static String add0Before(int number, int data){
        return String.format("%0"+number+"d",data);
    }

    /**
     * 日期转换成字符串
     * @Date 2019/3/7 9:02
     **/
    public static String dateToString(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 当前日期转换成字符串
     * @Date 2019/3/7 9:02
     **/
    public static String NewDateToString(String format){
        return dateToString(new Date(),format);
    }

}
