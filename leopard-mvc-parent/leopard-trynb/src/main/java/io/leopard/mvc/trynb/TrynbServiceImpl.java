package io.leopard.mvc.trynb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.leopard.core.exception.ApiException;
import io.leopard.mvc.trynb.model.ErrorConfig;
import io.leopard.mvc.trynb.model.ExceptionConfig;
import io.leopard.mvc.trynb.model.TrynbInfo;
import io.leopard.mvc.trynb.translate.Translater;
import io.leopard.mvc.trynb.translate.TranslaterImpl;

public class TrynbServiceImpl implements TrynbService {
	protected Log logger = LogFactory.getLog(this.getClass());

	private TrynbApi trynbApi = new TrynbApiImpl();

	private final TrynbDao trynbDao = new TrynbDaoImpl();
	private final TrynbLogger trynbLogger = new TrynbLoggerImpl();

	private static Translater translater = TranslaterImpl.getInstance();

	protected ExceptionConfig find(ErrorConfig errorConfig, Exception exception) {

		List<ExceptionConfig> exceptionConfigList = errorConfig.getExceptionConfigList();
		String exceptionClassName = exception.getClass().getName();

		for (ExceptionConfig exceptionConfig : exceptionConfigList) {
			boolean match = match(exceptionConfig.getType(), exceptionClassName);
			if (match) {
				return exceptionConfig;
			}
		}
		return null;
	}

	@Override
	public TrynbInfo parse(HttpServletRequest request, String uri, Exception exception) {
		{
			TrynbInfo trynbInfo = trynbApi.parse(trynbLogger, request, uri, exception);
			if (trynbInfo != null) {
				return trynbInfo;
			}
		}
		ErrorConfig errorConfig = trynbDao.find(uri);

		if (exception instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException e2 = (MethodArgumentTypeMismatchException) exception;
			Exception e = (Exception) e2.getCause().getCause();
			if (e != null) {
				exception = e;
			}
			else {
				exception = (Exception) e2.getCause();
			}
		}

		ExceptionConfig exceptionConfig = this.find(errorConfig, exception);
		TrynbInfo trynbInfo = new TrynbInfo();

		String message;
		if (exceptionConfig == null || StringUtils.isEmpty(exceptionConfig.getMessage())) {
			message = parseMessage(trynbInfo, exception);
		}
		else {
			message = exceptionConfig.getMessage();
			trynbInfo.setTrynbMessage(true);
		}

		String statusCode = this.parseStatusCode(exceptionConfig, request, uri, exception);
		trynbInfo.setPage(errorConfig.getPage());
		trynbInfo.setMessage(message);
		trynbInfo.setException(exception);
		trynbInfo.setStatusCode(statusCode);
		return trynbInfo;
	}

	protected String parseMessage(TrynbInfo trynbInfo, Exception exception) {
		if (translater.isEnable()) {
			if (exception instanceof ApiException) {
				String apiMessage = ((ApiException) exception).getApiMessage();
				if (apiMessage != null) {
					// logger.info("apiMessage:" + apiMessage);
					trynbInfo.setApiMessage(true);
					return apiMessage;
				}
			}
		}
		// exception.printStackTrace();
		String message = ErrorUtil.parseMessage(exception);
		if (message == null) {
			return null;
		}
		String message2 = translater.translate(message);
		if (message2 != null) {
			trynbInfo.setTranslate(true);
			return message2;
		}
		return message;
	}

	protected String parseStatusCode(ExceptionConfig exceptionConfig, HttpServletRequest request, String uri, Exception exception) {
		if (exceptionConfig == null) {
			// logger.error("匹配[" + uri + "." + exception.getClass().getName() +
			// "]不到exception配置");
			// this.error(request, uri, exception);
			trynbLogger.error(request, uri, exception);
			return exception.getClass().getSimpleName();
		}
		String logType = ExceptionConfig.getType(exceptionConfig.getLog());
		if ("error".equals(logType)) {
			// this.error(request, uri, exception);
			trynbLogger.error(request, uri, exception);
		}
		else if ("warn".equals(logType)) {
			// logger.warn("uri:" + uri + " message:" + exception.getMessage());
			trynbLogger.warn(request, uri, exception);
		}
		else if ("info".equals(logType)) {
			// logger.info("uri:" + uri + " message:" + exception.getMessage());
			trynbLogger.info(request, uri, exception);
		}
		else if ("debug".equals(logType)) {
			// logger.debug("uri:" + uri + " message:" + exception.getMessage());
			trynbLogger.debug(request, uri, exception);
		}
		if (StringUtils.isEmpty(exceptionConfig.getStatusCode())) {
			return exception.getClass().getSimpleName();
		}
		return exceptionConfig.getStatusCode();
	}

	private static boolean match(String type, String exceptionClassName) {
		if (type.indexOf(".") == -1) {
			if (exceptionClassName.endsWith(type)) {
				return true;
			}
		}
		if (exceptionClassName.equals(type)) {
			return true;
		}
		return false;
	}

}
