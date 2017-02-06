package io.leopard.data.kit.rank;

import java.util.Date;

/**
 * 数量排名.
 * 
 * @author 阿海
 *
 */
public interface CountRank extends Rank {

	/**
	 * 增加数量.
	 * 
	 * @param member
	 * @param count
	 * @return
	 */
	long incr(String member, long count, Date posttime);
}
