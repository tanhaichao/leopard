package io.leopard.exporter.mysql;

import java.util.List;
import java.util.Map;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.model.Column;
import io.leopard.jdbc.oracle.model.UserTable;

public class MysqlManagerImpl implements MysqlManager {
	private Jdbc jdbc;

	public MysqlManagerImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<UserTable> listUserTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Column>> listColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> listColumns(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
