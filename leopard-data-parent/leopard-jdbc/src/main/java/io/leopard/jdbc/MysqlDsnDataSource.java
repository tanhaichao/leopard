package io.leopard.jdbc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * 数据源实现
 * 
 * @author 阿海
 * 
 */
public class MysqlDsnDataSource extends JdbcDataSource {

	private String url;

	public void setUrl(String url) {
		// AssertUtil.assertNotEmpty(url, "参数url不能为空.");
		if (StringUtils.isEmpty(url)) {
			throw new IllegalArgumentException("参数url不能为空.");
		}
		logger.info("jdbcUrl:" + url);
		this.url = url;
	}

	@Override
	public void init() {
		JdbcUrlInfo jdbcUrlInfo = this.parseUrl(url);
		// String jdbcUrl = ProxyDataSource.getJdbcUrl(jdbcUrlInfo.getHost(),
		// jdbcUrlInfo.getPort(), jdbcUrlInfo.getDatabase());

		this.setHost(jdbcUrlInfo.getHost());
		this.setPort(jdbcUrlInfo.getPort());
		this.setDatabase(jdbcUrlInfo.getDatabase());

		super.init();
	}

	protected JdbcUrlInfo parseUrl(String url) {
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

}
