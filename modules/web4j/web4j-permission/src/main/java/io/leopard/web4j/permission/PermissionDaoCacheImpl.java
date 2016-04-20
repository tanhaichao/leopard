package io.leopard.web4j.permission;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.data4j.cache.CacheLoader;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class PermissionDaoCacheImpl extends ContextImpl implements PermissionDao {

	@Resource
	private PermissionDao permissionDaoMysqlImpl;
	@Resource
	private PermissionDao permissionDaoMemoryImpl;

	@Override
	public boolean add(Permission permission) {
		this.permissionDaoMemoryImpl.add(permission);
		return permissionDaoMysqlImpl.add(permission);
	}

	@Override
	public Permission get(PermissionKey permissionKey) {
		return CacheLoader.get(permissionDaoMemoryImpl, permissionDaoMysqlImpl, permissionKey);
	}

}
