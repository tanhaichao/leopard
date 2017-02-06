package io.leopard.data.dfs;

import java.io.IOException;

public interface Dfs {

	boolean create(String filename, byte[] data);

	byte[] read(String filename) throws IOException;

	// GridFSInputFile createFile(byte[] data);
	// GridFSDBFile findOne(String filename);

	boolean delete(String filename);
}
