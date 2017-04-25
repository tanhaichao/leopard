package io.leopard.jdbc.logger;

/**
 * 日志输出风格
 * 
 * @author 谭海潮
 *
 */
public enum JdbcLoggerStyle {
	/**
	 * 不做参数解析
	 */
	NO_ARGUMENT_PARSE,
	/**
	 * (短)参数解析
	 */
	ARGUMENT_PARSE,
	/**
	 * 完整参数值解析
	 */
	FULL_ARGUMENT_PARSE;
}
