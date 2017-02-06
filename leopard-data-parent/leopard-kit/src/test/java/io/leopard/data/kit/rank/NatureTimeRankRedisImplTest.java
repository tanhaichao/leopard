package io.leopard.data.kit.rank;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import io.leopard.redis.Redis;
import io.leopard.redis.util.RedisFactory;
import redis.clients.jedis.Tuple;

public class NatureTimeRankRedisImplTest {

	private NatureTimeRankRedisImpl natureTimeRankRedisImpl = new NatureTimeRankRedisImpl();

	public NatureTimeRankRedisImplTest() {
		Redis redis = RedisFactory.create("112.126.75.27:6311");
		natureTimeRankRedisImpl.setRedis(redis);
		natureTimeRankRedisImpl.setKey("time_rank");
		natureTimeRankRedisImpl.setNatureTime(NatureTimeRank.MINUTE_MILLIS);
	}

	@Test
	public void add() {
		// System.out.println("clean count:" + this.natureTimeRankRedisImpl.clean());
		natureTimeRankRedisImpl.add("shareId1", "1", new Date());
		System.out.println("member count:" + this.natureTimeRankRedisImpl.count("shareId1"));
		natureTimeRankRedisImpl.add("shareId1", "2", new Date());
		System.out.println("member count:" + this.natureTimeRankRedisImpl.count("shareId1"));
		natureTimeRankRedisImpl.add("shareId2", "1", new Date());
		System.out.println("member count:" + this.natureTimeRankRedisImpl.count("shareId2"));

		this.list();
	}

	@Test
	public void list() {
		List<Tuple> list = this.natureTimeRankRedisImpl.list(0, 10);
		for (Tuple tuple : list) {
			System.out.println("count:" + tuple.getScore() + " member:" + tuple.getElement());
		}
	}

}