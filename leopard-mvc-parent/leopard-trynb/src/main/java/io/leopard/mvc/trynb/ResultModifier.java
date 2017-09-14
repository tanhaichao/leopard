package io.leopard.mvc.trynb;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

/**
 * 返回值修改器.
 * 
 * @author 谭海潮
 *
 */
public interface ResultModifier {

	void modify(HttpServletRequest request, HandlerMethod handler, Exception exception, Map<String, Object> map);
}
