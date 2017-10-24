package io.leopard.exporter;

import java.lang.reflect.Field;

import org.springframework.util.StringUtils;

import io.leopard.burrow.util.StringUtil;
import io.leopard.exporter.annotation.Column;
import io.leopard.exporter.annotation.Table;

/**
 * 导出SQL生成器
 * 
 * @author 谭海潮
 *
 */
public class ExportSqlBuilder {

	public static final char ESC_ORACLE = '"';

	public static final char ESC_MYSQL = '`';

	private char esc;

	private Class<?> model;

	public ExportSqlBuilder(Class<?> model, char esc) {
		this.model = model;
		this.esc = esc;
	}

	public String buildSql() {
		String tableName = this.getTableName();
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		int index = 0;
		for (Field field : model.getDeclaredFields()) {
			if (index > 0) {
				sb.append(", ");
			}
			String columnName = getColumnName(field);
			String fieldName = getFieldName(field);
			sb.append(esc).append(columnName).append(esc);
			sb.append(" as ").append(esc).append(fieldName).append(esc);
			index++;
		}
		sb.append(" from ").append(esc).append(tableName).append(esc);
		return sb.toString();
	}

	/**
	 * 获取列名
	 * 
	 * @param field
	 * @return
	 */
	public String getColumnName(Field field) {
		Column column = model.getAnnotation(Column.class);
		if (column == null) {
			return field.getName();
		}
		String columnName = column.alias();
		if (StringUtils.isEmpty(columnName)) {
			columnName = field.getName();
		}
		return columnName;
	}

	/**
	 * 获取属性名称
	 * 
	 * @param field
	 * @return
	 */
	public String getFieldName(Field field) {
		return field.getName();
	}

	/**
	 * 获取表名称
	 * 
	 * @return
	 */
	public String getTableName() {
		Table table = model.getAnnotation(Table.class);
		if (table == null) {
			throw new NullPointerException("模型[" + model.getName() + "]没有@Table.");
		}
		String tableName = table.oldTable();
		if (StringUtils.isEmpty(tableName)) {
			tableName = StringUtil.camelToUnderline(model.getName());
		}
		return tableName;
	}

}
