package io.leopard.web4j.admin.dao;

import io.leopard.burrow.AutoResource;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.core.exception.LeopardRuntimeException;
import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.jdbc.StatementParameter;
import io.leopard.web4j.admin.Admin;
import io.leopard.web4j.parameter.SessUsernamePageParameter;
import io.leopard.web4j.passport.PassportValidateLei;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

public class AdminDaoMysqlImpl extends ContextImpl implements AdminDao {
	@Autowired
	private Jdbc jdbc;

	@AutoResource
	private PassportValidateLei passportValidateLei;

	@Autowired(required = false)
	private SessUsernamePageParameter sessUsernamePageParameter;

	@Override
	public String getUsername(HttpServletRequest request, HttpServletResponse response) {
		return sessUsernamePageParameter.getValue(request);
	}

	@Override
	public void forwardLoginUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		passportValidateLei.showLoginBox(request, response);
	}

	@Override
	public void login(String username, HttpServletRequest request) {
		Admin admin = this.get(username);
		if (admin == null) {
			logger.info("您[" + username + "]不是管理员.");
			throw new LeopardRuntimeException("您" + username + "不是管理员.");
		}
	}

	@Override
	public Admin get(String username) {
		logger.info("get:" + username);
		String sql = "SELECT * FROM admin where username=? limit 1;";
		StatementParameter param = new StatementParameter();
		param.setString(username);

		Admin admin = jdbc.query(sql, Admin.class, param);
		return admin;
	}
}
