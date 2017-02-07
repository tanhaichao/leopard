package io.leopard.myjetty.webapp;

import org.junit.Test;

public class DirectoryDaoImplTest {

	private DirectoryDaoImpl directoryDaoImpl = new DirectoryDaoImpl();

	@Test
	public void add() {
		Directory directory = new Directory();
		// directory.setProjectId("zhongcao");
		// directory.setRootDir("/work/olla/zhongcao");
		// directory.setWebModuleDir("/work/olla/zhongcao/zhongcao-web");
		directory.setWar("/work/olla/zhongcao/zhongcao-web/target/zhongcao-web/");
		directoryDaoImpl.add(directory);
	}

}