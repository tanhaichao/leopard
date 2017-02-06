package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取当前登录的自定义用户Id
 * 
 * @author 阿海
 * 
 */
@Service
public class SessUserIdXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		// throw new NotImplementedException();
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public String getKey() {
		return "sessUserId";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
