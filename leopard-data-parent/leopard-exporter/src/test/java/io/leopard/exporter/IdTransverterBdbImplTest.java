package io.leopard.exporter;

import java.io.File;

import org.junit.Test;

public class IdTransverterBdbImplTest {

	@Test
	public void IdTransverterBdbImpl() {
		IdTransverterBdbImpl idTransverter = new IdTransverterBdbImpl(new File("/tmp/exporter/id/"));
		idTransverter.add("user", "id1", "newId1");
		idTransverter.add("user", "id1", "newId1");
		String newId = idTransverter.transform("user", "id1");
		System.out.println("newId:" + newId);
		idTransverter.destroy();
	}

}