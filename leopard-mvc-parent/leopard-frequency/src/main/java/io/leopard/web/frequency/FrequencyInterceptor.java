package io.leopard.web.frequency;

import io.leopard.web.servlet.RegisterHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 访问频率限制过滤器.
 * 
 * @author 阿海
 * 
 */
// TODO ahai 在site项目必须实现BeanPostProcessor接口才能成功配置拦截器.
@Component
public class FrequencyInterceptor extends RegisterHandlerInterceptor {

	private FrequencyResolver frequencyResolver = new FrequencyResolver();
	private FrequencyChecker frequencyChecker = new FrequencyChecker();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Integer seconds = frequencyResolver.getSeconds(handler);
		// System.out.println("FrequencyInterceptor preHandle:" + handler + " seconds:" + seconds);
		if (seconds == null || seconds <= 0) {
			return true;
		}

		frequencyChecker.check(request, seconds, handler);
		return true;
	}

}
