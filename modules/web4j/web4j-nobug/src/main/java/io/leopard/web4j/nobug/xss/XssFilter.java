package io.leopard.web4j.nobug.xss;

import org.apache.commons.logging.Log;

/**
 * Xss内容过滤器.
 * 
 * @author 阿海
 * 
 */
public interface XssFilter {

	// String filter(String body);

	String filter(Log logger, String body);

}
