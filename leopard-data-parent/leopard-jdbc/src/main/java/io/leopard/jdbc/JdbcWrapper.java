package io.leopard.jdbc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.ReplaceBuilder;
import io.leopard.jdbc.builder.SqlBuilder;
import io.leopard.lang.Paging;

public class JdbcWrapper implements Jdbc {

	private Jdbc jdbc;

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public DataSource getDataSource() {
		return getJdbc().getDataSource();
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return getJdbc().getJdbcTemplate();
	}

	@Override
	public String printSQL(Log logger, String sql, StatementParameter param) {
		return getJdbc().printSQL(logger, sql, param);
	}

	@Override
	public String getSQL(String sql, StatementParameter param) {
		return getJdbc().getSQL(sql, param);
	}

	@Override
	public boolean exist(String sql) {
		return getJdbc().exist(sql);
	}

	@Override
	public boolean exist(String sql, StatementParameter param) {
		return getJdbc().exist(sql, param);
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter setter) {
		return getJdbc().batchUpdate(sql, setter);
	}

	@Override
	public <T> T query(String sql, Class<T> elementType) {
		return getJdbc().query(sql, elementType);
	}

	@Override
	public <T> T query(String sql, Class<T> elementType, StatementParameter param) {
		return getJdbc().query(sql, elementType, param);
	}

	@Override
	public <T> T query(String sql, Class<T> elementType, Object... params) {
		return getJdbc().query(sql, elementType, params);
	}

	@Override
	public List<Map<String, Object>> queryForMaps(String sql) {
		return this.getJdbc().queryForMaps(sql);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType) {
		return this.getJdbc().queryForList(sql, elementType);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) {
		return this.getJdbc().queryForList(sql, elementType, params);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param) {
		return this.getJdbc().queryForList(sql, elementType, param);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		return this.getJdbc().queryForList(sql, elementType, param, start, size);
	}

	@Override
	public List<Long> queryForLongs(String sql, StatementParameter param) {
		return this.getJdbc().queryForLongs(sql, param);
	}

	@Override
	public List<Long> queryForLongs(String sql, StatementParameter param, int start, int size) {
		return this.getJdbc().queryForLongs(sql, param, start, size);
	}

	@Override
	public List<Integer> queryForInts(String sql, StatementParameter param) {
		return this.getJdbc().queryForInts(sql, param);
	}

	@Override
	public List<Integer> queryForInts(String sql, StatementParameter param, int start, int size) {
		return this.getJdbc().queryForInts(sql, param, start, size);
	}

	@Override
	public List<String> queryForStrings(String sql) {
		return this.getJdbc().queryForStrings(sql);
	}

	@Override
	public List<String> queryForStrings(String sql, int start, int size) {
		return this.getJdbc().queryForStrings(sql, start, size);
	}

	@Override
	public List<String> queryForStrings(String sql, StatementParameter param) {
		return this.getJdbc().queryForStrings(sql, param);
	}

	@Override
	public List<String> queryForStrings(String sql, StatementParameter param, int start, int size) {
		return this.getJdbc().queryForStrings(sql, param, start, size);
	}

	@Override
	public Long queryForLong(String sql) {
		return this.getJdbc().queryForLong(sql);
	}

	@Override
	public Double queryForDouble(String sql, StatementParameter param) {
		return this.getJdbc().queryForDouble(sql, param);
	}

	@Override
	public Float queryForFloat(String sql, StatementParameter param) {
		return this.getJdbc().queryForFloat(sql, param);
	}

	@Override
	public Long queryForLong(String sql, StatementParameter param) {
		return this.getJdbc().queryForLong(sql, param);
	}

	@Override
	public Long queryForLong(String sql, Object... params) {
		return this.getJdbc().queryForLong(sql, params);
	}

	@Override
	public Integer queryForInt(String sql) {
		return this.getJdbc().queryForInt(sql);
	}

	@Override
	public Integer queryForInt(String sql, StatementParameter param) {
		return this.getJdbc().queryForInt(sql, param);
	}

	@Override
	public Integer queryForInt(String sql, Object... params) {
		return this.getJdbc().queryForInt(sql, params);
	}

	@Override
	public Date queryForDate(String sql) {
		return this.getJdbc().queryForDate(sql);
	}

	@Override
	public Date queryForDate(String sql, StatementParameter param) {
		return this.getJdbc().queryForDate(sql, param);
	}

	@Override
	public String queryForString(String sql) {
		return this.getJdbc().queryForString(sql);
	}

	@Override
	public String queryForString(String sql, Object... params) {
		return this.getJdbc().queryForString(sql, params);
	}

	@Override
	public String queryForString(String sql, StatementParameter param) {
		return this.getJdbc().queryForString(sql, param);
	}

	@Override
	public boolean insertIgnoreForBoolean(InsertBuilder builder) {
		return this.getJdbc().insertForBoolean(builder);
	}

	@Override
	public boolean insertIgnoreForBoolean(ReplaceBuilder builder) {
		return this.getJdbc().insertForBoolean(builder);
	}

	@Override
	public boolean insertIgnoreForBoolean(String sql, StatementParameter param) {
		return this.getJdbc().insertIgnoreForBoolean(sql, param);
	}

	@Override
	public boolean insertForBoolean(String sql, StatementParameter param) {
		return this.getJdbc().insertForBoolean(sql, param);
	}

	@Override
	public boolean insertForBoolean(String sql, Object... params) {
		return this.getJdbc().insertForBoolean(sql, params);
	}

	@Override
	public Long incr(String sql, StatementParameter param) {
		return this.getJdbc().incr(sql, param);
	}

	@Override
	public boolean insertForBoolean(InsertBuilder builder) {
		return this.getJdbc().insertForBoolean(builder);
	}

	@Override
	public boolean insertForBoolean(ReplaceBuilder builder) {
		return this.getJdbc().insertForBoolean(builder);
	}

	@Override
	public boolean updateForBoolean(String sql, Object... params) {
		return this.getJdbc().updateForBoolean(sql, params);
	}

	@Override
	public boolean updateForBoolean(String sql, StatementParameter param) {
		return this.getJdbc().updateForBoolean(sql, param);
	}

	@Override
	public boolean updateForBoolean(SqlBuilder builder) {
		return this.getJdbc().updateForBoolean(builder);
	}

	@Override
	public int update(SqlBuilder builder) {
		return this.getJdbc().update(builder);
	}

	@Override
	public int update(String sql, StatementParameter param) {
		return this.getJdbc().update(sql, param);
	}

	@Override
	public int update(String sql) {
		return this.getJdbc().update(sql);
	}

	@Override
	public long insertForLastId(String sql, StatementParameter param) {
		return this.getJdbc().insertForLastId(sql, param);
	}

	@Override
	public int[] batchUpdate(String[] sqls) {
		return this.getJdbc().batchUpdate(sqls);
	}

	@Override
	public boolean insert(String tableName, Object bean) {
		return this.getJdbc().insert(tableName, bean);
	}

	@Override
	public boolean insertByBean(String sql, Object bean) {
		return this.getJdbc().insertByBean(sql, bean);
	}

	@Override
	public boolean updateByBean(String sql, Object bean) {
		return this.getJdbc().updateByBean(sql, bean);
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType) {
		return this.getJdbc().queryForPaging(sql, elementType);
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, Object... params) {
		return this.getJdbc().queryForPaging(sql, elementType, params);
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param) {
		return this.getJdbc().queryForPaging(sql, elementType, param);
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		return this.getJdbc().queryForPaging(sql, elementType, param, start, size);
	}

	@Override
	public List<Map<String, Object>> queryForMaps(String sql, Object... params) {
		return this.getJdbc().queryForMaps(sql, params);
	}

	@Override
	public Double queryForDouble(String sql, Object... params) {
		return this.getJdbc().queryForDouble(sql, params);
	}

}
