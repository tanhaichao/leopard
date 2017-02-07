package io.leopard.topnb.thread;

/**
 * 线程分类.
 * 
 * @author 阿海
 *
 */
public interface Classifier {

	Category getCategory(String threadName, StackTraceElement element);

	public interface Category {

		public int getOrder();

		public String getKey();

		public String getName();
	}
}
