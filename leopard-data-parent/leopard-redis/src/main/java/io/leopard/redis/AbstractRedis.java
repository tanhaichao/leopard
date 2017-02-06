package io.leopard.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractRedis {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected int maxActive;
	protected int timeout;
	protected int initialPoolSize;// 默认初始化连接数量

	protected boolean enableBackup;
	protected String backupTime;

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public void setEnableBackup(boolean enableBackup) {
		this.enableBackup = enableBackup;
	}

	public void setBackupTime(String backupTime) {
		this.backupTime = backupTime;
	}

}
