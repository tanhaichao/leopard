package io.leopard.data.kit.rank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import io.leopard.redis.Redis;
import redis.clients.jedis.Tuple;

//TODO ahai 未完整实现
public class NatureTimeRankRedisImpl implements NatureTimeRank {

	private Redis redis;

	private String key;

	/**
	 * 自然时间(毫秒数)
	 */
	private long natureTime;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public void setKey(String key) {
		this.key = key;
	}

	protected String getFieldKey(String field) {
		return this.key + ":" + field;
	}

	public void setNatureTime(long natureTime) {
		this.natureTime = natureTime;
	}

	@Override
	public boolean add(String field, String member, Date posttime) {
		String fieldKey = this.getFieldKey(field);
		redis.zadd(fieldKey, posttime.getTime(), member);
		long min = posttime.getTime() - this.natureTime;
		Long count = redis.zcount(fieldKey, min, Long.MAX_VALUE);
		// System.out.println("count:" + count);
		redis.zadd(key, count, field);
		return true;
	}

	@Override
	public int count() {
		Long count = redis.zcard(key);
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	@Override
	public int count(String field) {
		String fieldKey = this.getFieldKey(field);
		long min = System.currentTimeMillis() - this.natureTime;
		Long count = redis.zcount(fieldKey, min, Long.MAX_VALUE);
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	@Override
	public List<Tuple> list(int start, int size) {
		long end = start + size;
		Set<Tuple> set = redis.zrevrangeWithScores(key, start, end);
		if (set == null || set.isEmpty()) {
			return null;
		}
		List<Tuple> list = new ArrayList<Tuple>();
		for (Tuple tuple : set) {
			list.add(tuple);
		}
		return list;
	}

	@Override
	public List<String> listMembers(int start, int size) {
		long end = start + size;
		Set<String> set = redis.zrevrange(key, start, end);
		if (set == null || set.isEmpty()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String member : set) {
			list.add(member);
		}
		return list;
	}

	@Override
	public List<Tuple> list(String field, int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String field, String element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int clean() {
		Set<String> fieldSet = this.redis.zrange(key, 0, -1);
		if (fieldSet == null || fieldSet.isEmpty()) {
			return 0;
		}
		for (String field : fieldSet) {
			String fieldKey = this.getFieldKey(field);
			redis.del(fieldKey);
		}
		redis.del(key);
		return fieldSet.size();
	}

}
