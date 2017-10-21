package io.leopard.exporter;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import io.leopard.json.Json;
import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;

public class ImporterBatchPreparedStatementSetter<T> implements BatchPreparedStatementSetter {

	private static FieldTypeResolver fieldTypeResolver = new FieldTypeResolverImpl();

	private List<T> list;

	private Field[] fields;

	public ImporterBatchPreparedStatementSetter(List<T> list, Class<T> model) {
		this.list = list;
		this.fields = model.getDeclaredFields();
	}

	@Override
	public void setValues(PreparedStatement ps, int row) throws SQLException {
		T bean = list.get(row);

		int parameterIndex = 0;
		for (Field field : fields) {
			FieldType type = fieldTypeResolver.resolveType(field);
			Object value;
			try {
				value = field.get(bean);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			parameterIndex++;
			if (type == FieldType.STRING) {
				ps.setString(parameterIndex, (String) value);
			}
			else if (type == FieldType.JSON) {
				ps.setString(parameterIndex, Json.toJson(value));
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
				long time = ((java.util.Date) value).getTime();
				ps.setTimestamp(parameterIndex, new Timestamp(time));
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

	@Override
	public int getBatchSize() {
		return list.size();
	}

}
