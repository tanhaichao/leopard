package io.leopard.redis.memory;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

public interface IRedisSortedSet extends IRedisKey {

	Long zadd(String key, double score, String member);

	Long zadd(String key, Map<Double, String> scoreMembers);

	Long zrem(String key, long member);

	Set<String> zunionStoreInJava(String... sets);

	Set<String> zunionStoreByScoreInJava(double min, double max, String... sets);

	Long zadd2(String key, Map<String, Double> scoreMembers);

	Long zrem(String key, String... members);

	Double zincrby(String key, double score, String member);

	Long zrank(String key, String member);

	Long zrevrank(String key, String member);

	Set<Tuple> zrangeWithScores(String key, long start, long end);

	Double zscore(String key, String member);

	Long zcount(String key, double min, double max);

	Long zcount(String key, String min, String max);

	Set<String> zrangeByScore(String key, double min, double max);

	Set<String> zrangeByScore(String key, String min, String max);

	Set<String> zrangeByScore(String key, String min, String max, int offset, int count);

	Set<String> zrevrangeByScore(String key, double max, double min);

	Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count);

	Set<String> zrevrangeByScore(String key, String max, String min);

	Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

	Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(String key, String min, String max);

	Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min);

	Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min);

	Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	Long zremrangeByRank(String key, long start, long end);

	Long zremrangeByScore(String key, double start, double end);

	Long zremrangeByScore(String key, String start, String end);

	Long zcard(String key);

	Long zinterstore(String dstkey, String... sets);

	Long zinterstore(String dstkey, ZParams params, String... sets);

	Long zunionstore(String dstkey, String... sets);

	Long zunionstore(String dstkey, ZParams params, String... sets);

	Set<String> zrevrange(String key, long start, long end);

	Set<Tuple> zrevrangeWithScores(String key, long start, long end);

	Set<String> zrange(String key, long start, long end);
}
