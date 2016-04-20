package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法游戏ID.
 * 
 * @author 阿海
 * 
 */
public class GameIdInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public GameIdInvalidException(String gameId) {
		super("非法游戏ID[" + gameId + "].");
	}

}
