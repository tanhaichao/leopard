package io.leopard.sysconfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysconfigDaoJdbcImpl implements SysconfigDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Object resolve(String key) {
		logger.info("resolve:" + key);
		return null;
	}

}
