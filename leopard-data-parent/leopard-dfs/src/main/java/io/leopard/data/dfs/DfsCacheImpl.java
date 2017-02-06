package io.leopard.data.dfs;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class DfsCacheImpl implements Dfs, InitializingBean, DisposableBean {
	protected Log logger = LogFactory.getLog(this.getClass());

	private DfsGridImpl dfsGridImpl;
	private DfsFileImpl dfsFileImpl;

	private String server;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		dfsGridImpl = new DfsGridImpl();
		dfsGridImpl.setServer(server);
		dfsGridImpl.afterPropertiesSet();

		dfsFileImpl = new DfsFileImpl();
		dfsFileImpl.afterPropertiesSet();
	}

	@Override
	public void destroy() throws Exception {
		dfsGridImpl.destroy();
		dfsFileImpl.destroy();
	}

	@Override
	public boolean create(String filename, byte[] data) {
		// logger.info("create:" + filename);
		dfsFileImpl.create(filename, data);
		return dfsGridImpl.create(filename, data);
	}

	@Override
	public byte[] read(String filename) throws IOException {
		try {
			return dfsFileImpl.read(filename);
		}
		catch (IOException e) {
			byte[] data = dfsGridImpl.read(filename);
			dfsFileImpl.create(filename, data);
			return data;
		}
	}

	@Override
	public boolean delete(String filename) {
		dfsFileImpl.delete(filename);
		return dfsGridImpl.delete(filename);
	}

}
