package io.leopard.topnb.thread;

import java.util.Comparator;

public class CategoryThreadComparator implements Comparator<ThreadInfo> {

	@Override
	public int compare(ThreadInfo o1, ThreadInfo o2) {
		int order1 = o1.getCategory().getOrder();
		int order2 = o2.getCategory().getOrder();
		long order = order2 - order1;
		if (order > 0) {
			return 1;
		}
		else if (order == 0) {
			return byCurrentSize(o1, o2);
		}
		else {
			return -1;
		}
	}

	/**
	 * 按总次数排序.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	protected int byCurrentSize(ThreadInfo o1, ThreadInfo o2) {
		double count = o2.getCurrentSize() - o1.getCurrentSize();
		if (count > 0) {
			return 1;
		}
		else if (count == 0) {
			return 0;
		}
		else {
			return -1;
		}
	}
}
