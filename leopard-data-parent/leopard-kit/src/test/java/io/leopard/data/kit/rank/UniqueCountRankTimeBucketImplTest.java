package io.leopard.data.kit.rank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import io.leopard.redis.Redis;
import io.leopard.redis.RedisMemoryImpl;
import io.leopard.redis.util.RedisFactory;

public class UniqueCountRankTimeBucketImplTest {

	private UniqueCountRankTimeBucketImpl rank = new UniqueCountRankTimeBucketImpl();

	private Redis redis;

	public UniqueCountRankTimeBucketImplTest() {
		redis = new RedisMemoryImpl();
		redis = RedisFactory.create("112.126.75.27:6311");

		rank.setKey("CountRankTimeBucket");
		rank.setRedis(redis);
		rank.setTimeBucket(TimeBucket.DAY);
		rank.init();
	}

	@Test
	public void incr() {
		rank.clean();
		Assert.assertEquals(1, rank.incr("member1", "1", 1, new Date()));
		Assert.assertEquals(0, rank.incr("member1", "1", 1, new Date()));
		Assert.assertEquals(2, rank.incr("member1", "2", 1, new Date()));
		Assert.assertEquals(1, rank.incr("member2", "1", 1, new Date()));
		Assert.assertEquals(1, rank.getScore("member2").longValue());

		System.out.println(rank.listMembers(0, 10));
		Assert.assertEquals("[member1, member2]", rank.listMembers(0, 10).toString());

	}

	@Test
	public void isCurrentTimeBucket() throws ParseException {
		// yyyyMMddHHmm
		Assert.assertTrue(rank.isCurrentTimeBucket((double) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-02-21 00:59:00").getTime()));
		Assert.assertFalse(rank.isCurrentTimeBucket((double) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-02-20 00:59:00").getTime()));
	}

}