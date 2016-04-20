package io.leopard.data.signature;

public interface SignatureDao {

	boolean remove(String key);

	boolean add(String member);

	boolean exist(String member);
}
