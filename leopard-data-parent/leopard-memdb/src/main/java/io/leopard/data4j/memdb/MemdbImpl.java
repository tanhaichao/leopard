package io.leopard.data4j.memdb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MemdbImpl implements Memdb {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected Map<String, String> data;// get

	public MemdbImpl() {
		data = new ConcurrentHashMap<String, String>();
	}

	@Override
	public String get(String key) {
		String value = data.get(key);
		return value;
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		String json = this.get(key);
		return SerializeImpl.getInstance().toObject(json, clazz);
	}

	// @Override
	// public List<String> mget(Set<String> keySet) {
	// List<String> list = new ArrayList<String>();
	// for (String field : keySet) {
	// String value = this.get(field);
	// list.add(value);
	// }
	// return list;
	// }

	// @Override
	// public List<String> mget(String... fields) {
	// List<String> list = new ArrayList<String>();
	// for (String field : fields) {
	// String value = this.get(field);
	// list.add(value);
	// }
	// return list;
	// }

	@Override
	public boolean remove(String key) {
		String value = this.data.remove(key);
		return (value != null);
	}

	@Override
	public long dbSize() {
		return data.size();
	}

	@Override
	public boolean set(String key, String value) {
		this.data.put(key, value);
		return true;
	}

}
