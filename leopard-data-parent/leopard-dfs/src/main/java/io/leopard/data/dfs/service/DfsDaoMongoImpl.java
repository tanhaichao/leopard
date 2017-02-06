package io.leopard.data.dfs.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.leopard.data.dfs.Dfs;

@Repository
public class DfsDaoMongoImpl implements DfsDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private Dfs gridFs;

	@Override
	public void write(String filename, byte[] data, long uid) throws IOException {
		if (data == null || data.length == 0) {
			throw new IllegalArgumentException("文件内容不能为空.");
		}

		gridFs.create(filename, data);
	}

	@Override
	public byte[] read(String filename) throws IOException {
		byte[] data = this.gridFs.read(filename);
		// logger.info("read filename:" + filename + " data size:" + data.length);
		if (data.length <= 0) {
			logger.error("怎么会有空文件[" + filename + "].");
			throw new FileNotFoundException(filename);
		}
		return data;
	}

}
