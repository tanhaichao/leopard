package io.leopard.jdbc.oracle;

import java.beans.PropertyVetoException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import io.leopard.jdbc.ProxyDataSource;

public class OracleProxyDataSource extends ProxyDataSource {

	public OracleProxyDataSource(ComboPooledDataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected String parseHost(String jdbcUrl) {
		// String jdbcUrl = "jdbc:oracle:thin:@" + host + ":1521:" + database + "?useUnicode=true&characterEncoding=UTF8";

		String regex = "jdbc:oracle:thin:@(.*?):";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(jdbcUrl);
		if (m.find()) {
			return m.group(1);
		}
		throw new RuntimeException("解析host出错[" + jdbcUrl + "].");
	}

	public static ProxyDataSource createDataSource(String driverClass, String jdbcUrl, String user, String password, int maxPoolSize) {
		if (StringUtils.isEmpty(driverClass)) {
			// System.err.println("驱动程序没有设置.");
			driverClass = "oracle.jdbc.driver.OracleDriver";
		}

		logger.info("createDataSource jdbcUrl:" + jdbcUrl);
		// ComboPooledDataSource dataSource = new ComboPooledDataSource();
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			// dataSource.setDriverClass("org.mariadb.jdbc.Driver");
			// dataSource.setDriverClass("org.gjt.mm.mysql.Driver");
			dataSource.setDriverClass(driverClass);
		}
		catch (PropertyVetoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setTestConnectionOnCheckout(false);
		dataSource.setInitialPoolSize(1);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(1);
		dataSource.setAcquireRetryAttempts(1);
		dataSource.setMaxIdleTime(7200);
		dataSource.setMaxStatements(0);
		return new OracleProxyDataSource(dataSource);
	}
}
