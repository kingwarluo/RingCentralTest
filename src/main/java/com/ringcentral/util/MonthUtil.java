package com.ringcentral.util;

/**
 * 月份工具类
 *
 * @author jianhua.luo
 * @date 2020/9/22
 */
public class MonthUtil {

    /**
     * 是否有效的月份
     * @param month
     * @return
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * 获取季度
     * @param month
     * @return
     */
    public static int getQuarter(int month) {
        if(!isValidMonth(month)) {
            return 0;
        }
        return (month - 1) / 3 + 1;
    }

}
