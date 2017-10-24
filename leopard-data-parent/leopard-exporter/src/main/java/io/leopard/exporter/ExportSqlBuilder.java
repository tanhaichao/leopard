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
			String columnName = getColumnName(field);
			if (StringUtils.isEmpty(columnName)) {
				continue;
			}
			if (index > 0) {
				sb.append(", ");
			}
			String fieldName = getFieldName(field);
			sb.append(esc).append(columnName).append(esc);
			sb.append(" as ").append(esc).append(fieldName).append(esc);
			index++;
		}
		if (index <= 0) {
			throw new RuntimeException("表[" + tableName + "]没有任何字段配置了别名");
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
		Column column = field.getAnnotation(Column.class);
		// System.err.println("field:" + field.getName() + " column:" + column);
		if (column == null) {
			// return field.getName();
			return null;
		}
		String columnName = column.alias();
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
