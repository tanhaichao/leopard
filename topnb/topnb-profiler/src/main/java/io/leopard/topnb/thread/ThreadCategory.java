package io.leopard.topnb.thread;

public enum ThreadCategory {
	CONNECTION(90, "网络连接"), DAO(80, "Dao"), SERVICE(70, "Service"), TIMER(60, "定时器"), UNKNOWN(50, "未知");
	private int key;
	private String desc;

	private ThreadCategory(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public Integer getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public static ThreadCategory toEnumByDesc(String desc) {
		ThreadCategory[] values = ThreadCategory.values();
		for (ThreadCategory category : values) {
			if (category.getDesc().equals(desc)) {
				return category;
			}
		}
		return UNKNOWN;
	}

}
