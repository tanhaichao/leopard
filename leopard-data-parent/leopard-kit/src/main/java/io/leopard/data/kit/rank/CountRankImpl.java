package io.leopard.data.kit.rank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import io.leopard.redis.Redis;
import redis.clients.jedis.Tuple;

/**
 * 数量排名实现.
 * 
 * @author 阿海
 *
 */
public abstract class CountRankImpl implements CountRank {

	private Redis redis;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public abstract String getKey(Date posttime);

	public Redis getRedis() {
		return redis;
	}

	@Override
	public boolean clean() {
		Long num = redis.del(getKey(new Date()));
		return num != null && num > 0;
	}

	@Override
	public boolean delete(String member) {
		Long num = redis.zrem(getKey(new Date()), member);
		return num != null && num > 0;
	}

	@Override
	public Double getScore(String member) {
		return redis.zscore(getKey(new Date()), member);
	}

	@Override
	public List<Tuple> list(int start, int size) {
		long end = start + size;
		Set<Tuple> set = redis.zrevrangeWithScores(getKey(new Date()), start, end);
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
		long end = start + size - 1;
		Set<String> set = redis.zrevrange(getKey(new Date()), start, end);
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
	public long incr(String member, long count, Date posttime) {
		Double totalCount = redis.zincrby(getKey(posttime), count, member);
		return totalCount.longValue();
	}

}
