package io.leopard.topnb.thread;

/**
 * 隐藏.
 * 
 * @author 阿海
 *
 */
public interface Hider {

	/**
	 * 是否需要隐藏.
	 * 
	 * @param threadName
	 * @return
	 */
	Boolean isHide(String threadName, StackTraceElement element);
}
