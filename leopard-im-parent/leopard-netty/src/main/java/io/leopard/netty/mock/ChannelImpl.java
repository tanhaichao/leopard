package io.leopard.netty.mock;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.SocketAddress;

public class ChannelImpl implements Channel {
	private String remoteAddress;

	public ChannelImpl(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> key) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public int compareTo(Channel o) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public EventLoop eventLoop() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public Channel parent() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelConfig config() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public boolean isOpen() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public boolean isRegistered() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public boolean isActive() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelMetadata metadata() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public SocketAddress localAddress() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public SocketAddress remoteAddress() {
		return new SocketAddressImpl(this.remoteAddress);
	}

	@Override
	public ChannelFuture closeFuture() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public boolean isWritable() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public Unsafe unsafe() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelPipeline pipeline() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ByteBufAllocator alloc() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelPromise newPromise() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture newSucceededFuture() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture newFailedFuture(Throwable cause) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelPromise voidPromise() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture disconnect() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture close() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture deregister() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture disconnect(ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture close(ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture deregister(ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public Channel read() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture write(Object msg) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture write(Object msg, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public Channel flush() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg) {
		throw new UnsupportedOperationException("Not Impl.");

	}

}
