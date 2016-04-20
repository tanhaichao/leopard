package io.leopard.data.counter;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class CounterRedisImplTest {

	@Test
	public void getKey() {
		String key = CounterRedisImpl.getKey("share", TimeUnit.DAYS, 1);
		System.err.println("key:" + key);
		CounterRedisImpl.getKey("share", TimeUnit.MINUTES, 1);
	}

}