package io.leopard.jdbc;

import io.leopard.jdbc.InvalidParamDataAccessException;
import io.leopard.jdbc.SqlUtil;
import io.leopard.jdbc.StatementParameter;

import org.junit.Assert;
import org.junit.Test;

public class SqlUtilTest {

	@Test
	public void getSQL() {
		Assert.assertEquals("sql", SqlUtil.getSQL("sql", null));
		StatementParameter param = new StatementParameter();
		param.setString("value");

		Assert.assertEquals("sql='value'", SqlUtil.getSQL("sql=?", param));

		try {
			SqlUtil.getSQL("sql=?", null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (InvalidParamDataAccessException e) {

		}
	}

	@Test
	public void SqlUtil() {
		new SqlUtil();
	}

	@Test
	public void getValue() {
		Assert.assertEquals("'value'", SqlUtil.getValue(String.class, "value"));
		// Assert.assertEquals("'2013-01-01 00:00:00'",
		// SqlUtil.getValue(Date.class,
		// DateUtil.toDate("2013-01-01 00:00:00")));
//		Assert.assertEquals("'2013-01-01'", SqlUtil.getValue(OnlyDate.class, new OnlyDate("2013-01-01")));
		// Assert.assertEquals("'2013-01'", SqlUtil.getValue(Month.class, new
		// Month("2013-01-01")));
		Assert.assertEquals("1", SqlUtil.getValue(Integer.class, 1));
		Assert.assertEquals("1", SqlUtil.getValue(Long.class, 1L));
		Assert.assertEquals("1.0", SqlUtil.getValue(Float.class, 1.0f));
		Assert.assertEquals("1.0", SqlUtil.getValue(Double.class, 1.0d));
		try {
			SqlUtil.getValue(Object.class, new Object());
			Assert.fail("怎么没有抛异常?");
		}
		catch (InvalidParamDataAccessException e) {

		}
	}

	@Test
	public void toCountSql() {
		Assert.assertEquals("select count(*) from user", SqlUtil.toCountSql("select * from user"));
	}

}