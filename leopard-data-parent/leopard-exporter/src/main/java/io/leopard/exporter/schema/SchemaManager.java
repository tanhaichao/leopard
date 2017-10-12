package io.leopard.exporter.schema;

import java.sql.SQLException;
import java.util.List;

public interface SchemaManager {
	/**
	 * 获取所有表(包含注释)
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<Table> listTables() throws SQLException;

	Table get(String tableName);
}
