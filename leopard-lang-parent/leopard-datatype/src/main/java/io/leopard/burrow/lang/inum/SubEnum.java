package io.leopard.burrow.lang.inum;

import java.util.List;

/**
 * 子枚举
 * 
 * @author 谭海潮
 *
 */
public interface SubEnum<T> {

	/**
	 * 获取子枚举列表
	 * 
	 * @return
	 */
	List<T> getSubList();

}
