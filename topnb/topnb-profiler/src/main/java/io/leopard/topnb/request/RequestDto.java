package io.leopard.topnb.request;

import java.util.Date;

public class RequestDto {

	private String url;//

	private long allCount;// 数量
	private double allTime;// 总耗时

	private long slowCount;// 数量
	private double slowTime;// 总耗时

	// 特慢连接
	private long verySlowCount;// 数量
	private double verySlowTime;// 总耗时

	private long maxCount;// 数量
	private double maxTime;// 总耗时
	private Date maxDate;// 发生时间

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public long getVerySlowCount() {
		return verySlowCount;
	}

	public void setVerySlowCount(long verySlowCount) {
		this.verySlowCount = verySlowCount;
	}

	public double getVerySlowTime() {
		return verySlowTime;
	}

	public void setVerySlowTime(double verySlowTime) {
		this.verySlowTime = verySlowTime;
	}

	public long getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(long maxCount) {
		this.maxCount = maxCount;
	}

	public double getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(double maxTime) {
		this.maxTime = maxTime;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

}
