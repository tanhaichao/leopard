package io.leopard.data4j.cache.api.simple;

public interface ISExist<KEYTYPE> {
	boolean add(KEYTYPE key);

	boolean exist(KEYTYPE key);
}
