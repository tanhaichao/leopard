package io.leopard.lang.inum.daynamic;

import io.leopard.lang.inum.Bnum;

/**
 * key类型为Byte的动态枚举
 * 
 * @author 谭海潮
 *
 */
public class AbstractBnum extends DynamicEnum<Byte> implements Bnum {
	public AbstractBnum() {

	}

	public AbstractBnum(Byte key) {
		super(key);
	}
}
