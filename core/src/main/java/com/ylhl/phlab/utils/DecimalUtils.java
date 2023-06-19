package com.ylhl.phlab.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtils {
    /**
     * 元转分，确保price保留两位有效数字
     * @return
     */
    public static int changeY2F(double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        price = Double.parseDouble(df.format(price));
        return (int)(price * 100);
    }
    /**
     * 分转元，转换为bigDecimal在toString
     * @return
     */
    public static String changeF2Y(Integer price) {
        if(price!=null){

            return BigDecimal.valueOf(Long.valueOf(price)).divide(new BigDecimal(100)).toString();
        }
        return null;
    }
}
