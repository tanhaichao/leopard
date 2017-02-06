package io.leopard.redis.memory;

import io.leopard.redis.util.RedisUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

public class RedisSortedSetImpl implements IRedisSortedSet {

	private Map<String, Map<String, Double>> data = new ConcurrentHashMap<String, Map<String, Double>>();

	private Map<String, Long> expire = new ConcurrentHashMap<String, Long>();

	protected Map<String, Double> getMap(String key) {
		Map<String, Double> map = data.get(key);
		if (map == null) {
			map = new TreeMap<String, Double>();
			data.put(key, map);
		}
		return map;
	}

	@Override
	public Boolean exists(String key) {
		return data.containsKey(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		if (!this.exists(key)) {
			return 0L;
		}
		long expireTime = System.currentTimeMillis() + seconds * 1000L;
		this.expire.put(key, expireTime);
		return 1L;
	}

	@Override
	public Long del(String key) {
		Object value = data.remove(key);

		this.expire.remove(key);
		if (value == null) {
			return 0L;
		}
		else {
			return 1L;
		}
	}

	@Override
	public boolean flushAll() {
		data.clear();
		expire.clear();
		return true;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Double old = this.zscore(key, member);
		this.getMap(key).put(member, score);
		if (old == null) {
			return 1L;
		}
		else {
			return 0L;
		}
	}

	@Override
	public Long zrem(String key, long member) {
		return this.zrem(key, Long.toString(member));
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {

		return null;
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {

		return null;
	}

	@Override
	public Long zadd2(String key, Map<String, Double> scoreMembers) {
		Long total = 0L;
		Iterator<Entry<String, Double>> iterator = scoreMembers.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Double> entry = iterator.next();
			double score = entry.getValue();
			String member = entry.getKey();
			Long result = this.zadd(key, score, member);
			total += result;
		}
		return total;
	}

	@Override
	public Long zrem(String key, String... members) {
		long count = 0;
		for (String member : members) {
			Double score = this.getMap(key).remove(member);
			if (score != null) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		Double total = this.getMap(key).get(member);
		if (total == null) {
			total = 0D;
		}
		total += score;
		// System.out.println("zincrby total:" + total + " score:" + score);
		this.zadd(key, total, member);
		return total;
	}

	protected List<Map.Entry<String, Double>> list(String key) {
		Map<String, Double> map = this.getMap(key);
		// Json.printMap(map, "mapmap");
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return (int) (o1.getValue() - o2.getValue());
			}
		});
		return list;
	}

	protected List<Map.Entry<String, Double>> list(String key, double min, double max) {
		List<Map.Entry<String, Double>> list = list(key);
		// System.out.println("list:" + list);
		List<Map.Entry<String, Double>> result = new ArrayList<Map.Entry<String, Double>>();
		for (Map.Entry<String, Double> entry : list) {
			Double score = entry.getValue();
			System.out.println("member:" + entry.getKey() + " score:" + score);
			if (this.checkScore(min, max, score)) {
				result.add(entry);
			}
		}
		return result;
	}

	protected boolean checkScore(double min, double max, double score) {
		if (score >= min) {
			if (score <= max) {
				return true;
			}
			if (max < 0) {
				return true;
			}
		}
		return false;
	}

	protected List<Map.Entry<String, Double>> revlist(String key) {
		Map<String, Double> map = this.getMap(key);
		System.out.println("map:" + map);
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});
		return list;
	}

	protected List<Map.Entry<String, Double>> revlist(String key, double min, double max) {
		List<Map.Entry<String, Double>> list = revlist(key);
		List<Map.Entry<String, Double>> result = new ArrayList<Map.Entry<String, Double>>();
		for (Map.Entry<String, Double> entry : list) {
			Double score = entry.getValue();
			System.out.println("member:" + entry.getKey() + " score:" + score);
			if (score >= min && score <= max) {

				result.add(entry);
			}
		}
		return result;
	}

	@Override
	public Long zrank(String key, String member) {
		Map<String, Double> map = this.getMap(key);
		if (!map.containsKey(member)) {
			return null;
		}
		List<Map.Entry<String, Double>> list = this.list(key);

		long rank = -1;
		for (Map.Entry<String, Double> entry : list) {
			rank++;
			if (entry.getKey().equals(member)) {
				break;
			}
		}
		return rank;
	}

	@Override
	public Long zrevrank(String key, String member) {
		Map<String, Double> map = this.getMap(key);
		if (!map.containsKey(member)) {
			return null;
		}
		List<Map.Entry<String, Double>> list = this.revlist(key);

		Set<String> keySet = map.keySet();
		System.out.println("keySet:" + keySet);
		long rank = -1;
		for (Map.Entry<String, Double> entry : list) {
			rank++;
			if (entry.getKey().equals(member)) {
				break;
			}
		}
		return rank;
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Set<Tuple> set = new LinkedHashSet<Tuple>();
		List<Entry<String, Double>> list = this.list(key);
		long index = 0;
		for (Entry<String, Double> entry : list) {
			System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
			if (this.checkIndex(start, end, index)) {
				Tuple tuple = new Tuple(entry.getKey(), entry.getValue());
				set.add(tuple);
			}
			index++;
		}
		return set;
	}

	private boolean checkIndex(long start, long end, long index) {
		return true;
	}

	@Override
	public Double zscore(String key, String member) {
		return this.getMap(key).get(member);
	}

	@Override
	public Long zcount(String key, double min, double max) {
		Set<String> set = zrangeByScore(key, min, max);
		return (long) set.size();
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return this.zcount(key, Double.parseDouble(min), Double.parseDouble(max));
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		List<Map.Entry<String, Double>> list = this.list(key, min, max);
		Set<String> set = new LinkedHashSet<String>();
		for (Map.Entry<String, Double> entry : list) {
			set.add(entry.getKey());
		}
		return set;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return this.zrangeByScore(key, Double.parseDouble(min), Double.parseDouble(max));
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return this.zrangeByScore(key, Double.parseDouble(min), Double.parseDouble(max), offset, count);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		// TODO ahai 忽略了offset和count两个参数
		List<Map.Entry<String, Double>> list = this.list(key, min, max);
		Set<String> set = new LinkedHashSet<String>();
		for (Map.Entry<String, Double> entry : list) {
			set.add(entry.getKey());
		}
		return set;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return this.zrevrangeByScore(key, max, min, 0, Integer.MAX_VALUE);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return this.zrevrangeByScore(key, Double.parseDouble(max), Double.parseDouble(min));
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return this.zrevrangeByScore(key, Double.parseDouble(max), Double.parseDouble(min), offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		List<Map.Entry<String, Double>> list = this.list(key, min, max);
		List<String> list2 = new ArrayList<String>();
		for (Map.Entry<String, Double> entry : list) {
			list2.add(0, entry.getKey());
		}
		System.out.println("list2:" + list2);
		Set<String> set = new LinkedHashSet<String>();
		set.addAll(list2);
		System.out.println("set:" + set);
		return set;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {

		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {

		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {

		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {

		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {

		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {

		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {

		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {

		return null;
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {

		return null;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {

		return null;
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {

		return null;
	}

	@Override
	public Long zcard(String key) {
		return (long) this.getMap(key).size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long zinterstore(String dstkey, String... sets) {
		int[] weights = new int[sets.length];
		for (int i = 0; i < sets.length; i++) {
			weights[i] = 1;
		}
		ZParams params = new ZParams().aggregate(ZParams.Aggregate.SUM);
		params.weights(weights);
		return zinterstore(dstkey, params, sets);
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		Map<String, Double> map = this.getMap(sets[0]);
		Set<String> keySet = map.keySet();
		Set<String> interKeySet = new HashSet<String>();

		for (String member : keySet) {
			boolean exist = true;
			for (int i = 1; i < sets.length; i++) {
				exist = exist & this.getMap(sets[i]).containsKey(member);
			}
			if (!exist) {
				continue;
			}
			interKeySet.add(member);
		}
		this.del(dstkey);
		if (interKeySet.isEmpty()) {
			return 0L;
		}

		List<Double> weightList = RedisUtil.getWeights(params);
		Double[] weights = new Double[weightList.size()];
		weightList.toArray(weights);

		// ZParams.Aggregate.SUM
		ZParams.Aggregate aggregate = RedisUtil.getAggregate(params);
		Map<String, Double> dstMap = new LinkedHashMap<String, Double>();
		if (aggregate == ZParams.Aggregate.SUM) {
			for (String member : interKeySet) {
				Double total = 0D;
				for (int i = 0; i < sets.length; i++) {
					String key = sets[i];
					Double score = this.getMap(key).get(member);
					total += score * weights[i];
				}
				dstMap.put(member, total);
			}
		}
		else if (aggregate == ZParams.Aggregate.MAX) {
			throw new UnsupportedOperationException("Not Implemented");
		}
		else if (aggregate == ZParams.Aggregate.MIN) {
			throw new UnsupportedOperationException("Not Implemented");
		}
		else {
			throw new IllegalArgumentException("未知类型[" + aggregate + "].");
		}
		this.zadd2(dstkey, dstMap);

		// System.out.println("dstkey:" + dstkey + " dstMap:" + Json.toJson(dstMap));
		System.out.println("dstkey:" + dstkey + " dstList:" + this.list(dstkey));
		return (long) interKeySet.size();
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		return null;
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		Set<String> set = new LinkedHashSet<String>();
		List<Map.Entry<String, Double>> list = this.revlist(key);
		System.out.println("list:" + list);
		long index = 0;
		for (Entry<String, Double> entry : list) {
			System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
			if (this.checkIndex(start, end, index)) {
				set.add(entry.getKey());
			}
			index++;
		}
		return set;
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Set<Tuple> set = new LinkedHashSet<Tuple>();
		List<Entry<String, Double>> list = this.revlist(key);
		long index = 0;
		for (Entry<String, Double> entry : list) {
			System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
			if (this.checkIndex(start, end, index)) {
				Tuple tuple = new Tuple(entry.getKey(), entry.getValue());
				set.add(tuple);
			}
			index++;
		}
		return set;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		Set<String> set = new LinkedHashSet<String>();
		List<Map.Entry<String, Double>> list = this.list(key);
		long index = 0;
		for (Entry<String, Double> entry : list) {
			System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
			if (this.checkIndex(start, end, index)) {
				set.add(entry.getKey());
			}
			index++;
		}
		return set;
	}

	@Override
	public Long zadd(String key, Map<Double, String> scoreMembers) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		return null;
	}

}
