package com.dubbo.common.util;

import org.springframework.util.StringUtils;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 20:14
 * @version:
 **/
public class StringUtil extends StringUtils {

    /**
     * 字符串是否为空
     *
     * @param str 检测对象
     * @return 判断结果
     */
    public static boolean isBlank(String str) {
        boolean flag = false;
        if (null == str || "".equals(str.trim())) {
            flag = true;
        }
        return flag;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
