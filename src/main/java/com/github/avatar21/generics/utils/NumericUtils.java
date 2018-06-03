package com.github.avatar21.generics.utils;

import com.github.avatar21.generics.constants.Regexp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * numeric utility functions
 */
public class NumericUtils {
    private static final Logger logger = LoggerFactory.getLogger(NumericUtils.class);

    /**
     * 转换字符串为 BigDecimal 类
     *
     * @param strAmount 9999.99 格式
     * @return 成功返回已转换的数字类，失败则返回0.00
     */
    public static BigDecimal verifyAndParseToBigDecimal(String strAmount) {
        return (!StringUtils.isEmpty(strAmount) && strAmount.matches(Regexp.NUMBER_REGEX) ? new BigDecimal(strAmount.trim()) : BigDecimal.ZERO);
    }

    /**
     * 转换字符串为 Integer 类
     *
     * @param strNum 9999 格式
     * @return 成功返回已转换的整数类，失败则返回0
     */
    public static Integer verifyAndParseToInteger(String strNum) {
        return (!StringUtils.isEmpty(strNum) && strNum.matches(Regexp.LONG_REGEX) ? Integer.parseInt(strNum.trim()) : 0);
    }

    /**
     * 转换字符串为 Double 类
     *
     * @param strDouble 9999.99 格式
     * @return 成功返回已转换的双浮点数类，失败则返回0.00
     */
    public static Double verifyAndParseToDouble(String strDouble) {
        return (!StringUtils.isEmpty(strDouble) && strDouble.matches(Regexp.NUMBER_REGEX) ? Double.parseDouble(strDouble.trim()) : 0.00);
    }

    /**
     * 转换字符串为 Float 类
     *
     * @param strFloat 9999.99f 格式
     * @return 成功返回已转换的浮点数类，失败则返回0.00f
     */
    public static Float verifyAndParseToFloat(String strFloat) {
        return (!StringUtils.isEmpty(strFloat) && strFloat.matches(Regexp.NUMBER_REGEX) ? Float.parseFloat(strFloat.trim()) : 0.00f);
    }
}
