package io.leopard.jdbc.builder;

import org.springframework.util.StringUtils;

/**
 * insert语句生成器.
 * 
 * @author 阿海
 * 
 */
public class InsertBuilder extends AbstractSqlBuilder implements SqlBuilder {
	private String tableName;

	private boolean insertIgnore;

	/**
	 * 
	 * @param tableName
	 *            表名称.
	 */
	public InsertBuilder(String tableName) {
		this(tableName, false);
	}

	/**
	 * 
	 * @param tableName
	 *            表名称.
	 * @param insertIgnore
	 *            是否使用insert ignore into
	 */
	public InsertBuilder(String tableName, boolean insertIgnore) {
		// AssertUtil.assertNotEmpty(tableName, "参数tableName不能为空.");
		if (StringUtils.isEmpty(tableName)) {
			throw new IllegalArgumentException("参数tableName不能为空.");
		}
		this.tableName = tableName;
		this.insertIgnore = insertIgnore;
	}

	@Override
	public String getSql() {
		if (fieldList.isEmpty()) {
			throw new NullPointerException("还没有设置任何参数.");
		}

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();

		for (String fieldName : fieldList) {
			if (fields.length() > 0) {
				fields.append(", ");
				values.append(", ");
			}
			fields.append(fieldName);
			values.append("?");
		}

		// System.out.println("fields:" + fields.toString());

		StringBuilder sb = new StringBuilder("INSERT ");
		if (insertIgnore) {
			sb.append("IGNORE ");
		}
		sb.append("INTO ");
		sb.append(tableName).append("(");
		sb.append(fields.toString()).append(") values(");
		sb.append(values.toString()).append(");");

		return sb.toString();
	}
}
