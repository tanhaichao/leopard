package io.leopard.data.dfs;

import org.junit.Test;

public class DfsGridImplTest {

	@Test
	public void test() throws Exception {
		DfsGridImpl dfsGridImpl = new DfsGridImpl();
		dfsGridImpl.afterPropertiesSet();
		dfsGridImpl.read("/test.jpg");
		dfsGridImpl.destroy();
	}

}