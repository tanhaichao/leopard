package io.leopard.data.cacher;

public interface Cacher<KEYTYPE, VALUETYPE> {

	VALUETYPE get(KEYTYPE key);

	boolean remove(KEYTYPE key);

}
