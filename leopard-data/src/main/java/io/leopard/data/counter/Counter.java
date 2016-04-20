package io.leopard.data.counter;

import java.util.concurrent.TimeUnit;

/**
 * 计数器.
 * 
 * @author 阿海
 *
 */
public interface Counter {

	long incr(String name, TimeUnit unit, long uid);

	void check(String name, TimeUnit unit, long uid, int max) throws MaxCountException;
}
