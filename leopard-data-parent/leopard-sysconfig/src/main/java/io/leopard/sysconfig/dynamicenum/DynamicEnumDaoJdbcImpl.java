package io.leopard.sysconfig.dynamicenum;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.jdbc.Jdbc;
import io.leopard.json.Json;
import io.leopard.lang.inum.daynamic.EnumConstant;

public class DynamicEnumDaoJdbcImpl implements DynamicEnumDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	private Jdbc jdbc;

	/**
	 * 表名
	 */
	private String tableName = "dynamic_enum";

	private Map<String, DynamicEnumRecord> data = new ConcurrentHashMap<>();

	public DynamicEnumDaoJdbcImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
		loadData();
	}

	@Override
	public void loadData() {
		String sql = "select * from " + tableName;
		List<DynamicEnumRecord> list = jdbc.queryForList(sql, DynamicEnumRecord.class);
		data.clear();
		for (DynamicEnumRecord record : list) {
			String enumId = record.getEnumId();
			data.put(enumId, record);
		}
	}

	@Override
	public List<EnumConstant> resolve(String enumId, Class<?> type) {
		// logger.info("resolve:" + key + " type:" + type.getName());
		DynamicEnumRecord record = data.get(enumId);
		if (record == null) {
			return null;
		}
		String json = record.getData();
		List<Map> list = Json.toListObject(json, Map.class);
		System.err.println("list:" + list);
		return null;
		//
		// if (type.equals(String.class)) {
		// return value;
		// }
		// else if (type.equals(int.class) || type.equals(Integer.class)) {
		// return Integer.parseInt(value);
		// }
		// else if (type.equals(long.class) || type.equals(Long.class)) {
		// return Long.parseLong(value);
		// }
		// else if (type.equals(float.class) || type.equals(Float.class)) {
		// return Float.parseFloat(value);
		// }
		// else if (type.equals(double.class) || type.equals(Double.class)) {
		// return Double.parseDouble(value);
		// }
		// else {
		// throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
		// }
	}

}
