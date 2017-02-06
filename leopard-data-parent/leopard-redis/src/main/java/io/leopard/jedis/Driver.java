package io.leopard.jedis;

public interface Driver {

	JedisWrapper connect(redis.clients.jedis.Jedis jedis);
}
