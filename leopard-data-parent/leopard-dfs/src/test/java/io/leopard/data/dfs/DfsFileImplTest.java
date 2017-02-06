package io.leopard.data.dfs;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class DfsFileImplTest {

	protected DfsFileImpl dfs = new DfsFileImpl();

	@Test
	public void delete() throws IOException {
		String filename = "/user/avatar/1.jpg";
		dfs.create(filename, "str".getBytes());
		Assert.assertEquals("str", new String(dfs.read(filename)));
		dfs.delete(filename);
		try {
			dfs.read(filename);
			Assert.fail("怎么文件还存在?");
		}
		catch (IOException e) {

		}
	}

}