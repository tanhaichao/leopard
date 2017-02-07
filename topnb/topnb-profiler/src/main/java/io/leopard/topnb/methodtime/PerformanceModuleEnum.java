package io.leopard.topnb.methodtime;

public enum PerformanceModuleEnum {

	DAO(90, "DAO"), DAOMYSQLIMPL(90, "DaoMysqlImpl"), OUTSIDE(80, "外部接口"), DAOMEMORYIMPL(70, "DaoMemoryImpl"), DAOREDISIMPL(60, "DaoRedisImpl"), DAOMEMCACHEDIMPL(50, "DaoMemcachedImpl")//
	, DAOCACHEIMPL(45, "DaoCacheImpl"), LEOPARD(45, "Leopard"), SERVICE(40, "Service"), HANDLER(30, "Handler"), TIMER(20, "Controller"), UNKNOWN(10, "未知模块");
	private int key;
	private String desc;

	private PerformanceModuleEnum(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public Integer getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public static PerformanceModuleEnum toEnumByDesc(String desc) {
		PerformanceModuleEnum[] values = PerformanceModuleEnum.values();
		for (PerformanceModuleEnum category : values) {
			if (category.getDesc().equals(desc)) {
				return category;
			}
		}
		// throw new IllegalArgumentException("根据描述信息[" + desc + "]找不到对应的枚举.");

		// "根据描述信息[" + desc + "]找不到对应的枚举."
		return UNKNOWN;
	}

}
