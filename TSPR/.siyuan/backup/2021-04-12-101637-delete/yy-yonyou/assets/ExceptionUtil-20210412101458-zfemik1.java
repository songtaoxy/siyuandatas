package com.yonyou.ucf.mdf.app.util;

/**
 * @author: st
 * @date: 2021/3/31 10:18
 * @version: 1.0
 * @description:
 */
public class ExceptionUtil {

    // 获取嵌套异常中的最终信息
    // 详见测出方法
    public static String getRealMessage(Throwable e) {
        // 如果e不为空，则去掉外层的异常包装
        while (e != null) {
            Throwable cause = e.getCause();
            if (cause == null) {
                return e.getMessage();
            }
            e = cause;
        }
        return "";
    }
}
