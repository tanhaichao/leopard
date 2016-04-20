package io.leopard.web4j.validator;

import io.leopard.burrow.lang.LeopardCheckUtil;

import org.springframework.stereotype.Component;

/**
 * 游戏ID参数合法性验证器.
 * 
 * @author 阿海
 * 
 */
@Component
public class GameIdParameterValidator implements ParameterValidator {

	@Override
	public String getKey() {
		return "gameId";
	}

	@Override
	public void check(String value) {
		LeopardCheckUtil.isValidGameId(value);
		// System.err.println("GameIdParameterValidator:" + value);

	}

}
