package io.leopard.jdbc;

import io.leopard.jdbc.ProxyConnection;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ProxyConnectionTest {
	Connection connection = Mockito.mock(Connection.class);
	ProxyConnection proxyConn = new ProxyConnection(connection);

	@Test
	public void getConn() throws SQLException {
		Assert.assertNotNull(proxyConn.getConn());
	}

	@Test
	public void unwrap() throws SQLException {
		proxyConn.unwrap(String.class);
	}

	@Test
	public void isWrapperFor() throws SQLException {
		proxyConn.isWrapperFor(String.class);
	}

	@Test
	public void createStatement() throws SQLException {
		proxyConn.createStatement();
		proxyConn.createStatement(1, 1);
		proxyConn.createStatement(1, 1, 1);
	}

	@Test
	public void prepareStatement() throws SQLException {
		proxyConn.prepareStatement("sql");
		proxyConn.prepareStatement("sql", 1);
		proxyConn.prepareStatement("sql", 1, 1);
		proxyConn.prepareStatement("sql", 1, 1, 1);
		proxyConn.prepareStatement("sql", new int[] {});
		proxyConn.prepareStatement("sql", new String[] {});

	}

	@Test
	public void prepareCall() throws SQLException {
		proxyConn.prepareCall("sql");
		proxyConn.prepareCall("sql", 1, 1);
		proxyConn.prepareCall("sql", 1, 1, 1);
	}

	@Test
	public void nativeSQL() throws SQLException {
		proxyConn.nativeSQL("sql");
	}

	@Test
	public void setAutoCommit() throws SQLException {
		proxyConn.setAutoCommit(true);
	}

	@Test
	public void getAutoCommit() throws SQLException {
		proxyConn.getAutoCommit();
	}

	@Test
	public void commit() throws SQLException {
		proxyConn.commit();
	}

	@Test
	public void rollback() throws SQLException {
		proxyConn.rollback();
		proxyConn.rollback(null);
	}

	@Test
	public void close() throws SQLException {
		proxyConn.close();
	}

	@Test
	public void isClosed() throws SQLException {
		proxyConn.isClosed();
	}

	@Test
	public void getMetaData() throws SQLException {
		proxyConn.getMetaData();
	}

	@Test
	public void setReadOnly() throws SQLException {
		proxyConn.setReadOnly(true);
	}

	@Test
	public void isReadOnly() throws SQLException {
		proxyConn.isReadOnly();
	}

	@Test
	public void setCatalog() throws SQLException {
		proxyConn.setCatalog("catalog");
	}

	@Test
	public void getCatalog() throws SQLException {
		proxyConn.getCatalog();
	}

	@Test
	public void setTransactionIsolation() throws SQLException {
		proxyConn.setTransactionIsolation(1);
	}

	@Test
	public void getTransactionIsolation() throws SQLException {
		proxyConn.getTransactionIsolation();
	}

	@Test
	public void getWarnings() throws SQLException {
		proxyConn.getWarnings();
	}

	@Test
	public void clearWarnings() throws SQLException {
		proxyConn.clearWarnings();
	}

	@Test
	public void getTypeMap() throws SQLException {
		proxyConn.getTypeMap();
	}

	@Test
	public void setTypeMap() throws SQLException {
		proxyConn.setTypeMap(null);
	}

	@Test
	public void setHoldability() throws SQLException {
		proxyConn.setHoldability(1);
	}

	@Test
	public void getHoldability() throws SQLException {
		proxyConn.getHoldability();
	}

	@Test
	public void setSavepoint() throws SQLException {
		proxyConn.setSavepoint();
		proxyConn.setSavepoint("name");
	}

	@Test
	public void releaseSavepoint() throws SQLException {
		proxyConn.releaseSavepoint(null);
	}

	@Test
	public void createClob() throws SQLException {
		proxyConn.createClob();
	}

	@Test
	public void createBlob() throws SQLException {
		proxyConn.createBlob();
	}

	@Test
	public void createNClob() throws SQLException {
		proxyConn.createNClob();
	}

	@Test
	public void createSQLXML() throws SQLException {
		proxyConn.createSQLXML();
	}

	@Test
	public void isValid() throws SQLException {
		proxyConn.isValid(1);
	}

	@Test
	public void setClientInfo() throws SQLException {
		proxyConn.setClientInfo(null);
		proxyConn.setClientInfo("name", "value");
	}

	@Test
	public void getClientInfo() throws SQLException {
		proxyConn.getClientInfo();
		proxyConn.getClientInfo("name");
	}

	@Test
	public void createArrayOf() throws SQLException {
		proxyConn.createArrayOf("typeName", null);
	}

	@Test
	public void createStruct() throws SQLException {
		proxyConn.createStruct("typeName", null);
	}

}