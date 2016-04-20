package io.leopard.web4j.validator;

import io.leopard.burrow.lang.LeopardCheckUtil;

import org.springframework.stereotype.Component;

/**
 * 通行证参数合法性验证器.
 * 
 * @author 阿海
 * 
 */
@Component
public class PassportParameterValidator implements ParameterValidator {

	@Override
	public String getKey() {
		return "passport";
	}

	@Override
	public void check(String value) {
		LeopardCheckUtil.isValidPassport(value);

	}

}
