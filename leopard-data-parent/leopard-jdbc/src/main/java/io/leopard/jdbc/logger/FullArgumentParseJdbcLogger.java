package io.leopard.jdbc.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 短参数值输出
 * 
 * @author 谭海潮
 *
 */
public class FullArgumentParseJdbcLogger implements JdbcLogger {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void update(int updatedCount, String sql, Object[] args) {
		logger.info("update updatedCount:" + updatedCount + " sql:" + sql);
	}

}
