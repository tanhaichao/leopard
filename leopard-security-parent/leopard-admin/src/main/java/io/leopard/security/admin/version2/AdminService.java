package io.leopard.security.admin.version2;

import java.util.List;

import io.leopard.lang.Paging;

/**
 * 管理员
 * 
 * @author 谭海潮
 *
 */
public interface AdminService {

	boolean add(Admin admin);

	Admin get(long adminId);

	boolean delete(long adminId, long opuid);

	List<Admin> list(int start, int size);

	Paging<Admin> paging(int start, int size);

	Admin getByUsername(String username);

	long getAdminId(String username);
}