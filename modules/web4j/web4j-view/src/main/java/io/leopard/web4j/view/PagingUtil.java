package io.leopard.web4j.view;

public class PagingUtil {
	/**
	 * 计算默认起始记录
	 * 
	 * @param pageid
	 *            分页编号
	 * @param size
	 *            分页大小
	 * @return 当前分页的起始记录编号
	 */
	public static int getPageStart(int pageId, int size) {
		if (pageId < 1) {
			throw new IllegalArgumentException("pageid不能小于1.");
		}
		if (size < 1) {
			throw new IllegalArgumentException("size不能小于1.");
		}
		return (pageId - 1) * size;
	}
}
