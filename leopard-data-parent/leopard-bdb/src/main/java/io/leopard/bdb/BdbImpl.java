package io.leopard.bdb;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentLockedException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.Transaction;

public class BdbImpl implements Bdb {

	private Environment environment;

	private Database database;

	// private EntityStore store;
	private File dataDir;

	private Transaction transaction;

	public File getDataDir() {
		return dataDir;
	}

	public void setDataDir(File dataDir) {
		this.dataDir = dataDir;
	}

	public void init() throws EnvironmentLockedException, DatabaseException {
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setSortedDuplicates(true);
		environmentConfig.setReadOnly(false);
		environmentConfig.setAllowCreate(true);
		// Open the environment and entity store
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		environment = new Environment(dataDir, environmentConfig);
		database = environment.openDatabase(transaction, "BDB", dbConfig);
	}

	@Override
	public boolean add(String key, String value) throws DatabaseException {
		database.put(transaction, new DatabaseEntry(key.getBytes()), new DatabaseEntry(value.getBytes()));
		return true;
	}

	@Override
	public String getString(String key) throws DatabaseException {
		DatabaseEntry data = new DatabaseEntry();
		database.get(transaction, new DatabaseEntry(key.getBytes()), data, LockMode.DEFAULT);
		return new String(data.getData());
	}

	public void destroy() throws DatabaseException {
		if (database != null) {
			database.close();
		}
		if (environment != null) {
			environment.close();
		}
	}

}
