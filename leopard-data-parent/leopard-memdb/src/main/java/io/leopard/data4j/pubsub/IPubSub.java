package io.leopard.data4j.pubsub;

/**
 * 发布订阅.
 * 
 * @author ahai
 * 
 */
public interface IPubSub {

	// /**
	// * 发布消息
	// *
	// * @param message
	// * @return
	// */
	// boolean publish(String message);

	/**
	 * 订阅消息.
	 * 
	 * @param message
	 */
	void subscribe(String message, boolean isMySelf);
}
