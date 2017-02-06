package io.leopard.security.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import io.leopard.jdbc.Jdbc;
import io.leopard.web.passport.PassportValidator;
import io.leopard.web.xparam.SessUidXParam;

public class AdminDaoMysqlImpl implements AdminDao {

	@Autowired
	private Jdbc jdbc;

	private PassportValidator passportValidator;

	@Autowired(required = false) // web环境下次参数为null
	private SessUidXParam sessUidXParam;

	@Override
	public Long getUid(HttpServletRequest request, HttpServletResponse response) {
		return (Long) sessUidXParam.getValue(request, null);
	}

	@Override
	public void forwardLoginUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		passportValidator.showLoginBox(request, response);
	}

	@Override
	public void login(long uid, HttpServletRequest request) {
		Admin admin = this.get(uid);
		if (admin == null) {
			// logger.info("您[" + username + "]不是管理员.");
			throw new AdminNotFoundException("您[" + uid + "]不是管理员.");
		}
	}

	@Override
	public Admin get(long uid) {
		String sql = "SELECT * FROM admin where uid=? limit 1;";
		return jdbc.query(sql, Admin.class, uid);
	}
}
