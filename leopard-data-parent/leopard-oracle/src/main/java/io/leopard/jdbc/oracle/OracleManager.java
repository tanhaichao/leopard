package io.leopard.jdbc.oracle;

import java.util.List;
import java.util.Map;

import io.leopard.jdbc.oracle.model.Column;
import io.leopard.jdbc.oracle.model.UserTable;

public interface OracleManager {
	/**
	 * 获取所有表(包含注释)
	 * 
	 * @return
	 */
	List<UserTable> listUserTables();

	Map<String, List<Column>> listColumns();

	List<Column> listColumns(String tableName);

}
