package io.leopard.jdbc.builder;

public class WhereBuilder extends AbstractSqlBuilder implements SqlBuilder {

	@Override
	/**
	 * 拼接where查询条件.
	 */
	public String getSql() {
		if (fieldList.isEmpty()) {
			throw new NullPointerException("还没有设置任何参数.");
		}
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		for (String filedName : fieldList) {
			if (count > 0) {
				sb.append(" and ");
			}
			sb.append(filedName).append("=?");
			count++;
		}
		return sb.toString();
	}

}
