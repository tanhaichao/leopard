package io.leopard.jdbc;

import java.util.Date;

public class RequestStat {

	// 数量 总耗时 平均 数量 总耗时 平均 数量 总耗时 平均 发生时间
	private Date time;// 时间段

	private String projectId;
	private String url;//
	private String server;// APP服务器

	
	// 新连接
	private int allCount;// 数量
	private double allTime;// 总耗时
	// private Long newAvgTime;// 平均

	// 慢连接
	private int slowCount;// 数量
	private double slowTime;// 总耗时
	// private Long slowAvgTime;// 平均

	// 特慢连接
	private int verySlowCount;// 数量
	private double verySlowTime;// 总耗时
	// private Long verySlowAvgTime;// 平均

	// 一分钟最大连接
	private int maxCount;// 数量
	private double maxTime;// 总耗时
	// private Long maxAvgTime;// 平均
	private Date maxDate;// 发生时间
	private Date lmodify;// 记录最后修改时间

	public RequestStat() {

	}

	public RequestStat(String url, String server) {
		this.url = url;
		this.server = server;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public double getAllTime() {
		return allTime;
	}

	public void setAllTime(double allTime) {
		this.allTime = allTime;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getSlowCount() {
		return slowCount;
	}

	public void setSlowCount(int slowCount) {
		this.slowCount = slowCount;
	}

	public double getSlowTime() {
		return slowTime;
	}

	public void setSlowTime(double slowTime) {
		this.slowTime = slowTime;
	}

	public int getVerySlowCount() {
		return verySlowCount;
	}

	public void setVerySlowCount(int verySlowCount) {
		this.verySlowCount = verySlowCount;
	}

	public double getVerySlowTime() {
		return verySlowTime;
	}

	public void setVerySlowTime(double verySlowTime) {
		this.verySlowTime = verySlowTime;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
