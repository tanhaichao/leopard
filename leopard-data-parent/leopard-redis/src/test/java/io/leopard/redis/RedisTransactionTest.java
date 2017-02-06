package io.leopard.redis;

import io.leopard.redis.RedisImpl;
import io.leopard.redis.util.RedisFactory;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * Redis事务测试.
 * 
 * @author 阿海
 * 
 */
public class RedisTransactionTest {
	private RedisImpl redis = RedisFactory.create("113.108.228.100:6311");

	private String KEY_PREFIX = "prefix_";

	@Test
	public void test() {
		// Caller caller = new Caller();
		// for (int i = 0; i < 2; i++) {
		// final int threadId = i;
		// caller.add(new Invoker() {
		// @Override
		// public void execute() {
		// test(threadId);
		// }
		// });
		// }
		// caller.execute();

	}

	public void test(int threadId) {
		String key1 = KEY_PREFIX + "key1";
		String value = "value:" + threadId + ";";
		// redis.set(key1, value);
		// System.out.println("getResource:" + threadId);
		Jedis jedis = redis.getResource();
		{
			Transaction transaction = jedis.multi();
			transaction.set(key1, value + "1");
			transaction.discard();
		}
		{
			Transaction transaction = jedis.multi();
			Response<String> value2 = transaction.get(key1);
			transaction.set(key1, value + "2");
			transaction.exec();
			// System.out.println("value:" + value2.get());
			Assert.assertEquals(value, value2.get());
		}
		System.out.println("threadId:" + threadId + " value:" + redis.get(key1));
		Assert.assertEquals(value + "2", redis.get(key1));
	}
}
