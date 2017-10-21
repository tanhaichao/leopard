package io.leopard.bdb;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.tree.DuplicateEntryException;

public class BdbDatabaseImpl implements Bdb {

	private Database database;

	private Transaction transaction;

	public BdbDatabaseImpl(Environment environment, String databaseName) throws DatabaseException {
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setSortedDuplicates(true);
		database = environment.openDatabase(transaction, databaseName, dbConfig);
	}

	@Override
	public boolean put(String key, String value) throws DatabaseException {
		database.put(transaction, new DatabaseEntry(key.getBytes()), new DatabaseEntry(value.getBytes()));
		return true;
	}

	@Override
	public boolean putNoDupData(String key, String value) throws DatabaseException {
		OperationStatus status = database.putNoDupData(transaction, new DatabaseEntry(key.getBytes()), new DatabaseEntry(value.getBytes()));
		String result = status.toString();
		if (result.endsWith(".KEYEXIST")) {
			throw new DuplicateEntryException("key[" + key + "]已存在.");
		}
		// System.err.println(status);
		return true;
	}

	@Override
	public String getString(String key) throws DatabaseException {
		DatabaseEntry data = new DatabaseEntry();
		database.get(transaction, new DatabaseEntry(key.getBytes()), data, LockMode.DEFAULT);
		byte[] bytes = data.getData();
		if (bytes == null) {
			return null;
		}
		return new String(bytes);
	}

	@Override
	public boolean delete(String key) throws DatabaseException {
		database.delete(transaction, new DatabaseEntry(key.getBytes()));
		return true;
	}

	public void destroy() throws DatabaseException {
		if (database != null) {
			database.close();
		}
	}

	@Override
	public long count() throws DatabaseException {
		// TODO 未正确实现
		// database.sync();
		return database.count();
	}

	@Override
	public boolean sync() throws DatabaseException {
		database.sync();
		return true;
	}
}
