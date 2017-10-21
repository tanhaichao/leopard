package io.leopard.bdb;

import java.io.File;

import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentLockedException;
import com.sleepycat.je.Transaction;

public class BdbImpl implements Bdb {

	protected Environment environment;

	private BdbDatabaseImpl bdb;

	// private EntityStore store;
	protected File dataDir;

	protected Transaction transaction;

	public File getDataDir() {
		return dataDir;
	}

	public void setDataDir(File dataDir) {
		this.dataDir = dataDir;
	}

	public Environment getEnvironment() {
		return environment;
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
		// Database database = environment.openDatabase(transaction, "BDB", dbConfig);
		this.bdb = new BdbDatabaseImpl(environment, "DEFAULT");
	}

	@Override
	public boolean put(String key, String value) throws DatabaseException {
		return bdb.put(key, value);
	}

	@Override
	public boolean putNoDupData(String key, String value) throws DatabaseException {
		return bdb.putNoDupData(key, value);
	}

	@Override
	public String getString(String key) throws DatabaseException {
		return bdb.getString(key);
	}

	@Override
	public boolean delete(String key) throws DatabaseException {
		return bdb.delete(key);
	}

	public void destroy() throws DatabaseException {
		bdb.destroy();
		if (environment != null) {
			environment.close();
		}
	}

	@Override
	public long count() throws DatabaseException {
		return bdb.count();
	}

}
