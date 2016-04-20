package io.leopard.biz.sortedset;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Tuple;

public class SortedSetBizMemoryImpl implements SortedSetBiz {

	private Map<String, Double> data = new ConcurrentHashMap<String, Double>();

	@Override
	public boolean zadd(String element, double score) {
		this.data.put(element, score);
		return true;
	}

	@Override
	public Double zscore(String element) {
		return data.get(element);
	}

	@Override
	public boolean zrem(String element) {
		Double score = data.remove(element);
		return score != null;
	}

	@Override
	public Set<Tuple> listAll() {
		Set<Entry<String, Double>> set = data.entrySet();
		if (set == null || set.isEmpty()) {
			return null;
		}
		Set<Tuple> result = new HashSet<Tuple>();
		for (Entry<String, Double> entry : set) {
			Tuple tuple = new Tuple(entry.getKey(), entry.getValue());
			result.add(tuple);
		}
		return result;
	}

}
