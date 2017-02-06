package io.leopard.data.dfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class DfsFileImpl implements Dfs, InitializingBean, DisposableBean {

	protected Log logger = LogFactory.getLog(this.getClass());

	private static final File rootDir = new File("/data/dfs");

	@Override
	public boolean create(String filename, byte[] data) {
		File file = new File(rootDir, filename);
		// logger.info("create:" + file.getAbsolutePath());
		try {
			FileUtils.writeByteArrayToFile(file, data);
			return true;
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public byte[] read(String filename) throws IOException {
		File file = new File(rootDir, filename);
		// logger.info("read:" + file.getAbsolutePath());
		return FileUtils.readFileToByteArray(file);
	}

	@Override
	public boolean delete(String filename) {
		File file = new File(rootDir, filename);
		FileUtils.deleteQuietly(file);
		return false;
	}

	@Override
	public void destroy() throws Exception {

	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
