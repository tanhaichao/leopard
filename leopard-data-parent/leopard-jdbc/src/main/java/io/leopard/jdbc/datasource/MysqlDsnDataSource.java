package io.leopard.jdbc.datasource;

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
		// logger.info("jdbcUrl:" + url);
		this.url = url;
	}

	@Override
	public void init() throws Exception {
		JdbcUrlInfo jdbcUrlInfo = DataSourceBuilder.parseUrl(url);
		// String jdbcUrl = ProxyDataSource.getJdbcUrl(jdbcUrlInfo.getHost(),
		// jdbcUrlInfo.getPort(), jdbcUrlInfo.getDatabase());

		this.setHost(jdbcUrlInfo.getHost());
		this.setPort(jdbcUrlInfo.getPort());
		this.setDatabase(jdbcUrlInfo.getDatabase());

		super.init();
	}

}
