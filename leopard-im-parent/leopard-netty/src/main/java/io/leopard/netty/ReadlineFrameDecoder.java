package io.leopard.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

public abstract class ReadlineFrameDecoder extends LineBasedFrameDecoder {

	public ReadlineFrameDecoder(int maxLength) {
		super(maxLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		ByteBuf in = (ByteBuf) super.decode(ctx, buffer);
		if (in != null) {
			byte[] data = new byte[in.readableBytes()];
			in.readBytes(data);
			onReadline(ctx, data);
			in.release();
		}
		return null;
	}

	public abstract void onReadline(ChannelHandlerContext ctx, byte[] data);
}
