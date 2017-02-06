package io.leopard.rpc;

import io.leopard.httpnb.HttpHeaderPostImpl;

public class RpcHttpHeader extends HttpHeaderPostImpl {

	public RpcHttpHeader(long timeout) {
		super(timeout);

		super.setUserAgent("Leopard-Rpc");
	}

}
