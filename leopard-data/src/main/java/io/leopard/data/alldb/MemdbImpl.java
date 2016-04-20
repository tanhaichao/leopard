package io.leopard.data.alldb;

import io.leopard.json.Json;

public class MemdbImpl {

	private io.leopard.data4j.memdb.Memdb memdb;

	public io.leopard.data4j.memdb.Memdb getMemdb() {
		return memdb;
	}

	public void setMemdb(io.leopard.data4j.memdb.Memdb memdb) {
		this.memdb = memdb;
	}

	public boolean set(Object bean, Object... keys) {
		String key = this.getKey(keys);
		String json = Json.toJson(bean);
		return this.memdb.set(key, json);
	}

	public <T> T get(Class<T> elementType, Object... keys) {
		String key = this.getKey(keys);
		return memdb.get(key, elementType);
	}

	protected String getKey(Object... keys) {
		if (keys.length == 1) {
			return keys[0].toString();
		}
		StringBuilder sb = new StringBuilder();
		for (Object key : keys) {
			if (sb.length() > 0) {
				sb.append(":");
			}
			sb.append(key.toString());
		}
		return sb.toString();
	}
}
