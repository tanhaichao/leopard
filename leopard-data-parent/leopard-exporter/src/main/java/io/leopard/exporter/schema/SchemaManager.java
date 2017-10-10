package io.leopard.exporter.schema;

import java.util.List;

public interface SchemaManager {
	/**
	 * 获取所有表(包含注释)
	 * 
	 * @return
	 */
	List<Table> listTables();

	Table get(String tableName);
}
