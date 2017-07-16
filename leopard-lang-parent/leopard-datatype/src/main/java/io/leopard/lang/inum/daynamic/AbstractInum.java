package io.leopard.lang.inum.daynamic;

import io.leopard.lang.inum.Inum;

public class AbstractInum extends DynamicEnum<Integer> implements Inum {
	public AbstractInum() {

	}

	public AbstractInum(Integer key) {
		super(key);
	}
}
