package io.leopard.lang.inum.daynamic;

import io.leopard.lang.inum.Bnum;

public class AbstractBnum extends DynamicEnum<Byte> implements Bnum {
	public AbstractBnum() {

	}

	public AbstractBnum(Byte key) {
		super(key);
	}
}
