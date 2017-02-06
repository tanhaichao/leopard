package io.leopard.treedata;

import java.io.IOException;
import java.util.List;

public interface TreeDataManager {
	List<Row> load(String path) throws IOException;

	List<Children> parse(String path) throws IOException;
}
