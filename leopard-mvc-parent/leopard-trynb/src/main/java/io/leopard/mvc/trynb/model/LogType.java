package io.leopard.mvc.trynb.model;

public enum LogType {
	ERROR, WARN, INFO, DEBUG

	// if ("error".equals(logType)) {
	// this.error(request, uri, exception);
	// }
	// else if ("warn".equals(logType)) {
	// if (exception instanceof NotLoginException) {
	// String cookie = request.getHeader("Cookie");
	// logger.warn("Cookie:" + cookie);
	// logger.warn("uri:" + uri + " message:" + exception.getMessage(), exception);
	// }
	// else {
	// logger.warn("uri:" + uri + " message:" + exception.getMessage());
	// }
	// }
	// else if ("info".equals(logType)) {
	// logger.info("uri:" + uri + " message:" + exception.getMessage());
	// }
	// else if ("debug".equals(logType)) {
	// logger.debug("uri:" + uri + " message:" + exception.getMessage());
	// }
}
