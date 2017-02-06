package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

import io.leopard.web.xparam.api.UserinfoResolverImpl;

/**
 * 根据username解析uid.
 * 
 * @author 阿海
 * 
 */
@Service
public class UidXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String uid = request.getParameter("uid");
		if (StringUtils.isNotEmpty(uid)) {
			return NumberUtils.toLong(uid);
		}
		String passport = request.getParameter("username");
		if (StringUtils.isEmpty(passport)) {
			passport = request.getParameter("passport");
			if (StringUtils.isEmpty(passport)) {
				return 0;
			}
		}
		return UserinfoResolverImpl.getInstance().getUid(passport);
	}

	@Override
	public String getKey() {
		return "uid";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }
}
