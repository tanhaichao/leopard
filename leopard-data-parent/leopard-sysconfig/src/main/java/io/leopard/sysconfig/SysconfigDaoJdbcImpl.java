package io.leopard.sysconfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.jdbc.Jdbc;

public class SysconfigDaoJdbcImpl implements SysconfigDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	private Jdbc jdbc;

	/**
	 * 表名
	 */
	private String tableName = "sysparam";

	private Map<String, String> data;

	public SysconfigDaoJdbcImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
		loadData();
	}

	@Override
	public Map<String, String> loadData() {
		String sql = "select * from " + tableName;
		List<Sysconfig> list = jdbc.queryForList(sql, Sysconfig.class);
		Map<String, String> data = new ConcurrentHashMap<>();
		for (Sysconfig sysconfig : list) {
			data.put(sysconfig.getSysparamId(), sysconfig.getValue());
		}
		this.data = data;
		return data;
	}

	@Override
	public Object resolve(String key, Class<?> type) {
		// logger.info("resolve:" + key + " type:" + type.getName());
		String value = data.get(key);
		if (value == null) {
			return null;
		}

		if (type.equals(String.class)) {
			return value;
		}
		else if (type.equals(int.class) || type.equals(Integer.class)) {
			return Integer.parseInt(value);
		}
		else if (type.equals(long.class) || type.equals(Long.class)) {
			return Long.parseLong(value);
		}
		else if (type.equals(float.class) || type.equals(Float.class)) {
			return Float.parseFloat(value);
		}
		else if (type.equals(double.class) || type.equals(Double.class)) {
			return Double.parseDouble(value);
		}
		else {
			throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
		}
	}

}
