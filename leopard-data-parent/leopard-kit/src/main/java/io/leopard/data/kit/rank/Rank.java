package io.leopard.data.kit.rank;

import java.util.List;

import redis.clients.jedis.Tuple;

/**
 * 排名.
 * 
 * @author 阿海
 *
 */
public interface Rank {
	/**
	 * 清空记录.
	 * 
	 * @return
	 */
	boolean clean();

	/**
	 * 删除元素.
	 * 
	 * @param member
	 * @return
	 */
	boolean delete(String member);

	/**
	 * 获取排序值.
	 * 
	 * @param member
	 * @return
	 */
	Double getScore(String member);

	List<Tuple> list(int start, int size);

	/**
	 * 获取所有元素.
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
	List<String> listMembers(int start, int size);
}
