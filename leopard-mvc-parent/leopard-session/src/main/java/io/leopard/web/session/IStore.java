package io.leopard.web.session;

public interface IStore {

	boolean isEnable();

	String get(String key);

	String set(String key, String value, int seconds);

	Long del(String key);

	// String toJson(Object obj);
	//
	// <T> T toObject(String json, Class<T> clazz);

	// Map<String, Object> getSession(String sid);
	//
	// void saveSession(String sid, Map<String, Object> session, int seconds);
	//
	// void removeSession(String sid);

}
