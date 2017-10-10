package io.leopard.jdbc.oracle;

import java.util.List;

import io.leopard.jdbc.oracle.model.Column;
import io.leopard.jdbc.oracle.model.UserTable;
import io.leopard.jdbc.oracle.model.UserTableComment;

public interface OracleManager {

	List<UserTable> listUserTables();

	List<Column> listColumns(String tableName);

	List<UserTableComment> listUserTableComments();

	/**
	 * 获取所有表(包含注释)
	 * 
	 * @return
	 */
	List<UserTable> listUserTables2();

}
