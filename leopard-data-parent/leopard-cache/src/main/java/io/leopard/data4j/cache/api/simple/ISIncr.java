package io.leopard.data4j.cache.api.simple;

public interface ISIncr<KEYTYPE> {

	long incr(KEYTYPE key, long count);

	long get(KEYTYPE key);
}
