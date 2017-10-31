package org.mybatis.generator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *时间工具类
 *
 *@author: Weiyf
 *Date: 2017/10/31 11:37
 */
public class DateTimeUtils {

    public static final String FULL_DATE = "yyyy-MM-dd HH:mm:ss";

    /**
     *获取时间戳
     *Create by: Weiyf
     *Date: 2017/10/18 9:54
     */
    public static String dateTimeSty(){
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE);
        String strDate = sdf.format(new Date());
        return strDate;
    }

}
