package io.leopard.security.admin.version2;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.LeopardValidUtil;
import io.leopard.burrow.util.ListUtil;
import io.leopard.burrow.util.StringUtil;
import io.leopard.core.exception.forbidden.PasswordWrongException;
import io.leopard.core.exception.invalid.UsernameInvalidException;
import io.leopard.core.exception.other.AdminDisabledException;
import io.leopard.data.kit.password.LoginInfo;
import io.leopard.data.kit.password.PassportTokenUtil;
import io.leopard.data.kit.password.PasswordUtil;
import io.leopard.data.kit.password.PasswordVerifier;
import io.leopard.data.kit.password.PasswordVerifierImpl;
import io.leopard.jdbc.Jdbc;
import io.leopard.json.Json;

@Service
public class AdminBizImpl implements AdminBiz {

	@Autowired
	private AdminService adminService;

	@Resource // 允许为空
	private AdminApi adminApi;

	@Autowired
	private Jdbc jdbc;

	private PasswordVerifier passwordVerifier = new PasswordVerifierImpl();

	@Override
	public boolean login(long adminId, String password) throws AdminNotFoundException, PasswordWrongException, AdminDisabledException {
		if (adminId <= 0) {
			throw new AdminIdInvalidException(adminId);
		}
		AdminVO admin = adminApi.get(adminId);
		if (admin == null) {
			throw new AdminNotFoundException(adminId);
		}
		if (admin.isDisabled()) {
			throw new AdminDisabledException(admin.getUsername());
		}

		String salt = admin.getSalt();
		String dbPassword = admin.getPassword();

		boolean correctPassword = passwordVerifier.verify(Long.toString(adminId), password, salt, dbPassword);
		if (!correctPassword) {
			throw new PasswordWrongException("密码[" + adminId + "]不正确.");
		}
		return correctPassword;
	}

	@Override
	public LoginInfo login(String username, String password) throws AdminNotFoundException, PasswordWrongException {
		if (!LeopardValidUtil.isValidUsername(username)) {
			throw new UsernameInvalidException(username);
		}
		AdminVO admin = adminApi.getByUsername(username);
		if (admin == null) {
			throw new AdminNotFoundException(0);
		}
		String salt = admin.getSalt();
		String dbPassword = admin.getPassword();

		boolean correctPassword = passwordVerifier.verify(username, password, salt, dbPassword);
		if (!correctPassword) {
			throw new PasswordWrongException("密码[" + username + "]不正确.");
		}

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUid(admin.getAdminId());
		loginInfo.setAcccount(admin.getUsername());
		loginInfo.setToken(PassportTokenUtil.makeToken(admin.getPassword()));
		loginInfo.setUser(admin);
		return loginInfo;
	}

	@Override
	public String getName(long adminId) throws AdminNotFoundException {
		AdminVO admin = this.adminApi.get(adminId);
		if (admin == null) {
			throw new AdminNotFoundException(adminId);
		}
		return admin.getName();
	}

	@Override
	public boolean add(String username, String password, String name) {
		String salt = StringUtil.uuid().substring(0, 7);

		String encryptedPassword = PasswordUtil.encryptPassword(password, salt);

		Admin admin = new Admin();
		admin.setAdminId(System.currentTimeMillis());
		admin.setUsername(username);
		admin.setPassword(password);
		admin.setName(name);
		admin.setSalt(salt);
		admin.setPassword(encryptedPassword);
		return this.adminService.add(admin);
	}

	public static <T> T convert2(Object obj, Class<T> clazz) {
		if (obj == null) {
			return null;
		}
		String json = Json.toJson(obj);
		return Json.toObject(json, clazz, true);
	}

	@Override
	public AdminVO getByUsername(String username) {
		if (this.adminApi == null) {
			Admin admin = adminService.getByUsername(username);
			return convert2(admin, AdminVO.class);
		}
		return this.adminApi.getByUsername(username);
	}

	@Override
	public AdminVO get(long adminId) {
		if (this.adminApi == null) {
			Admin admin = adminService.get(adminId);
			return convert2(admin, AdminVO.class);
		}
		return this.adminApi.get(adminId);
	}

	@Override
	public boolean isTopdomainCookie() {
		if (this.adminApi == null) {
			return false;
		}
		return adminApi.isTopdomainCookie();
	}

	@Override
	public boolean updatePassword(long adminId, String oldPassword, String newPassword, String confirmPassword) throws PasswordWrongException, AdminDisabledException, AdminNotFoundException {
		AssertUtil.assertNotEmpty(oldPassword, "旧密码不能为空.");
		AssertUtil.assertNotEmpty(newPassword, "新密码不能为空.");
		AssertUtil.assertNotEmpty(confirmPassword, "确认密码不能为空.");
		// CheckUtil.isPassword(oldPassword);
		// CheckUtil.isPassword(newPassword);
		// CheckUtil.isPassword(confirmPassword);

		if (!newPassword.equals(confirmPassword)) {
			throw new IllegalArgumentException("两次密码不一致.");
		}

		// Admin admin = this.adminService.get(sessAdminId);
		this.login(adminId, oldPassword);

		String salt = StringUtil.uuid().substring(0, 7);
		String encryptedPassword = PasswordUtil.encryptPassword(newPassword, salt);
		String sql = "update gen_admin set salt=?,password=? where adminId=?";
		return jdbc.updateForBoolean(sql, salt, encryptedPassword, adminId);
	}

	@Override
	public boolean addRole(String username, String role) throws AdminNotFoundException {
		Admin admin = adminService.getByUsername(username);
		if (admin == null) {
			throw new AdminNotFoundException(username);
		}
		List<String> roleList = admin.getRoleList();
		roleList = ListUtil.defaultList(roleList);
		if (roleList.contains(role)) {
			return false;
		}
		roleList.add(role);
		return this.updateRoles(username, roleList);
	}

	@Override
	public boolean deleteRole(String username, String role) throws AdminNotFoundException {
		Admin admin = adminService.getByUsername(username);
		if (admin == null) {
			throw new AdminNotFoundException(username);
		}
		List<String> roleList = admin.getRoleList();
		roleList = ListUtil.defaultList(roleList);
		roleList.remove(role);

		return this.updateRoles(username, roleList);
	}

	protected boolean updateRoles(String username, List<String> roleList) {
		String roles = Json.toJson(roleList);
		String sql = "update admin set roleList=? where username=?";
		return jdbc.updateForBoolean(sql, roles, username);
	}

	@Override
	public boolean disable(long adminId) {
		String sql = "update gen_admin set disabled=1 where adminId=?";
		return this.jdbc.updateForBoolean(sql, adminId);
	}

	@Override
	public boolean enable(long adminId) {
		String sql = "update gen_admin set disabled=0 where adminId=?";
		return this.jdbc.updateForBoolean(sql, adminId);
	}
}
