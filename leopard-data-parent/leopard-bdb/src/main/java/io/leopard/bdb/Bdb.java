package io.leopard.bdb;

import com.sleepycat.je.DatabaseException;

/**
 * Oracle Berkeley DB
 * 
 * @author 谭海潮
 *
 */
public interface Bdb {

	boolean add(String key, String value) throws DatabaseException;

	String getString(String key) throws DatabaseException;

	boolean delete(String key) throws DatabaseException;
}
