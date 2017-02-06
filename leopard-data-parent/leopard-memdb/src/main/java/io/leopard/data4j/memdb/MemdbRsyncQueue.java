package io.leopard.data4j.memdb;

public interface MemdbRsyncQueue {

	public static final String TYPE_SET = "set";
	public static final String TYPE_REMOVE = "remove";

	boolean add(String type, String key, String value, boolean isMySelf);

	void init();

	void destroy();

}
