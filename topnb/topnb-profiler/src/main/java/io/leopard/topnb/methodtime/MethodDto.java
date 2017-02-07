package io.leopard.topnb.methodtime;

public class MethodDto {

	private String name;//

	private long allCount;// 数量
	private double allTime;// 总耗时

	private long slowCount;// 数量
	private double slowTime;// 总耗时

	private String simpleMethodName;

	/**
	 * 接口名称.
	 */
	private String interfaceName;

	private String simpleClassName;

	/**
	 * 耗时比率(接口)
	 */
	private double interfaceRatio;

	/**
	 * 耗时比率(所有)
	 */
	private double timeRatio;

	/**
	 * 分类.
	 */
	private String categoryName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public double getAllTime() {
		return allTime;
	}

	public void setAllTime(double allTime) {
		this.allTime = allTime;
	}

	public long getSlowCount() {
		return slowCount;
	}

	public void setSlowCount(long slowCount) {
		this.slowCount = slowCount;
	}

	public double getSlowTime() {
		return slowTime;
	}

	public void setSlowTime(double slowTime) {
		this.slowTime = slowTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public double getInterfaceRatio() {
		return interfaceRatio;
	}

	public void setInterfaceRatio(double interfaceRatio) {
		this.interfaceRatio = interfaceRatio;
	}

	public double getTimeRatio() {
		return timeRatio;
	}

	public void setTimeRatio(double timeRatio) {
		this.timeRatio = timeRatio;
	}

	public String getSimpleMethodName() {
		return simpleMethodName;
	}

	public void setSimpleMethodName(String simpleMethodName) {
		this.simpleMethodName = simpleMethodName;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

}
