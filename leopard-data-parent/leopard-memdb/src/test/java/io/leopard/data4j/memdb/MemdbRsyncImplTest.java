package io.leopard.data4j.memdb;

import io.leopard.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MemdbRsyncImplTest {
	MemdbRsyncService memdbRsyncService = Mockito.mock(MemdbRsyncService.class);
	protected MemdbRsyncImpl memdb = newInstance();

	public MemdbRsyncImpl newInstance() {
		MemdbRsyncImpl memdb = new MemdbRsyncImpl(10);
		memdb.memdbRsyncService = memdbRsyncService;
		return memdb;
	}

	@Test
	public void set() {
		memdb.set("key", "value");

		Mockito.verify(memdbRsyncService).add(MemdbRsyncService.TYPE_SET, "key", "value", false);
	}

	@Test
	public void remove() {
		memdb.remove("key");
		Mockito.verify(memdbRsyncService).add(MemdbRsyncService.TYPE_REMOVE, "key", null, false);
	}

	@Test
	public void add() {
		memdb.add(MemdbRsyncService.TYPE_SET.toString(), "key", "value", false);
		memdb.add(MemdbRsyncService.TYPE_REMOVE.toString(), "key", "value", false);
		Assert.assertFalse(memdb.add("other", "key", "value", false));
	}

	@Test
	public void MemdbRsyncImpl() {

	}

	@Test
	public void init() {
		Redis redis = Mockito.mock(Redis.class);
		MemdbRsyncImpl memdb = new MemdbRsyncImpl(10);
		memdb.setRedis(redis);
		memdb.setChannel("channel");
		memdb.init();
		Assert.assertNotNull(memdb.memdbRsyncService);
		memdb.destroy();

	}

}