package io.leopard.data4j.pubsub;

import io.leopard.redis.Redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Publisher {

	private static Map<IPubSub, PubSubRsyncImpl> map = new ConcurrentHashMap<IPubSub, PubSubRsyncImpl>();

	public static void listen(final IPubSub pub, Redis redis) {
		if (map.containsKey(pub)) {
			throw new IllegalArgumentException("Pub[" + pub + "]已经监听过.");
		}
		String channel = getChannel(pub);
		PubSubRsyncImpl pubRsyncImpl = new PubSubRsyncImpl(redis, channel) {
			@Override
			public void subscribe(String message, boolean isMySelf) {
				pub.subscribe(message, isMySelf);
			}
		};
		pubRsyncImpl.init();
		map.put(pub, pubRsyncImpl);
	}

	protected static String getChannel(final IPubSub pub) {
		Class<?> clazz = pub.getClass();
		String classSimpleName = clazz.getSimpleName();
		return "pubsub:" + classSimpleName;
	}

	public static boolean publish(IPubSub pub, String message) {
		PubSubRsyncImpl pubRsyncImpl = map.get(pub);
		return pubRsyncImpl.publish(message);
	}

}
