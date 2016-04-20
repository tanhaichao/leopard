package io.leopard.web4j.permission;

import io.leopard.data4j.cache.api.IGet;


public interface PermissionDao extends IGet<Permission, PermissionKey> {

	@Override
	boolean add(Permission permission);

	@Override
	Permission get(PermissionKey permissionKey);

}
