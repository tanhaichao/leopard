package io.leopard.lang;

import java.util.Date;

/**
 * 时间范围(时间段).
 * 
 * @author 阿海
 *
 */
public class TimeRange {

	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	public TimeRange() {
	}

	public TimeRange(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
