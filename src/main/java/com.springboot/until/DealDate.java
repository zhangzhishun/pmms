package com.springboot.until;

import org.junit.platform.commons.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author eternalSy
 * @version 1.0.0
 */
public class DealDate {
    /**
     * 将2019-06-03T16:00:00.000Z日期格式转换为2019-06-03 16:00:00格式
     * @param oldDateStr
     * @return
     */
    public static String dealDateFormat(String oldDateStr) {
        if (StringUtils.isBlank(oldDateStr)){
            return null;
        }
        Date date,date1;
        String dateStr,result = null;
        try {
            dateStr = oldDateStr.replace("Z", " UTC");//是空格+UTC
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            date1 = df.parse(dateStr);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date = df1.parse(date1.toString());
            //此处是将date类型装换为字符串类型，比如：Sat Nov 18 15:12:06 CST 2017转换为2017-11-18 15:12:06
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = sf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("函数返回值：" + result);
        return result;
    }
}
