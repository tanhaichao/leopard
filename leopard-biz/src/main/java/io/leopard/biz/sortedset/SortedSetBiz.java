package io.leopard.biz.sortedset;

import java.util.Set;

import redis.clients.jedis.Tuple;

public interface SortedSetBiz {

	boolean zadd(String element, double score);

	Double zscore(String element);

	boolean zrem(String element);

	Set<Tuple> listAll();

}
