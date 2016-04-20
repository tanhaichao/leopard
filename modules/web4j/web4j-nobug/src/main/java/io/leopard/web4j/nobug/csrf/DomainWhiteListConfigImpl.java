package io.leopard.web4j.nobug.csrf;

import io.leopard.burrow.timer.SimpleTimer;
import io.leopard.burrow.util.ListUtil;
import io.leopard.jdbc.CreateTableUtil;
import io.leopard.jdbc.CreateTableUtil.GetSql;
import io.leopard.jdbc.Jdbc;
import io.leopard.web4j.nobug.DomainWhiteListConfig;
import io.leopard.web4j.nobug.RefererSecurityValidator;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DomainWhiteListConfigImpl implements DomainWhiteListConfig {

	protected Log logger = LogFactory.getLog(this.getClass());

	// private static String jdbcName = "jdbc";
	//
	// public static void setJdbcName(String jdbcName) {
	// DoaminWhiteListConfigImpl.jdbcName = jdbcName;
	// }
	//
	// public static String getJdbcName() {
	// return jdbcName;
	// }

	private Jdbc jdbc;

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
		// System.err.println("setJdbc:" + jdbc);
	}

	@Override
	public Set<String> getRefererDomainWhiteSet() {
		String sql = "select domain from leopard_domainwhitelist;";
		List<String> list = jdbc.queryForStrings(sql);
		return ListUtil.toSet(list);
	}

	@PostConstruct
	public void init() {
		createTable();

		new SimpleTimer(60) {
			@Override
			public void start() {
				update();
			}
		}.run();
	}

	protected void update() {
		// System.err.println("update RefererDomainWhiteSet:");
		Set<String> set = this.getRefererDomainWhiteSet();
		RefererSecurityValidator.setDoaminWhiteList(set);
	}

	protected void createTable() {
		CreateTableUtil.createTable(jdbc, "leopard_domainwhitelist", new GetSql() {
			@Override
			public String getSql() {
				StringBuilder sb = new StringBuilder();
				sb.append("CREATE TABLE  if not exists `leopard_domainwhitelist` (");
				sb.append("`domain` varchar(50) NOT NULL DEFAULT '',");
				sb.append("`username` varchar(50) NOT NULL DEFAULT '',");
				sb.append("`posttime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',");
				sb.append("`comment` varchar(255) NOT NULL DEFAULT '',");
				sb.append("PRIMARY KEY (`domain`)");
				sb.append(");");
				return sb.toString();
			}

		});
	}

}
