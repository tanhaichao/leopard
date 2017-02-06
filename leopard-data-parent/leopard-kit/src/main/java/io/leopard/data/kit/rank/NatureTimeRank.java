package io.leopard.data.kit.rank;

import java.util.Date;
import java.util.List;

import redis.clients.jedis.Tuple;

/**
 * 按自然时间排名.
 * 
 * @author ahai
 *
 */
public interface NatureTimeRank {

	public static long MINUTE_MILLIS = 1000L * 60;
	public static long HOUR_MILLIS = MINUTE_MILLIS * 60;
	public static long DAY_MILLIS = HOUR_MILLIS * 24;
	public static long WEEK_MILLIS = DAY_MILLIS * 7;

	boolean add(String field, String member, Date posttime);

	List<Tuple> list(int start, int size);

	List<Tuple> list(String field, int start, int size);

	boolean delete(String field);

	boolean delete(String field, String member);

	int clean();

	int count(String field);

	int count();

	List<String> listMembers(int start, int size);
}
