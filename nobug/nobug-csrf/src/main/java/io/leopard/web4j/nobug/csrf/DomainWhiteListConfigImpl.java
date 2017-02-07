package io.leopard.web4j.nobug.csrf;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;

public class DomainWhiteListConfigImpl implements DomainWhiteListConfig {

	protected Log logger = LogFactory.getLog(this.getClass());

	// private Jdbc jdbc;

	private JdbcTemplate jdbcTemplate;

	// public void setJdbc(Jdbc jdbc) {
	//
	// this.jdbc = jdbc;
	// // System.err.println("setJdbc:" + jdbc);
	// }

	@Override
	public List<String> getRefererDomainWhiteList() {
		String sql = "select domain from leopard_domainwhitelist;";
		return jdbcTemplate.queryForList(sql, String.class);
		// List<String> list = jdbc.queryForStrings(sql);
		// return ListUtil.toSet(list);
	}

	@PostConstruct
	public void init() {
		createTable();

		update();

		// TODO ahai 更新域名白名单定时器没有启动.

		// new SimpleTimer(60) {
		// @Override
		// public void start() {
		// update();
		// }
		// }.run();
	}

	protected void update() {
		// System.err.println("update RefererDomainWhiteSet:");
		List<String> set = this.getRefererDomainWhiteList();
		RefererSecurityValidator.setDoaminWhiteList(set);
	}

	protected void createTable() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE  if not exists `leopard_domainwhitelist` (");
		sb.append("`domain` varchar(50) NOT NULL DEFAULT '',");
		sb.append("`username` varchar(50) NOT NULL DEFAULT '',");
		sb.append("`posttime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',");
		sb.append("`comment` varchar(255) NOT NULL DEFAULT '',");
		sb.append("PRIMARY KEY (`domain`)");
		sb.append(");");
		String sql = sb.toString();

		Resource scripts = new ByteArrayResource(sql.getBytes());
		DatabasePopulator populator = new ResourceDatabasePopulator(scripts);
		try {
			DatabasePopulatorUtils.execute(populator, jdbcTemplate.getDataSource());
		}
		catch (ScriptStatementFailedException e) {

		}
	}

}
