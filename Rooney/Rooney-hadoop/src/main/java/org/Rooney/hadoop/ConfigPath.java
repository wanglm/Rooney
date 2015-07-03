package org.Rooney.hadoop;

public enum ConfigPath {
	/**
	 * 本地文件系统存放原始日志文件路径
	 */
	LOG_PATH,
	/**
	 * hdfs存放原始日志文件路径
	 */
	LOG_HDFS_PATH,
	/**
	 * 存放转换成推荐文件格式的输入文件路径
	 */
	RECOMMEND_INPUT,
	/**
	 * 推荐文件生成路径
	 */
	RECOMMEND_OUTPUT,
	/**
	 * 存放补足物品文件路径
	 */
	RECOMMEND_ITEM,
	/**
	 * userID对应文件存放
	 */
	USER_ID,
	/**
	 * 物品ID对应文件存放
	 */
	ITEM_ID;

}
