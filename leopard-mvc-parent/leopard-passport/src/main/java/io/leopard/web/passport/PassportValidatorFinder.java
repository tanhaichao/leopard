package io.leopard.web.passport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.burrow.lang.AssertUtil;

/**
 * 通行证验证器查找器
 * 
 * @author 谭海潮
 *
 */
@Component
public class PassportValidatorFinder {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private List<PassportValidator> passportValidatorList;

	public PassportValidator find(String type) {
		AssertUtil.assertNotEmpty(type, "登录类型不能为空.");
		if (passportValidatorList != null) {
			for (PassportValidator validator : passportValidatorList) {
				if (type.equals(PassportUtil.getSessionAttributeName(validator))) {
					// System.err.println("find:" + validator + " sessionKey:" + getSessionKey(validator));
					return validator;
				}
			}
		}
		throw new IllegalArgumentException("找不到通行证验证器[" + type + "]的实现.");
	}

	/**
	 * 根据Handler匹配通行证登录验证器
	 * 
	 * @param request
	 * @param handler
	 * @return
	 */
	public List<PassportValidator> find(HttpServletRequest request, Object handler) {
		List<PassportValidator> list = new ArrayList<PassportValidator>();
		if (passportValidatorList != null) {
			for (PassportValidator validator : this.passportValidatorList) {
				boolean isNeedCheckLogin = PassportUtil.isNeedCheckLogin(validator, request, handler);
				// logger.info("validator:" + validator + " isNeedCheckLogin:" + isNeedCheckLogin + " request:" + request.getRequestURI());
				if (isNeedCheckLogin) {
					// System.err.println("validator:" + Finder.getSessionKey(validator) + " isNeedCheckLogin:" + isNeedCheckLogin + " request:" + request.getRequestURI());
					list.add(validator);
				}
			}
		}
		return list;
	}
}
