package com.yarm.dao.util;

import com.yarm.dao.constant.SystemConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * author:Yarm.Yang
 * Date:2019/10/23
 * Time:16:11
 * Des:
 */
@Slf4j
public class DBUtils {
    public static final int SEED = 0x13579BDF;
    private static final String CLASS_NAME = "表、库处理工具类";
    private static int DATABASE_NUM = 2;// 分库粒度
    private static final int TABLE_NUM = 1;// 分表粒度
    public static final String DATABASE_FIX = "mysql_";// 库名前缀
    private static final String TABLE_FIX = "_";// 表名后缀

    /**
     * Do 设置分库分表粒度
     * @param num
     */
    public static void  setDatabaseNum(int num) {
        DATABASE_NUM = num > DATABASE_NUM ? num : DATABASE_NUM;
    }

    public static Map<String, Object> getDataBaseMap(String dataBaseKey, String tableName) {
        String functionName = "获取数据库基本参数Map/getDataBaseMap";
        String msg = "进入" + CLASS_NAME + "--" + functionName;
        Map<String, Object> map = new HashMap<String, Object>();
        // 1、解析分库分表字段
        // 表主键，如：201611180F5012345678
        if (null == dataBaseKey || dataBaseKey.length() < DATABASE_NUM) {
            log.error(msg + ",分库分表关键字错误:dataBaseKey=" + dataBaseKey);
            return map;
        }
        // 取中间4位GUID标识位
        int hash = Math.abs(dataBaseKey.hashCode());
        // 2、设置库名
        int index = hash % (DATABASE_NUM * TABLE_NUM);
        int dataBaseIndex = index % DATABASE_NUM +1;
        // 3、设置表名
        int tableIndex = index % TABLE_NUM +1;
        log.debug(msg + ",数据库基本参数:key=" + dataBaseKey + ";hash=" + hash + ";index=" + index);
        map = getDataBaseMapByIndex(dataBaseIndex, tableIndex);
        if (StringUtils.isNotBlank(tableName)) {
            map.put(SystemConst.TABLE_NAME.name(), tableName + TABLE_FIX + tableIndex);
        }
        return map;
    }

    /**
     * 根据dbIndex和tableIndex获取数据库基本参数Map
     * @param dataBaseIndex
     * @param tableIndex
     * @return
     */
    public static Map<String, Object> getDataBaseMapByIndex(int dataBaseIndex, int tableIndex) {
        String functionName = "获取数据库基本参数Map/setDataSource";
        log.debug("进入" + CLASS_NAME + "--" + functionName + ",数据库基本参数: dataBaseIndex=" + dataBaseIndex + ";tableIndex="
                + tableIndex);
        String databaseKey = DATABASE_FIX + dataBaseIndex;// 动态设置datasource名称, // 切换数据源切换到@transaction前
        String dbName = TABLE_FIX + dataBaseIndex;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SystemConst.tableUserFix.name(), TABLE_FIX + tableIndex);
        map.put(SystemConst.dbUserFix.name(), dbName + ".");
        map.put(SystemConst.MUTILPLE_DB_KEY.name(), databaseKey);// 多数据源对应的key
        return map;
    }
    /**
     * 获取memGuid的哈希值%512(2^10)的结果，如为负数则+512补正。用户生成流水后三位值
     * @return
     */
    public static int getTranShardByUserId(String userId) {
        int part = MurmurHash3.murmurhash3_x86_32(userId, 0, userId.length(), DBUtils.SEED);
        part = part % 512;
        if (part < 0) {
            part += 512;
        }
        return part;
    }

    /**
     * 根据userId和tableNum获取分表基本参数Map
     * @param userId
     * @param tableNum
     * @return
     */
    public static Map<String,Object> getDataBaseMapByUserId(int userId, int tableNum){
        int hash= userId % 128;
        int index = hash % (DATABASE_NUM * tableNum);
        int dataBaseIndex = index % DATABASE_NUM +1;
        int tableIndex = index % tableNum +1;
        Map<String, Object> map = new HashMap<String, Object>();
        String dbName = TABLE_FIX + dataBaseIndex;

        map.put(SystemConst.tableUserFix.name(), TABLE_FIX + tableIndex);
        map.put(SystemConst.dbUserFix.name(), dbName + ".");
        return map;
    }
}