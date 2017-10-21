package io.leopard.bdb;

import com.sleepycat.je.DatabaseException;

/**
 * Oracle Berkeley DB
 * 
 * @author 谭海潮
 *
 */
public interface Bdb {

	boolean put(String key, String value) throws DatabaseException;

	String getString(String key) throws DatabaseException;

	boolean delete(String key) throws DatabaseException;

	/**
	 * 获取记录条数
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	long count() throws DatabaseException;

	/**
	 * 同步数据到磁盘
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	boolean sync() throws DatabaseException;

	boolean putNoDupData(String key, String value) throws DatabaseException;
}
