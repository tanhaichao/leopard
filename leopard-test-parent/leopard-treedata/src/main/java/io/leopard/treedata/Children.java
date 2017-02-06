package io.leopard.treedata;

import java.util.ArrayList;
import java.util.List;

public class Children {

	private String name;

	private String colour;

	private String line;

	private List<Children> children;

	// public Children(String name) {
	// this(name, null);
	// }

	public Children(String name, String colour, String line) {
		this.name = name;
		this.colour = colour;
		this.line = line;
	}

	// public Children add(String name) {
	// return this.add(name, null);
	// }

	public Children add(String name, String colour, String line) {
		Children children = new Children(name, colour, line);
		if (this.children == null) {
			this.children = new ArrayList<Children>();
		}
		this.children.add(children);
		return children;
	}

	public List<Children> getChildren() {
		return children;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
