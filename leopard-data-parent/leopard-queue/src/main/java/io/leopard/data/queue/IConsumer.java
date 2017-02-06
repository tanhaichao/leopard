package io.leopard.data.queue;

public interface IConsumer {

	void consume(String message) throws Exception;

}
