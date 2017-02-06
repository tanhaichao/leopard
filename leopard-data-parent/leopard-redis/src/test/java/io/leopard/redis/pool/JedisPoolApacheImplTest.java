package io.leopard.redis.pool;

import io.leopard.redis.JedisPool;
import io.leopard.redis.util.JedisPoolApacheImpl;

import org.junit.Test;
import org.mockito.Mockito;

public class JedisPoolApacheImplTest {

	@Test
	public void JedisPoolApacheImpl() throws Exception {
		JedisPool jedisPoolMock = Mockito.mock(JedisPool.class);

		JedisPoolApacheImpl jedisPool = new JedisPoolApacheImpl("host", 6311, 3000, 16, null);
		// LeopardMockito.setProperty(jedisPool, jedisPoolMock, "pool");
		jedisPool.getResource();
		jedisPool.returnBrokenResource(null);
		jedisPool.returnResource(null);
		jedisPool.destroy();
	}

}