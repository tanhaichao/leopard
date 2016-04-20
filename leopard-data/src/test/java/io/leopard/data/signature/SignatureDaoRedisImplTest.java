package io.leopard.data.signature;

import io.leopard.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SignatureDaoRedisImplTest {

	protected SignatureDaoRedisImpl signatureDao = newInstance();
	protected static Redis redis = Mockito.mock(Redis.class);

	protected static SignatureDaoRedisImpl newInstance() {
		SignatureDaoRedisImpl signatureDao = new SignatureDaoRedisImpl();
		signatureDao.setRedis(redis);
		signatureDao.setRedisKey("prefix");
		return signatureDao;
	}

	@Test
	public void getKey() {
		Assert.assertEquals("prefix:member", signatureDao.getKey("member"));
	}

	@Test
	public void add() {
		// this.redis.set(key, "", TIMEOUT_SECONDS);
		this.signatureDao.add("member");
		Mockito.verify(redis).set("prefix:member", "", 30 * 60);
	}

	@Test
	public void exist() {
		Mockito.doReturn("ok").when(redis).get("prefix:member");
		Assert.assertTrue(this.signatureDao.exist("member"));
	}

	@Test
	public void remove() {
		Mockito.doReturn(1L).when(redis).del("prefix:member");
		Assert.assertTrue(this.signatureDao.remove("member"));
	}

}