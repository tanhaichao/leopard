package io.leopard.sysconfig;

import java.util.List;
import java.util.Map;

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

	public SysconfigDaoJdbcImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	protected Map<String, String> loadData() {
		String sql = "select * from " + tableName;
		List<Sysconfig> list = jdbc.queryForList(sql, Sysconfig.class);
		return null;
	}

	@Override
	public Object resolve(String key, Class<?> type) {
		logger.info("resolve:" + key + " type:" + type.getName());
		return null;
	}

}
