package io.leopard.biz.lucky;

/**
 * 随机匹配组件.
 * 
 * @author 阿海
 *
 */
public interface LuckyBizdb {

	/**
	 * 在随机池子里添加或修改一个元素.
	 * 
	 * @param member
	 * @param weight
	 *            随机权重
	 * @return
	 */
	boolean add(String member, int weight);

	/**
	 * 随机返回一个元素.
	 * 
	 * @return
	 */
	String lucky();

	/**
	 * 从随机池子中删除一个元素.
	 * 
	 * @param member
	 * @return
	 */
	boolean delete(String member);

	/**
	 * 获取元素总数量.
	 * 
	 * @return
	 */
	int count();

	/**
	 * 按权重获取元素数量.
	 * 
	 * @param weight
	 * @return
	 */
	int count(int weight);

	/**
	 * 检查一个元素是否存在.
	 * 
	 * @param member
	 * @return
	 */
	boolean exist(String member);

	boolean clean();
}
