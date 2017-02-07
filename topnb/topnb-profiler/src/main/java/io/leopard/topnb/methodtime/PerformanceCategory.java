package io.leopard.topnb.methodtime;

public enum PerformanceCategory {

	DAOMYSQLIMPL(90, "DaoMysqlImpl"), OUTSIDE(80, "外部接口"), DAOMEMORYIMPL(70, "DaoMemoryImpl"), DAOREDISIMPL(60, "DaoRedisImpl"), DAOMEMCACHEDIMPL(50, "DaoMemcachedImpl")//
	, DAOCACHEIMPL(48, "DaoCacheImpl"), LEOPARD(45, "Leopard"), DAO(40, "Service"), SERVICE(30, "Service"), HANDLER(25, "Handler"), Controller(20, "Controller"), TIMER(15, "Timer")//
	, ENCODER(14, "编码"), DATASOURCE(13, "数据源"), UNKNOWN(10, "未知分类");

	private int key;
	private String desc;

	private PerformanceCategory(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public Integer getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public static PerformanceCategory toEnumByDesc(String desc) {
		PerformanceCategory[] values = PerformanceCategory.values();
		for (PerformanceCategory category : values) {
			if (category.getDesc().equals(desc)) {
				return category;
			}
		}
		// throw new IllegalArgumentException("根据描述信息[" + desc + "]找不到对应的枚举.");

		// "根据描述信息[" + desc + "]找不到对应的枚举."
		return UNKNOWN;
	}

}
