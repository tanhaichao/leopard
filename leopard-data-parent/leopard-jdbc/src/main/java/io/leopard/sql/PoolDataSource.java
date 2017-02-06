package io.leopard.sql;

import javax.sql.DataSource;

public interface PoolDataSource extends DataSource {

	void setJdbcUrl(String jdbcUrl);

	void setMaxIdleTime(int maxIdleTime);

	void setMaxPoolSize(int maxPoolSize);

	void setUser(String user);

	void setPassword(String password);

	void setTestConnectionOnCheckout(boolean testConnectionOnCheckout);

	void setInitialPoolSize(int initialPoolSize);

	void setMinPoolSize(int minPoolSize);

	void setAcquireIncrement(int acquireIncrement);

	void setAcquireRetryAttempts(int acquireRetryAttempts);

	void setMaxStatements(int maxStatements);
}
