package io.leopard.bdb;

import java.io.File;

import org.junit.Test;

import com.sleepycat.je.DatabaseException;

public class BdbImplTest {

	@Test
	public void add() throws DatabaseException {
		BdbImpl bdb = new BdbImpl();
		bdb.setDataDir(new File("/tmp/dbd/"));
		bdb.init();
		bdb.add("key", "value");
		bdb.destroy();
	}

}