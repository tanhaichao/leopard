package io.leopard.exporter;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class IdTransverterBdbImplTest {

	@Test
	public void IdTransverterBdbImpl() throws IOException {
		File dataDir = new File("/tmp/exporter/id/");
		FileUtils.forceDelete(dataDir);

		IdTransverterBdbImpl idTransverter = new IdTransverterBdbImpl(dataDir);
		idTransverter.add("user", "id1", "newId1");
		idTransverter.add("user", "id1", "newId1");
		String newId = idTransverter.transform("user", "id1");
		System.out.println("newId:" + newId);
		System.out.println("newId:" + idTransverter.transform("user2", "id1"));
		idTransverter.destroy();
	}

}