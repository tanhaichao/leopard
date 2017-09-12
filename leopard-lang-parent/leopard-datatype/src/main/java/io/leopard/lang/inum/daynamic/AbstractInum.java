package io.leopard.lang.inum.daynamic;

import io.leopard.lang.inum.Inum;

/**
 * key类型为Integer的动态枚举
 * 
 * @author 谭海潮
 *
 */
public class AbstractInum extends DynamicEnum<Integer> implements Inum {
	public AbstractInum() {

	}

	public AbstractInum(Integer key) {
		super(key);
	}
}
