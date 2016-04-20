package io.leopard.web4j.validator;

import io.leopard.burrow.lang.LeopardCheckUtil;

import org.springframework.stereotype.Component;

/**
 * 服务器ID参数合法性验证器.
 * 
 * @author 阿海
 * 
 */
@Component
public class ServerIdParameterValidator implements ParameterValidator {

	@Override
	public String getKey() {
		return "serverId";
	}

	@Override
	public void check(String value) {
		LeopardCheckUtil.isValidServerId(value);

	}

}
