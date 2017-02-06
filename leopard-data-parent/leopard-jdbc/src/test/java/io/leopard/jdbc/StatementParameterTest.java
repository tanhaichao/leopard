package io.leopard.jdbc;

import io.leopard.jdbc.StatementParameter;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class StatementParameterTest {

	@Test
	public void setDate() {
		StatementParameter param = new StatementParameter();
		param.setDate(new Date(1));
		Assert.assertEquals(1L, param.getDate(0).getTime());
	}

//	@Test
//	public void setOnlyDate() {
//		StatementParameter param = new StatementParameter();
//		param.setOnlyDate(new OnlyDate("2013-01-01"));
//	}
//
//	@Test
//	public void setMonth() {
//		StatementParameter param = new StatementParameter();
//		param.setMonth(new Month("2013-01-01"));
//		Assert.assertEquals("2013-01", param.getMonth(0).toString());
//	}

	@Test
	public void setTimestamp() {
		StatementParameter param = new StatementParameter();
		param.setTimestamp(new Timestamp(1));
		Assert.assertEquals(1L, param.getTimestamp(0).getTime());
	}

	@Test
	public void setObject() {
		StatementParameter param = new StatementParameter();
		param.setBool(false);
		param.setString("str");
		param.setInt(1);
		param.setLong(1L);
		param.setFloat(1F);
		param.setLong(1L);
		param.setDouble(1D);
//		param.setMonth(new Month());
//		param.setOnlyDate(new OnlyDate());
		param.setDate(new Date());

		param.getArgTypes();

		StatementParameter param2 = new StatementParameter();

//		System.out.println("args:" + StringUtils.join(param.getArgs(), ","));
		int index = 0;
		for (Object arg : param.getArgs()) {
			Class<?> type = param.getType(index);
			System.out.println("index:" + index + " type:" + type + " arg:" + arg);
			param2.setObject(type, arg);
			index++;
		}
	}

	@Test
	public void setString() {
		StatementParameter param = new StatementParameter();
		param.setString("str");
		Assert.assertEquals("str", param.getString(0));
	}

	@Test
	public void setBool() {
		StatementParameter param = new StatementParameter();
		param.setBool(true);
		Assert.assertTrue(param.getBool(0));
		param.setBool(false);
		Assert.assertFalse(param.getBool(1));
	}

	@Test
	public void setInt() {
		StatementParameter param = new StatementParameter();
		param.setInt(2);
		Assert.assertEquals(2, param.getInt(0));
	}

	@Test
	public void setLong() {
		StatementParameter param = new StatementParameter();
		param.setLong(2L);
		Assert.assertEquals(2L, param.getLong(0));
	}

	@Test
	public void setDouble() {
		StatementParameter param = new StatementParameter();
		param.setDouble(2D);
		Assert.assertEquals(2D, param.getDouble(0), 0);
	}

	@Test
	public void setFloat() {
		StatementParameter param = new StatementParameter();
		param.setFloat(2F);
		Assert.assertEquals(2F, param.getFloat(0), 0);
	}

	@Test
	public void getArgs() {

	}

	@Test
	public void getObject() {

	}

	@Test
	public void getTypes() {

	}

	@Test
	public void getArgTypes() {

	}

	@Test
	public void size() {
		StatementParameter param = new StatementParameter();
		param.setDouble(2D);

		Assert.assertEquals(1, param.size());
	}

	@Test
	public void getParameters() {
		StatementParameter param = new StatementParameter();
		Assert.assertNull(param.getParameters());
		param.setString("str");
		Assert.assertNotNull(param.getParameters());
	}

	@Test
	public void setValues() throws SQLException {
		// PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);
		// StatementParameter param = new StatementParameter();
		// param.setValues(pstmt, 1, String.class, "str");
		// param.setValues(pstmt, 2, Integer.class, 1);
		// param.setValues(pstmt, 3, Long.class, 1L);
		// param.setValues(pstmt, 4, Double.class, 1D);
		// param.setValues(pstmt, 4, Float.class, 1F);
		// param.setValues(pstmt, 5, Timestamp.class, new Timestamp(1));
		// param.setValues(pstmt, 6, java.sql.Date.class, new java.sql.Date(1));
		// try {
		// param.setValues(pstmt, 9, Object.class, new Object());
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (InvalidParamDataAccessException e) {
		//
		// }
	}

	@Test
	public void StatementParameter() {
		StatementParameter param = new StatementParameter();
		param.setBool(null);
		Assert.assertNull(param.getArg(0));
	}
}