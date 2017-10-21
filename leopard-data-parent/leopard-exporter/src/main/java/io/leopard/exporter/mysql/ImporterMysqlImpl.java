package io.leopard.exporter.mysql;

import java.util.List;

import io.leopard.exporter.Exporter;
import io.leopard.exporter.IdTransverter;
import io.leopard.exporter.ImportSqlBuilder;
import io.leopard.exporter.Importer;
import io.leopard.exporter.ImporterBatchPreparedStatementSetter;
import io.leopard.jdbc.Jdbc;

public class ImporterMysqlImpl implements Importer {

	private Jdbc jdbc;

	private Exporter exporter;

	private IdTransverter idTransverter;

	public ImporterMysqlImpl(Jdbc jdbc, Exporter exporter) {
		this.jdbc = jdbc;
		this.exporter = exporter;
	}

	@Override
	public void setIdTransverter(IdTransverter idTransverter) {
		this.idTransverter = idTransverter;
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
		if (list == null || list.isEmpty()) {
			return null;
		}
		String sql = new ImportSqlBuilder(model, ImportSqlBuilder.ESC_MYSQL).buildSql();
		System.out.println("sql:" + sql);
		jdbc.batchUpdate(sql, new ImporterBatchPreparedStatementSetter(list, model, idTransverter));
		return list;
	}

}
