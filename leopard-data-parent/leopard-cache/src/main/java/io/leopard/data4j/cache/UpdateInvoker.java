package io.leopard.data4j.cache;

public interface UpdateInvoker<BEAN> {
	public boolean update(BEAN bean);
}