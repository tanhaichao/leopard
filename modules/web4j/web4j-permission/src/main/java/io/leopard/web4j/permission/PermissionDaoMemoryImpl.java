package io.leopard.web4j.permission;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.lang.SynchronizedLRUMap;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PermissionDaoMemoryImpl extends ContextImpl implements PermissionDao {

	// private Memdb memdb = new MemdbLruImpl(100);

	Map<String, String> data = new SynchronizedLRUMap<String, String>(10, 100);

	protected String getKey(PermissionKey permissionKey) {
		return permissionKey.getUri() + ":" + permissionKey.getIp();
	}

	@Override
	public boolean add(Permission permission) {
		String key = this.getKey(permission);
		data.put(key, Json.toJson(permission));
		return true;
	}

	@Override
	public Permission get(PermissionKey permissionKey) {
		String key = this.getKey(permissionKey);
		String json = data.get(key);
		Permission permission = Json.toObject(json, Permission.class);
		return permission;
	}

}
