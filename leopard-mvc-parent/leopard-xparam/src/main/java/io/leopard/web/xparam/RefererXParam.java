package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 访问来源
 * 
 * @author 阿海
 * 
 */
@Service
public class RefererXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String referer = request.getHeader("referer");
		return referer;
	}

	@Override
	public String getKey() {
		return "referer";
	}

}
