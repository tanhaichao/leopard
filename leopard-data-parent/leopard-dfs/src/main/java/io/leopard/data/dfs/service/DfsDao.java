package io.leopard.data.dfs.service;

import java.io.IOException;

public interface DfsDao {

	void write(String filename, byte[] data, long uid) throws IOException;

	
	byte[] read(String filename) throws IOException;

}
