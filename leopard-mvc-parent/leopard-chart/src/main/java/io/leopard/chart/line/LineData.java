package io.leopard.chart.line;

import java.util.List;

/**
 * 线
 * 
 * @author 谭海潮
 *
 */
public class LineData {

	/**
	 * 标签
	 */
	private String label;
	/**
	 * 颜色
	 */
	private String color;

	/**
	 * 数据
	 */
	private List<Object[]> data;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Object[]> getData() {
		return data;
	}

	public void setData(List<Object[]> data) {
		this.data = data;
	}

}
