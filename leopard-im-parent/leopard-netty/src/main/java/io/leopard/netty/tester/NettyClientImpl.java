package io.leopard.netty.tester;

public class NettyClientImpl extends AbstractNettyClient {

	public NettyClientImpl(String host, int port) {
		this.connect(host, port);
	}

	// @Override
	// public void channelRead(byte[] data) {
	// String message = new String(data);
	// System.out.println("read:" + message.trim());
	// }

	public static void main(String[] args) throws Exception {
		NettyClientImpl tester = new NettyClientImpl("182.92.158.15", 5180);
		// NettyTesterImpl tester = new NettyTesterImpl("127.0.0.1", 5180);
		// Thread.sleep(1000 * 2);
		tester.writeln("login 1 123456");
		tester.writeln("text 2 msgid1 hello");
		tester.stop(1000 * 5);
	}

	@Override
	public void onReadline(byte[] data) {
		String message = new String(data);
		System.out.println("read:" + message.trim());
	}
}
