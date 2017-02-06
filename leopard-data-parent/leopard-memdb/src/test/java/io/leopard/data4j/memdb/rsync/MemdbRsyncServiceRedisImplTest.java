package io.leopard.data4j.memdb.rsync;

import io.leopard.data4j.memdb.MemdbRsyncQueue;
import io.leopard.data4j.memdb.MemdbRsyncServiceRedisImpl;
import io.leopard.redis.Redis;

import org.junit.Test;
import org.mockito.Mockito;

public class MemdbRsyncServiceRedisImplTest {

	MemdbRsyncQueue memdbRsyncQueue = Mockito.mock(MemdbRsyncQueue.class);
	Redis redis = Mockito.mock(Redis.class);

	MemdbRsyncServiceRedisImpl memdbRsyncService = new MemdbRsyncServiceRedisImpl(redis, "channel", memdbRsyncQueue);

	@Test
	public void add() {
		memdbRsyncService.add("type", "key", "value", false);
	}

	@Test
	public void onPMessage() {

	}

	@Test
	public void destroy() {
		memdbRsyncService.destroy();
	}

	@Test
	public void init() {
		memdbRsyncService.init();
	}

}