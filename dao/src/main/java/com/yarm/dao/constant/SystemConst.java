/**
 * feiniu.com Inc.
 * Copyright (c) All Rights Reserved.
 */
package com.yarm.dao.constant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public enum SystemConst {

    /**
     * 成功
     */
	SUCCESS("success"),
	
	/**
     * 失败
     */
	FAILED("failed"),
	
	/**
     * 系统错误
     */
	SYSTEM_ERROR("system error"),
	
	/**
     * 接口响应失败
     */
	API_ERROR("接口响应失败"),
	
	/**
     * 查无资料
     */
	NO_DATA("查无资料"),

	/**
     * 数据库多数据源配置的key,application.xml中的multipleDataSourceMysql(bean)的key
     */
	MUTILPLE_DB_KEY,

	//application.properties配置文件参数 begin
	/**
     * MYSQL_DB_PREFIX
     */
	MYSQL_DB_PREFIX,

	/**
     * dbUserFix
     */
	dbUserFix,

	/**
     * tableUserFix
     */
	tableUserFix,

	/**
     * TABLE_NAME
     */
	TABLE_NAME,

	/**
     * DOUBLE_WRITE
     */
	DOUBLE_WRITE,

	/**
     * DB_INDEX_FROM
     */
	DB_INDEX_FROM, // 数据库开始序号
	// application.properties配置文件参数 end

	/**
     * 会员guid长度
     */
	GUID_LENGTH(36),

	/**
     * roSeq,riSeq,rdSeq长度
     */
	SEQ_ID_LENGTH(20),
	
	/**
     * sequence截取位数
     */
	SEQ_SPLIT_LENGTH(6),
	
	/**
     * rest类型
     */
	PUT, GET, POST, DELETE,
	/**
	 * 日志中trance_id 的key
	 */
	MDC_KEY_TRACE_ID("trace_id"),
	;
	// Defined ENUM Type Begin
	private String value;
	private int intVal;
	private BigDecimal bdVal;
	private Map<?, ?> map;
	private List<?> list;
	public static final String KEY = "KEY"; 

	SystemConst() {
	}

	SystemConst(String value) {
		this.value = value;
	}

	SystemConst(int intVal) {
		this.intVal = intVal;
	}

	SystemConst(BigDecimal bdVal) {
		this.bdVal = bdVal;
	}

	SystemConst(Map<?, ?> map) {
		this.map = map;
	}

	SystemConst(List<?> list) {
		this.list = list;
	}

	public String val() {
		return value;
	}

	public int intVal() {
		return intVal;
	}

	public BigDecimal bdVal() {
		return bdVal;
	}

	public Map<?, ?> map() {
		return map;
	}

	public List<?> list() {
		return list;
	}
	// Defined ENUM Type End
}
