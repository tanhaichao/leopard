package io.leopard.web4j.frequency;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 访问频率限制过滤器.
 * 
 * @author 阿海
 * 
 */
public class FrequencyInterceptor implements HandlerInterceptor {
	@Autowired
	private FrequencyLei frequencyLei;

	private Map<Integer, Integer> data = new HashMap<Integer, Integer>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		int hashCode = handler.hashCode();
		Integer seconds = data.get(hashCode);
		if (seconds == null) {
			return true;
		}
		Object account = this.getAccount(request);
		if (account == null) {
			return true;
		}
		// String requestUri = RequestUtil.getRequestContextUri(request);
		String requestUri = request.getRequestURI();// 包含ContextPath也没有问题
		frequencyLei.request(account.toString(), requestUri, seconds);
		return true;
	}

	protected Object getAccount(HttpServletRequest request) {
		// TODO ahai 根据Controller方法中使用的session参数进行判断?
		return request.getAttribute("account");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
