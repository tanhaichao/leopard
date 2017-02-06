package io.leopard.security.admin.version2;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.leopard.lang.Paging;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Override
	public boolean add(Admin admin) {
		Date posttime = new Date();
		admin.setPosttime(posttime);
		return adminDao.add(admin);
	}

	@Override
	public Admin get(long adminId) {
		// CheckUtil.isAdminId(adminId);
		return adminDao.get(adminId);
	}

	@Override
	public boolean delete(long adminId, long opuid) {
		// CheckUtil.isAdminId(adminId);
		return adminDao.delete(adminId, opuid, new Date());
	}

	@Override
	public List<Admin> list(int start, int size) {
		List<Admin> list = adminDao.list(start, size);
		return list;
	}

	@Override
	public Paging<Admin> paging(int start, int size) {
		Paging<Admin> paging = adminDao.paging(start, size);
		return paging;
	}

	@Override
	public Admin getByUsername(String username) {
		return adminDao.getByUsername(username);
	}

	@Override
	public long getAdminId(String username) {
		Admin admin = this.getByUsername(username);
		if (admin == null) {
			return 0;
		}
		return admin.getAdminId();
	}
}