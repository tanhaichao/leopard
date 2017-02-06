package io.leopard.security.admin.version2;

import java.util.Date;
import java.util.List;

import io.leopard.data4j.cache.api.uid.IDelete;
import io.leopard.lang.Paging;

/**
 * 管理员
 * 
 * @author 谭海潮
 *
 */
public interface AdminDao extends IDelete<Admin, Long> {

	@Override
	boolean add(Admin admin);
	
	@Override
	Admin get(Long adminId);
	
	@Override
	boolean delete(Long adminId, long opuid, Date lmodify);

	List<Admin> list(int start, int size);
	
	Paging<Admin> paging(int start, int size);
	
	boolean update(Admin admin);

	Admin getByUsername(String username);
}