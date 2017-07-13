package io.leopard.web.view.trynb;

import io.leopard.mvc.trynb.TrynbResolver;
import io.leopard.mvc.trynb.model.TrynbInfo;
import io.leopard.web.view.OkTextView;
import io.leopard.web.view.OutSideException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

@Order(2)
@Component
public class OkTextViewTrynbResolver implements TrynbResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception exception, TrynbInfo trynbInfo) {
		Class<?> returnType = handler.getMethod().getReturnType();
		if (!returnType.equals(OkTextView.class)) {
			return null;
		}
		String message;
		if (exception instanceof OutSideException) {
			// 外部接口出错
			message = "560:" + exception.getMessage();
		}
		else {
			message = exception.getMessage();
			if (StringUtils.isEmpty(message)) {
				message = exception.toString();
			}
		}
		logger.error(exception.getMessage(), exception);
		return new OkTextView(message);
	}

}
