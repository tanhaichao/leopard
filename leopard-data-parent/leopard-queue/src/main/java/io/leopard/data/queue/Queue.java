package io.leopard.data.queue;

public interface Queue {

	void publish(String key, String message);

	void subscribe(String key, IConsumer callback);

}
