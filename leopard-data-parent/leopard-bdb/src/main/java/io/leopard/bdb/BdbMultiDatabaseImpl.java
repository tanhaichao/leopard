package io.leopard.bdb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sleepycat.je.DatabaseException;

public class BdbMultiDatabaseImpl extends BdbImpl {

	private Map<String, BdbDatabaseImpl> bdbMap = new ConcurrentHashMap<>();

	public Bdb getDatabase(String databaseName) throws DatabaseException {
		BdbDatabaseImpl bdb = bdbMap.get(databaseName);
		if (bdb == null) {
			bdb = new BdbDatabaseImpl(environment, databaseName);
			bdbMap.put(databaseName, bdb);
		}
		return bdb;
	}

	@Override
	public void destroy() throws DatabaseException {
		for (BdbDatabaseImpl bdb : bdbMap.values()) {
			bdb.destroy();
		}
		super.destroy();
	}
}
