package io.leopard.netty.mock;

import java.net.SocketAddress;

public class SocketAddressImpl extends SocketAddress {

	private static final long serialVersionUID = 1L;

	private String remoteAddress;

	public SocketAddressImpl(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String toString() {
		return remoteAddress;
	}
}
