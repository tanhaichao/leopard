package io.leopard.web.mvc.method;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 * InvocableHandlerMethod创建器
 * 
 * @author 谭海潮
 *
 */
public interface InvocableHandlerMethodCreator {
	ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod);
}
