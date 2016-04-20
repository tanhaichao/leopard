package io.leopard.web4j.trynb.resolver;

import io.leopard.core.exception.other.OutSideException;
import io.leopard.web4j.trynb.model.TrynbInfo;
import io.leopard.web4j.view.OkTextView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class OkTextViewTrynbResolver implements TrynbResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception, TrynbInfo trynbInfo) {

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
