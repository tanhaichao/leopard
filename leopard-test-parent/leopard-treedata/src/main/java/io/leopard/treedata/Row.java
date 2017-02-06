package io.leopard.treedata;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private int floor;

	private String line;

	private List<String> data = new ArrayList<String>();

	public Row(int floor, String line) {
		this.setFloor(floor);
		line = line.trim();
		this.line = line;
		String[] blocks = line.split("\t+");
		// System.out.println("line:" + line + " blocks:" + blocks.length);
		this.addData(blocks[0].trim());
		if (blocks.length > 1) {
			this.addData(blocks[1].trim());
		}

		// System.out.println("data:" + data);
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getData(int index) {
		if (index >= data.size()) {
			return null;
		}
		return data.get(index);
	}

	public void addData(String value) {
		data.add(value);
	}

}
