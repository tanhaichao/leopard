package io.leopard.data4j.cache.api.simple;

public interface ISGet<KEYTYPE, VALUETYPE> {
	boolean add(KEYTYPE key, VALUETYPE value);

	VALUETYPE get(KEYTYPE key);
}
