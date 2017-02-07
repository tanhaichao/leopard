package io.leopard.topnb.web.data;

/**
 * 方法耗时信息.
 * 
 * @author 阿海
 *
 */
public class CountDto {

	private String name;
	private long count;
	private double time;// 单位微秒

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "CountDto [name=" + name + ", count=" + count + ", time=" + time + "]";
	}

}
