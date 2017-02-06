package io.leopard.security.admin.version2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import io.leopard.web.xparam.Nologin;
import io.leopard.web.xparam.NotLoginException;
import io.leopard.web.xparam.XParam;
import io.leopard.web.xparam.XParamUtil;

/**
 * 获取当前登录的管理员ID.
 * 
 * @author 谭海潮
 *
 */
@Component
public class SessAdminIdXParam implements XParam {

	@Override
	public String getKey() {
		return "sessAdminId";
	}

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		// 分布式session还不够好，Long类型存进去再拿出来会变成Integer，这里做兼容
		Number sessAdminId = (Number) request.getSession().getAttribute("sessAdminId");
		if (sessAdminId == null) {
			Nologin nologin = parameter.getMethodAnnotation(Nologin.class);
			if (nologin == null) {
				String ip = XParamUtil.getProxyIp(request);
				throw new NotLoginException("您[" + ip + "]未登录.");
			}
			else {
				return 0;
			}
		}
		return sessAdminId.longValue();
	}

}
