package io.leopard.data.dfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class DfsGridImpl implements Dfs, InitializingBean, DisposableBean {

	protected Log logger = LogFactory.getLog(this.getClass());

	private String server;

	private MongoClient client;

	private GridFS fs;

	public DfsGridImpl() {

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	protected GridFS getGridFS() {
		if (fs == null) {
			this.fs = loadGridFS();
		}
		return fs;
	}

	@SuppressWarnings("deprecation")
	private synchronized GridFS loadGridFS() {
		if (fs != null) {
			return fs;
		}
		String[] list = server.split(":");
		String host = list[0];
		int port = Integer.parseInt(list[1]);

		String username = null;
		String password = null;
		if (list.length >= 4) {
			username = list[2];
			password = list[3];
		}

		int connectTimeout = 1000 * 60;
		MongoClientOptions options = new MongoClientOptions.Builder().connectTimeout(connectTimeout).build();
		client = new MongoClient(new ServerAddress(host, port), options);
		// @SuppressWarnings("deprecation")
		DB db = client.getDB("dfs");
		if (username != null && username.length() > 0) {
			if (password != null && password.length() > 0) {
				db.addUser(username, password.toCharArray());
			}
		}
		return new GridFS(db, "fs");
	}

	@Override
	public void destroy() throws Exception {
		if (client != null) {
			this.client.close();
			this.client = null;
		}
	}

	@Override
	public boolean delete(String filename) {
		getGridFS().remove(filename);
		return true;
	}

	@Override
	public boolean create(String filename, byte[] data) {
		this.delete(filename);
		GridFSInputFile file = getGridFS().createFile(data);
		file.setFilename(filename);
		file.save();
		return true;
	}

	@Override
	public byte[] read(String filename) throws IOException {
		GridFSDBFile file = getGridFS().findOne(filename);
		if (file == null) {
			throw new FileNotFoundException(filename);
		}
		InputStream input = file.getInputStream();
		try {
			return IOUtils.toByteArray(input);
		}
		finally {
			input.close();
		}
	}

}
