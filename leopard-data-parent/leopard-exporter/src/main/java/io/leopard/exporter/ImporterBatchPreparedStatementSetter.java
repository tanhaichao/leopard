package io.leopard.exporter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class ImporterBatchPreparedStatementSetter<T> implements BatchPreparedStatementSetter {

	private List<T> list;

	private Class<T> model;

	public ImporterBatchPreparedStatementSetter(List<T> list, Class<T> model) {
		this.list = list;
		this.model = model;
	}

	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {
		ps.setString(i, value);
	}

	@Override
	public int getBatchSize() {
		return list.size();
	}

}
