package io.leopard.jdbc.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.SqlUtil;
import io.leopard.lang.Paging;
import io.leopard.lang.PagingImpl;

public class JoinBuilder {
	private Integer limitStart;
	private Integer limitSize;

	private String sql;
	private String totalSql;
	private String tableName;
	private String fieldName;

	public JoinBuilder(String tableName) {
		this.tableName = tableName;
	}

	public JoinBuilder join(String sql, String fieldName) {
		this.sql = sql;
		this.fieldName = fieldName;
		return this;
	}

	public JoinBuilder total(String sql) {
		this.totalSql = sql;
		return this;
	}

	public JoinBuilder limit(int start, int size) {
		this.limitStart = start;
		this.limitSize = size;
		return this;
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType) {
		PagingImpl<T> paging = new PagingImpl<T>();
		List<String> set = new ArrayList<String>();
		{
			List<Map<String, Object>> list = jdbc.queryForMaps(sql, limitStart, limitSize);
			if (list != null) {
				for (Map<String, Object> map : list) {
					String value = map.get(this.fieldName).toString();
					set.add(value);
				}
			}
			// Json.printList(list, "list");
			int totalCount = jdbc.queryForInt(totalSql);
			paging.setTotalCount(totalCount);
			paging.setPageSize(limitSize);
		}
		if (!set.isEmpty()) {
			String sql = "select * from " + tableName + " where " + this.fieldName + " in (" + SqlUtil.toIn(set) + ")";
			List<T> list = jdbc.queryForList(sql, elementType);
			paging.setList(list);
		}

		return paging;
	}

}
