package io.leopard.exporter;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import io.leopard.burrow.util.NumberUtil;
import io.leopard.burrow.util.StringUtil;
import io.leopard.exporter.annotation.Column;
import io.leopard.exporter.exception.IdTransformException;
import io.leopard.json.Json;
import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;

public class ImporterBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

	protected static FieldResolver fieldResolver = FieldResolverImpl.getInstance();

	protected List<?> list;

	protected Field[] fields;

	protected IdTransverter idTransverter;

	public ImporterBatchPreparedStatementSetter(List<?> list, Class<?> model, IdTransverter idTransverter) {
		this.list = list;
		this.fields = model.getDeclaredFields();
		this.idTransverter = idTransverter;
	}

	protected FieldType getFieldType(Field field, int index) {
		if (index == 0) {
			Class<?> type = field.getType();
			if (type.equals(String.class)) {
				return FieldType.STRING;
			}
			else if (type.equals(long.class)) {
				return FieldType.LONG;
			}
			else {
				throw new RuntimeException("未知主键数据类型[" + type.getSimpleName() + "].");
			}
		}
		Column column = field.getAnnotation(Column.class);
		if (column == null || StringUtils.isEmpty(column.alias())) {
			return null;
		}
		FieldType type = fieldResolver.resolveType(field);
		return type;
	}

	protected Object getValue(Field field, Object bean, int index) {
		if (index == 0) {
			Class<?> type = field.getType();
			if (type.equals(String.class)) {
				return StringUtil.uuid();
			}
			else if (type.equals(long.class)) {
				return (long) NumberUtil.random(10000000);
				// return System.currentTimeMillis();
			}
			else {
				throw new RuntimeException("未知主键数据类型[" + type.getSimpleName() + "].");
			}
		}

		try {
			return field.get(bean);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void setValues(PreparedStatement ps, int row) throws SQLException {
		Object bean = list.get(row);

		int parameterIndex = 0;
		int i = -1;
		for (Field field : fields) {
			i++;
			FieldType type = this.getFieldType(field, i);
			if (type == null) {
				continue;
			}
			// System.err.println("setValues:" + field.getName() + " parameterIndex:" + parameterIndex);
			Object value = this.getValue(field, bean, i);
			if (value != null && fieldResolver != null) {
				value = idTransform(field, value);
			}
			parameterIndex++;
			if (type == FieldType.STRING) {
				ps.setString(parameterIndex, (String) value);
			}
			else if (type == FieldType.JSON) {
				ps.setString(parameterIndex, Json.toJson(value));
			}
			else if (type == FieldType.BOOLEAN) {
				ps.setBoolean(parameterIndex, (boolean) value);
			}
			else if (type == FieldType.INTEGER) {
				ps.setInt(parameterIndex, (int) value);
			}
			else if (type == FieldType.LONG) {
				ps.setLong(parameterIndex, (long) value);
			}
			else if (type == FieldType.FLOAT) {
				ps.setFloat(parameterIndex, (float) value);
			}
			else if (type == FieldType.DOUBLE) {
				ps.setDouble(parameterIndex, (double) value);
			}
			else if (type == FieldType.DATE) {
				if (value == null) {
					ps.setTimestamp(parameterIndex, null);
				}
				else {
					long time = ((java.util.Date) value).getTime();
					ps.setTimestamp(parameterIndex, new Timestamp(time));
				}
			}
			else if (type == FieldType.TIMESTAMP) {
				ps.setTimestamp(parameterIndex, (Timestamp) value);
			}
			else if (type == FieldType.ONLY_DATE) {
				long time = ((OnlyDate) value).getTime();
				ps.setDate(parameterIndex, new java.sql.Date(time));
			}
			else if (type == FieldType.MONTH) {
				ps.setString(parameterIndex, ((Month) value).toString());
			}
			else {
				throw new IllegalArgumentException("未知类型[" + type + "].");
			}
		}

	}

	/**
	 * ID转换
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	protected Object idTransform(Field field, Object value) {
		if (value == null) {
			return value;
		}
		if (!(value instanceof String || value instanceof Integer || value instanceof Long)) {
			return value;
		}

		String idTransformTableName = fieldResolver.getIdTransformTableName(field);
		if (idTransformTableName == null) {
			return value;
		}
		if (value instanceof String) {
			String id = (String) value;
			if (id.length() == 0) {
				return id;
			}

			String newId = idTransform(idTransformTableName, id);
			if (newId == null) {
				return value;
			}
			return newId;
		}
		else if (value instanceof Integer) {
			int id = (int) value;
			if (id <= 0) {
				return id;
			}
			String newId = idTransform(idTransformTableName, Integer.toString(id));
			if (newId == null) {
				return value;
			}
			return Integer.parseInt(newId);
		}
		else if (value instanceof Long) {
			long id = (long) value;
			if (id <= 0) {
				return id;
			}
			String newId = idTransform(idTransformTableName, Long.toString(id));
			if (newId == null) {
				return value;
			}
			return Long.parseLong(newId);
		}
		else {
			throw new RuntimeException("未知ID类型[" + value.getClass().getSimpleName() + "].");
		}
	}

	protected String idTransform(String tableName, String id) {
		String newId = idTransverter.transform(tableName, id);
		if (StringUtils.isEmpty(newId)) {
			newId = idTransverter.generateNewId(tableName, newId);
			System.err.println("newId:" + newId);
			if (newId == null) {
				throw new IdTransformException(tableName, newId);
			}
			idTransverter.add(tableName, id, newId);
		}
		return newId;
	}

	@Override
	public int getBatchSize() {
		return list.size();
	}

}
