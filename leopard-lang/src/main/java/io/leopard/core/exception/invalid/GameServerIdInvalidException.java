package io.leopard.core.exception.invalid;

import io.leopard.core.exception.LeopardRuntimeException;

public class GameServerIdInvalidException extends LeopardRuntimeException {
	private static final long serialVersionUID = -5811456149078390234L;

	public GameServerIdInvalidException(String gameId, String serverId) {
		super("非法游戏服务器[" + gameId + "." + serverId + "].");
	}

}
