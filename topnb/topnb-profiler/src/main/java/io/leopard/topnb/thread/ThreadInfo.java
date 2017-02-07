package io.leopard.topnb.thread;

import io.leopard.topnb.thread.Classifier.Category;

public class ThreadInfo {

	private String threadName;// 线程名称
	private Category category;// 线程分类
	private int currentSize;// 当前连接数
	// private int peakSize;// 峰值数量
	private StackTraceElement element;
	private String content;// 备注

	public StackTraceElement getElement() {
		return element;
	}

	public void setElement(StackTraceElement element) {
		this.element = element;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	// public int getPeakSize() {
	// return peakSize;
	// }
	//
	// public void setPeakSize(int peakSize) {
	// this.peakSize = peakSize;
	// }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
