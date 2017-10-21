package io.leopard.exporter;

import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentLockedException;

import io.leopard.bdb.Bdb;
import io.leopard.bdb.BdbMultiDatabaseImpl;

public class IdTransverterBdbImpl implements IdTransverter {

	private BdbMultiDatabaseImpl bdb;

	public IdTransverterBdbImpl(File dataDir) {
		bdb = new BdbMultiDatabaseImpl();
		bdb.setDataDir(dataDir);
		try {
			bdb.init();
		}
		catch (EnvironmentLockedException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (DatabaseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String transform(String tableName, String id) {
		try {
			Bdb database = bdb.getDatabase(tableName);
			String newId = database.getString(id);
			return newId;
		}
		catch (DatabaseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void destroy() {
		try {
			bdb.destroy();
		}
		catch (DatabaseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public boolean add(String tableName, String id, String newId) {
		try {
			return bdb.put(id, newId);
		}
		catch (DatabaseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
