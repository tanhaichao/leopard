package io.leopard.exporter.mysql.oracle;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import io.leopard.exporter.ExportSqlBuilder;
import io.leopard.exporter.Exporter;
import io.leopard.exporter.ExporterLeopardBeanPropertyRowMapper;
import io.leopard.jdbc.Jdbc;

public class ExporterOracleImpl implements Exporter {

	private Jdbc jdbc;

	public ExporterOracleImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public <T> List<T> export(Class<T> model, int start, int size) {
		ExportSqlBuilder builder = new ExportSqlBuilder(model, ExportSqlBuilder.ESC_ORACLE);
		String tableName = builder.getTableName();
		String sql = builder.buildSql();
		System.out.println(sql);
		// return jdbc.queryForList(sql, model);
		try {
			return jdbc.getJdbcTemplate().query(sql, new ExporterLeopardBeanPropertyRowMapper<T>(model, tableName));
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Jdbc getJdbc() {
		return jdbc;
	}

}
