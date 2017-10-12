package io.leopard.exporter.mysql;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import io.leopard.jdbc.oracle.model.Column;
import io.leopard.jdbc.oracle.model.UserTable;

public interface MysqlManager {
	/**
	 * 获取所有表(包含注释)
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<UserTable> listUserTables() throws SQLException;

	Map<String, List<Column>> listColumns() throws SQLException;

	List<Column> listColumns(String tableName) throws SQLException;
}
