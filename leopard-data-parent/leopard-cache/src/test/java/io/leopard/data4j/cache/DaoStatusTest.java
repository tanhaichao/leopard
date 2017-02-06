package io.leopard.data4j.cache;

import org.junit.Assert;
import org.junit.Test;

public class DaoStatusTest {

	@Test
	public void setMysqlStatus() {
		DaoStatus daoStatus = new DaoStatus();
		daoStatus.setMysqlStatus(true);
		Assert.assertTrue((Boolean) daoStatus.getMysqlStatus());
	}

	@Test
	public void setMemcachedStatus() {
		DaoStatus daoStatus = new DaoStatus();
		daoStatus.setMemcachedStatus(true);
		Assert.assertTrue((Boolean) daoStatus.getMemcachedStatus());
	}

	@Test
	public void setTcStatus() {
		DaoStatus daoStatus = new DaoStatus();
		daoStatus.setTcStatus(true);
		Assert.assertTrue((Boolean) daoStatus.getTcStatus());
	}

	@Test
	public void isEquals() {
		{
			DaoStatus daoStatus = new DaoStatus();
			daoStatus.setMysqlStatus(null);
			daoStatus.setMemcachedStatus(true);
			Assert.assertFalse(daoStatus.isEquals());
		}
		{
			DaoStatus daoStatus = new DaoStatus();
			daoStatus.setMysqlStatus(null);
			daoStatus.setTcStatus(true);
			Assert.assertFalse(daoStatus.isEquals());
		}
		{
			DaoStatus daoStatus = new DaoStatus();
			daoStatus.setMysqlStatus(null);
			Assert.assertTrue(daoStatus.isEquals());
		}
	}

	@Test
	public void isEquals2() {
		{
			DaoStatus daoStatus = new DaoStatus();
			daoStatus.setMysqlStatus(true);
			daoStatus.setMemcachedStatus(false);
			Assert.assertFalse(daoStatus.isEquals());
		}
		{
			DaoStatus daoStatus = new DaoStatus();
			daoStatus.setMysqlStatus(true);
			daoStatus.setMemcachedStatus(true);
			daoStatus.setTcStatus(false);
			Assert.assertFalse(daoStatus.isEquals());
		}
		{
			DaoStatus daoStatus = new DaoStatus();
			daoStatus.setMysqlStatus(true);
			daoStatus.setMemcachedStatus(true);
			daoStatus.setTcStatus(true);
			Assert.assertTrue(daoStatus.isEquals());
		}
	}
}