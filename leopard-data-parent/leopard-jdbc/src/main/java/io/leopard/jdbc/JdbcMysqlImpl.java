package io.leopard.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.ReplaceBuilder;
import io.leopard.jdbc.builder.SqlBuilder;
import io.leopard.lang.Paging;
import io.leopard.lang.PagingImpl;

/**
 * Jdbc接口MySQL实现.
 * 
 * @author 阿海
 * 
 */
public class JdbcMysqlImpl implements Jdbc {

	protected JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	}

	@Override
	public DataSource getDataSource() {
		return jdbcTemplate.getDataSource();
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public String printSQL(Log logger, String sql, StatementParameter param) {
		String sql1 = this.getSQL(sql, param);
		logger.info(sql1);
		return sql1;
	}

	@Override
	public String getSQL(String sql, StatementParameter param) {
		return SqlUtil.getSQL(sql, param);
	}

	@Override
	public int[] batchUpdate(String[] sqls) {
		return this.getJdbcTemplate().batchUpdate(sqls);
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter setter) {
		return this.getJdbcTemplate().batchUpdate(sql, setter);
	}

	@Override
	public <T> T query(String sql, Class<T> elementType) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, new LeopardBeanPropertyRowMapper<T>(elementType));
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public <T> T query(String sql, Class<T> elementType, Object... params) {
		return this.query(sql, elementType, toStatementParameter(sql, params));
	}

	@Override
	public <T> T query(String sql, Class<T> elementType, StatementParameter param) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, param.getArgs(), new LeopardBeanPropertyRowMapper<T>(elementType));
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> queryForMaps(String sql) {
		try {
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> queryForMaps(String sql, Object... params) {
		try {
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql, params);
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	protected String appendLimitSql(String sql, int start, int size) {
		if (sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		return sql + " LIMIT " + start + "," + size + ";";
	}

	@Override

	public <T> List<T> queryForList(String sql, Class<T> elementType) {
		try {
			List<T> list = this.getJdbcTemplate().query(sql, new LeopardBeanPropertyRowMapper<T>(elementType));
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Long> queryForLongs(String sql, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForLongs(sql, param);
	}

	@Override
	public List<Long> queryForLongs(String sql, StatementParameter param) {
		List<Long> list = jdbcTemplate.query(sql, param.getArgs(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int index) {
				try {
					return rs.getLong(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public List<Integer> queryForInts(String sql, StatementParameter param) {
		List<Integer> list = jdbcTemplate.query(sql, param.getArgs(), new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int index) {
				try {
					return rs.getInt(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public List<String> queryForStrings(String sql) {
		List<String> list = jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int index) {
				try {
					return rs.getString(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public List<String> queryForStrings(String sql, StatementParameter param) {
		List<String> list = jdbcTemplate.query(sql, param.getArgs(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int index) {
				try {
					return rs.getString(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForList(sql, elementType, param);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) {
		return this.queryForList(sql, elementType, toStatementParameter(sql, params));
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param) {
		try {
			List<T> list = this.getJdbcTemplate().query(sql, param.getArgs(), new LeopardBeanPropertyRowMapper<T>(elementType));
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long queryForLong(String sql) {
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, Long.class);
			return (number != null ? number.longValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long queryForLong(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Long.class);
			return (number != null ? number.longValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Integer queryForInt(String sql) {
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, Integer.class);
			return (number != null ? number.intValue() : 0);
			//
			// int result = this.getJdbcTemplate().queryForInt(sql);
			// return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean exist(String sql) {
		return this.queryForInt(sql) > 0;
	}

	@Override
	public boolean exist(String sql, StatementParameter param) {
		return this.queryForInt(sql, param) > 0;
	}

	@Override
	public Integer queryForInt(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Integer.class);
			return (number != null ? number.intValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Float queryForFloat(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Float.class);
			return (number != null ? number.floatValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Double queryForDouble(String sql, Object... params) {
		return this.queryForDouble(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public Double queryForDouble(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Double.class);
			return (number != null ? number.doubleValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public java.util.Date queryForDate(String sql) {
		try {
			java.util.Date result = this.getJdbcTemplate().queryForObject(sql, java.util.Date.class);
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public java.util.Date queryForDate(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			java.util.Date result = this.getJdbcTemplate().queryForObject(sql, args, argTypes, java.util.Date.class);
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public String queryForString(String sql) {
		try {
			String result = this.getJdbcTemplate().queryForObject(sql, String.class);

			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public String queryForString(String sql, Object... params) {
		return this.queryForString(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public String queryForString(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			String result = this.getJdbcTemplate().queryForObject(sql, args, argTypes, String.class);
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean insertIgnoreForBoolean(String sql, StatementParameter param) {
		try {
			return this.insertForBoolean(sql, param);
		}
		catch (DuplicateKeyException e) {
			return false;
		}
	}

	@Override
	public long insertForLastId(final String sql, final StatementParameter param) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				param.setValues(pstmt);
				return pstmt;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public boolean insertForBoolean(String sql, StatementParameter param) {
		return this.updateForBoolean(sql, param);
	}

	@Override
	public boolean updateForBoolean(String sql, StatementParameter param) {
		// System.out.println("updateForBoolean sql:" + sql);
		int updatedCount = this.update(sql, param);
		return (updatedCount > 0);
	}

	/**
	 * 将参数列表转成StatementParameter.
	 * 
	 * @param params 参数列表
	 * @return 转换后的StatementParameter
	 */
	@SuppressWarnings("rawtypes")
	protected StatementParameter toStatementParameter(String sql, Object... params) {
		StatementParameter param = new StatementParameter();
		for (Object p : params) {
			if (p instanceof Integer) {
				param.setInt((Integer) p);
			}
			else if (p instanceof Long) {
				param.setLong((Long) p);
			}
			else if (p instanceof Boolean) {
				param.setBool((Boolean) p);
			}
			else if (p instanceof Float) {
				param.setFloat((Float) p);
			}
			else if (p instanceof Double) {
				param.setDouble((Double) p);
			}
			else if (p instanceof String) {
				param.setString((String) p);
			}
			else if (p instanceof Date) {
				param.setDate((Date) p);
			}

			else if (p instanceof List) {
				param.setList((List) p);
			}
			// 自定义数据类型start
			// else if (p instanceof OnlyDate) {
			// param.setDate(((OnlyDate) p).toDate());
			// }
			// else if (p instanceof Month) {
			// param.setString(((Month) p).toString());
			// }
			else {
				param.setObject(p);
				// throw new IllegalArgumentException("未知数据类型[" + p.getClass().getName() + "].");
			}
		}
		return param;
	}

	@Override
	/**
	 * @see io.leopard.data.jdbc.Jdbc#updateForBoolean(String, Object...)
	 */
	public boolean updateForBoolean(String sql, Object... params) {
		return this.updateForBoolean(sql, toStatementParameter(sql, params));
	}

	@Override
	public int update(String sql, StatementParameter param) {
		return this.getJdbcTemplate().update(sql, param.getParameters());
	}

	@Override
	public int update(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	@Override
	public boolean insertIgnoreForBoolean(InsertBuilder builder) {
		return this.insertIgnoreForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean insertIgnoreForBoolean(ReplaceBuilder builder) {
		return this.insertIgnoreForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean insertForBoolean(InsertBuilder builder) {
		return this.insertForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean insertForBoolean(ReplaceBuilder builder) {
		return this.insertForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean updateForBoolean(SqlBuilder builder) {
		return this.updateForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public int update(SqlBuilder builder) {
		return this.update(builder.getSql(), builder.getParam());
	}

	@Override
	public Long incr(String sql, StatementParameter param) {
		boolean success = this.updateForBoolean(sql, param);
		if (success) {
			return 1L;
		}
		else {
			return 0L;
		}
	}

	@Override
	public List<Integer> queryForInts(String sql, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForInts(sql, param);
	}

	@Override
	public List<String> queryForStrings(String sql, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForStrings(sql);
	}

	@Override
	public List<String> queryForStrings(String sql, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForStrings(sql, param);
	}

	@Override
	public Long queryForLong(String sql, Object... params) {
		return this.queryForLong(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public Integer queryForInt(String sql, Object... params) {
		return this.queryForInt(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public boolean insertForBoolean(String sql, Object... params) {
		return this.insertForBoolean(sql, toStatementParameter(sql, params));
	}

	@Override
	public boolean insertByBean(String sql, Object bean) {
		return this.insertForBoolean(sql, SqlParserUtil.toInsertParameter(sql, bean));
	}

	@Override
	public boolean insert(String tableName, Object bean) {
		InsertBuilder builder = new InsertBuilder(tableName);

		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> type = field.getType();
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(bean);
			}
			catch (IllegalArgumentException e) {
				throw new InvalidDataAccessApiUsageException(e.getMessage());
			}
			catch (IllegalAccessException e) {
				throw new InvalidDataAccessApiUsageException(e.getMessage());
			}
			if (String.class.equals(type)) {
				builder.setString(fieldName, (String) obj);
			}
			else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
				builder.setBool(fieldName, (Boolean) obj);
			}
			else if (int.class.equals(type) || Integer.class.equals(type)) {
				builder.setInt(fieldName, (Integer) obj);
			}
			else if (long.class.equals(type) || Long.class.equals(type)) {
				builder.setLong(fieldName, (Long) obj);
			}
			else if (float.class.equals(type) || Float.class.equals(type)) {
				builder.setFloat(fieldName, (Float) obj);
			}
			else if (double.class.equals(type) || Double.class.equals(type)) {
				builder.setDouble(fieldName, (Double) obj);
			}
			else if (Date.class.equals(type)) {
				builder.setDate(fieldName, (Date) obj);
			}
			else if (List.class.equals(type)) {
				builder.setString(fieldName, obj.toString());
			}
			else {
				throw new InvalidDataAccessApiUsageException("未知数据类型[" + type.getName() + "].");
			}
		}
		return this.insertForBoolean(builder);
	}

	@Override
	public boolean updateByBean(String sql, Object bean) {
		return this.updateForBoolean(sql, SqlParserUtil.toUpdateParameter(sql, bean));
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType) {
		List<T> list = this.queryForList(sql, elementType);
		String countSql = SqlUtil.toCountSql(sql);
		int totalCount = this.queryForInt(countSql);

		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);
		return paging;
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, Object... params) {
		StatementParameter param = toStatementParameter(sql, params);
		List<T> list = this.queryForList(sql, elementType, param);
		CountSqlParser parser = new CountSqlParser(sql, param);

		// System.err.println("countSQL:" + countSqlParser.getCountSql());
		int totalCount = this.queryForInt(parser.getCountSql(), parser.getCountParam());

		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);

		if (parser.getSize() != null) {
			paging.setPageSize(parser.getSize());
		}
		return paging;
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param) {
		List<T> list = this.queryForList(sql, elementType, param);
		CountSqlParser parser = new GroupCountSqlParser(sql, param);
		String countSql = parser.getCountSql();
		// System.err.println("countSql:" + countSql);
		int totalCount = this.queryForInt(countSql, parser.getCountParam());
		// System.err.println("countSql:" + countSql + " totalCount:" + totalCount);

		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);
		if (parser.getSize() != null) {
			paging.setPageSize(parser.getSize());
		}
		return paging;
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		PageableRowMapperResultSetExtractor<T> extractor = new PageableRowMapperResultSetExtractor<T>(new LeopardBeanPropertyRowMapper<T>(elementType), start, size);
		List<T> list = this.getJdbcTemplate().query(sql, param.getArgs(), extractor);
		int totalCount = extractor.getCount();
		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);

		paging.setPageSize(size);

		return paging;
	}

}
