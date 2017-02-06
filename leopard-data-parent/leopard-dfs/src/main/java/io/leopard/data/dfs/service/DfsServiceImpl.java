package io.leopard.data.dfs.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DfsServiceImpl implements DfsService {

	@Autowired
	private DfsDao dfsDao;

	@Autowired
	private FileDao fileDao;

	@Override
	public void write(String filename, byte[] data, long uid) throws IOException {
		if (data.length <= 0) {
			throw new IllegalArgumentException("文件不能为空.");
		}

		dfsDao.write(filename, data, uid);

		fileDao.add(uid, filename);
	}

	@Override
	public byte[] read(String filename) throws IOException {
		return dfsDao.read(filename);
	}

}
