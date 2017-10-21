package io.leopard.exporter.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import io.leopard.exporter.Exporter;
import io.leopard.exporter.Importer;
import io.leopard.jdbc.Jdbc;
import io.leopard.json.Json;

public class ImporterMysqlImpl implements Importer {

	private Jdbc jdbc;

	private Exporter exporter;

	public ImporterMysqlImpl(Jdbc jdbc, Exporter exporter) {
		this.jdbc = jdbc;
		this.exporter = exporter;
	}

	@Override
	public int execute(Class<?> model, int size) {
		List<?> list = this.execute(model, 0, size);
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public <T> List<T> execute(Class<T> model, int start, int size) {
		List<T> list = this.exporter.export(model, start, size);
		if (list != null) {
			for (T bean : list) {
				this.execute(model, bean);
			}
		}
		return list;
	}

	protected void execute(Class<?> model, Object bean) {
		Json.print(bean, model.getSimpleName());

		String sql = null;
		jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
			}

		});
	}

}
