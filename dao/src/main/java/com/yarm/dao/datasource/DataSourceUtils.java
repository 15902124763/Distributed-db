package com.yarm.dao.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IDEA
 * author:Yarm.Yang
 * Date:2019/10/23
 * Time:15:47
 * Des:
 */
@Slf4j
public class DataSourceUtils {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static String dbMode = null;

    public static void setCurrentKey(String value) {
        threadLocal.set(value);
    }

    public static void setDbMode(String value) {
        dbMode = value;
    }

    public static String getDbMode() {
        return dbMode;
    }
    /**
     * 获取当前数据源标识
     * @return
     */
    public static String getCurrentKey() {
        String datasource = threadLocal.get();
        log.debug("==============current datasource=================" + datasource + "========");
        return datasource;
    }

    /**
     * 删除当前数据源标识
     */
    public static void removeCurrentKey() {
        threadLocal.remove();
    }
}