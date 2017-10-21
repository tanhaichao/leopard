package io.leopard.exporter;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentLockedException;
import com.sleepycat.je.tree.DuplicateEntryException;

import io.leopard.bdb.Bdb;
import io.leopard.bdb.BdbMultiDatabaseImpl;
import io.leopard.burrow.util.StringUtil;

public class IdTransverterBdbImpl implements IdTransverter {

	protected Log logger = LogFactory.getLog(this.getClass());

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
			return bdb.putNoDupData(id, newId);
		}
		catch (DuplicateEntryException e) {
			logger.warn(e.getMessage());
			return false;
		}
		catch (DatabaseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String generateNewId(String tableName, String id) {
		String prefix = tableName.replace("_", "");
		String uuid = StringUtil.uuid();
		return prefix + uuid.substring(prefix.length() - 1);
	}

}
