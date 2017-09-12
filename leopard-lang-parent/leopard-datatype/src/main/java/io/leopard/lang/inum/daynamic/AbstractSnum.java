package io.leopard.lang.inum.daynamic;

import io.leopard.lang.inum.Snum;

/**
 * key类型为String的动态枚举
 * 
 * @author 谭海潮
 *
 */
public class AbstractSnum extends DynamicEnum<String> implements Snum {
	public AbstractSnum() {

	}

	public AbstractSnum(String key) {
		super(key);
	}

}
