package io.leopard.jdbc;

import org.junit.Test;

import io.leopard.lang.Paging;

public class JdbcMysqlImplTest {
	// udb.jdbc.driverClass=org.gjt.mm.mysql.Driver
	// udb.jdbc.url=jdbc:mysql://112.126.75.27:3306/monitor?useUnicode=true&characterEncoding=UTF8
	// udb.jdbc.username=cloud
	// udb.jdbc.password=cloud123

	private JdbcMysqlImpl jdbc = JdbcFactory.creaeJdbcMysqlImpl("112.126.75.27", "monitor", "cloud", "cloud123");

	@Test
	public void queryForPaging() {

		StringBuilder sb = new StringBuilder();
		sb.append(",sum(allCount) allCount,sum(allTime) allTime");
		sb.append(",sum(slowCount) slowCount,sum(slowTime) slowTime");
		sb.append(",sum(verySlowCount) verySlowCount,sum(verySlowTime) verySlowTime");
		sb.append(",sum(maxCount) maxCount,sum(maxTime) maxTime,max(maxDate) maxDate");

		sb.append(",lmodify");

		String sql = "select projectId,time,url" + sb.toString() + " from request_day where projectId=? group by url";
		StatementParameter param = new StatementParameter();
		param.setString("123");

		Paging<RequestStat> paging = jdbc.queryForPaging(sql, RequestStat.class, param, 1, 1);

		System.out.println("paging:" + paging.getTotalCount());
	}

}