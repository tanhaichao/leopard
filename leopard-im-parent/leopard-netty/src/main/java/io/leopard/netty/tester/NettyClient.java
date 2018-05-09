package io.leopard.netty.tester;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public interface NettyClient {

	public void connect(String host, int port);

	public void stop(long millis);

	public void sync(ChannelFuture future);

	public void onReadline(byte[] data);

	// public void channelRead(String message);

	void channelActive(ChannelHandlerContext ctx);

	void channelInactive(ChannelHandlerContext ctx) throws Exception;

	void write(String message);

	void writeln(String message);

	void write(byte[] data);
}
