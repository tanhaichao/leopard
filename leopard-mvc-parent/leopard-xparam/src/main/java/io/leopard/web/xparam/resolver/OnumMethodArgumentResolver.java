package io.leopard.web.xparam.resolver;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import io.leopard.burrow.lang.inum.EnumUtil;
import io.leopard.burrow.lang.inum.Inum;
import io.leopard.burrow.lang.inum.Onum;
import io.leopard.burrow.lang.inum.Snum;

/**
 * 枚举类型解析器
 * 
 * @author ahai
 *
 */
@Component
public class OnumMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		if (Onum.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Object value = RequestBodyParser.getParameter(request, name);
		if (value == null) {
			return null;
		}
		Class<?> clazz = parameter.getParameterType();
		if (Snum.class.isAssignableFrom(clazz)) {
			String key = (String) value;
			return EnumUtil.toEnum(key, (Class<Enum>) clazz);
		}
		else if (Inum.class.isAssignableFrom(clazz)) {
			int key;
			if (value instanceof Integer) {
				key = (int) value;
			}
			else {
				key = Integer.parseInt((String) value);
			}
			return EnumUtil.toEnum(key, (Class<Enum>) clazz);
		}
		else {
			throw new RuntimeException("未知枚举类型[" + clazz.getName() + "].");
		}
	}

}