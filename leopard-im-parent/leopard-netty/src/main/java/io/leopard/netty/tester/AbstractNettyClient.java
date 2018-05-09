package io.leopard.netty.tester;

import io.leopard.netty.ReadlineFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class AbstractNettyClient implements NettyClient {

	protected EventLoopGroup workerGroup = new NioEventLoopGroup();

	@Override
	public void connect(String host, int port) {
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DefaultClientIntHandler(10240));
				}
			});
			b.connect(host, port).sync();
			// this.sync(future);
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		this.checkChannelActive();
	}

	protected void checkChannelActive() {
		if (!isChannelActive) {
			for (int i = 0; i < 3; i++) {
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isChannelActive) {
					break;
				}
			}
		}
	}

	public void stop() {
		this.stop(10);
	}

	@Override
	public void stop(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		workerGroup.shutdownGracefully();
		// System.out.println("stop");
	}

	@Override
	public void sync(ChannelFuture future) {
		try {
			future.channel().closeFuture().sync();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public class DefaultClientIntHandler extends ReadlineFrameDecoder {

		// // 接收server端的消息，并打印出来
		// @Override
		// public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// ByteBuf result = (ByteBuf) msg;
		// byte[] data = new byte[result.readableBytes()];
		// result.readBytes(data);
		// AbstractNettyClient.this.channelRead(data);
		// result.release();
		// }

		public DefaultClientIntHandler(int maxLength) {
			super(maxLength);
		}

		// 连接成功后，向server发送消息
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			AbstractNettyClient.this.channelActive(ctx);
		}

		// public void lineRead(ChannelHandlerContext ctx, ByteBuf in) { // (2)
		// byte[] bytes = new byte[in.readableBytes()];
		// in.readBytes(bytes);
		// }

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			super.channelInactive(ctx);
			AbstractNettyClient.this.channelInactive(ctx);
		}

		@Override
		public void onReadline(ChannelHandlerContext ctx, byte[] data) {
			AbstractNettyClient.this.onReadline(data);
		}
	}

	protected ChannelHandlerContext ctx;

	@Override
	public void write(String message) {
		this.write(message.getBytes());
	}

	private boolean isChannelActive = false;

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		this.isChannelActive = true;
		// System.out.println("channelActive:" + ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void write(byte[] data) {
		if (!isChannelActive) {
			throw new RuntimeException("当前连接未活跃.");
		}
		ByteBuf encoded = ctx.alloc().buffer(data.length);
		encoded.writeBytes(data);
		ctx.write(encoded);
		ctx.flush();
	}

	@Override
	public void writeln(String message) {
		this.write(message + "\n");
	}
	// public static void main(String[] args) throws Exception {
	// AbstractNettyTester client = new AbstractNettyTester();
	// client.connect("182.92.158.15", 5180);
	// }

}
