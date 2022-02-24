package com.uto.puai.utils;

import cn.hutool.core.util.StrUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Richard
 * @version 1.0
 * @description 日期工具类
 * @date 2021/8/12 10:12
 */
public class DateUtil {

    /**
     * 判断时间格式 格式必须为“yyyy-MM-dd”,或者“yyyy-MM-dd HH:mm:ss”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     * @param sDate
     * @return
     */
    public static boolean isLegalDate(String sDate) {

        if (StrUtil.isNotBlank(sDate)) {
            switch (sDate.length()) {
                case 10:
                    return checkDate(new SimpleDateFormat("yyyy-MM-dd"), sDate);
                case 19:
                    return checkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), sDate);
                default:
                    return false;
            }
        }
        return false;
    }

    public static Boolean checkDate(DateFormat formatter, String sDate) {
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }
}
