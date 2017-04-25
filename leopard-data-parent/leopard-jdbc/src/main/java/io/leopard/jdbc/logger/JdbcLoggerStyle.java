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
	 * 参数解析
	 */
	ARGUMENT_PARSE,
	/**
	 * 短参数值解析
	 */
	SHORT_ARGUMENT_PARSE;
}
