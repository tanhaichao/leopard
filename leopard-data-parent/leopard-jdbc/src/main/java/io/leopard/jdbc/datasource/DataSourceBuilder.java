package io.leopard.jdbc.datasource;

import java.beans.PropertyVetoException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * DataSource生成器
 * 
 * @author 谭海潮
 *
 */
public class DataSourceBuilder {
	protected static Log logger = LogFactory.getLog(DataSourceBuilder.class);

	private static DataSourceBuilder dataSourceBuilder = new DataSourceBuilder();

	public static DataSourceBuilder getDataSourceBuilder() {
		return dataSourceBuilder;
	}

	public static void setDataSourceBuilder(DataSourceBuilder dataSourceBuilder) {
		DataSourceBuilder.dataSourceBuilder = dataSourceBuilder;
	}

	public static String getJdbcUrl(String host, int port, String database) {
		String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF8";// UTF8,utf8mb4
		return jdbcUrl;
	}

	public static JdbcUrlInfo parseUrl(String url) {
		String regex = "jdbc:mysql://(.*?):([0-9]+)/([a-z0-9A-Z_]+)";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		if (!m.find()) {
			throw new IllegalArgumentException("JdbcUrl[" + url + "]不符合规则.");
		}
		String host = m.group(1);
		int port = Integer.parseInt(m.group(2));
		String database = m.group(3);

		JdbcUrlInfo jdbcUrlInfo = new JdbcUrlInfo();
		jdbcUrlInfo.setDatabase(database);
		jdbcUrlInfo.setPort(port);
		jdbcUrlInfo.setHost(host);
		return jdbcUrlInfo;
	}

	public ProxyDataSource createDataSource(String driverClass, String jdbcUrl, String user, String password, int maxPoolSize, int idleConnectionTestPeriod) {
		if (StringUtils.isEmpty(driverClass)) {
			// System.err.println("驱动程序没有设置.");
			driverClass = "org.gjt.mm.mysql.Driver";
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

		// testConnectionOnCheckout
		// 如果设置为true,每次从池中取一个连接，将做一下测试，使用automaticTestTable 或者 preferredTestQuery,
		// 做一条查询语句.看看连接好不好用，不好用，就关闭它，重新从池中拿一个.
		// idleConnectionTestPeriod
		// 设置在池中的没有被使用的连接，是否定时做测试，看看这个连接还可以用吗？

		// <!--每60秒检查所有连接池中的空闲连接。Default: 0 -->
		// <property name="idleConnectionTestPeriod" value="60" />
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);

		dataSource.setInitialPoolSize(1);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(1);
		dataSource.setAcquireRetryAttempts(1);
		dataSource.setMaxIdleTime(7200);
		dataSource.setMaxStatements(0);
		return new ProxyDataSource(dataSource);
	}

}
