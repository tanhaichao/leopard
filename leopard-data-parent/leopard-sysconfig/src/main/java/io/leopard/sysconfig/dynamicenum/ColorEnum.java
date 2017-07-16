package io.leopard.sysconfig.dynamicenum;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

public class ColorEnum extends Enum {

	private static final long serialVersionUID = 1L;

	public static final ColorEnum RED = new ColorEnum("Red");

	public static final ColorEnum GREEN = new ColorEnum("Green");

	public static final ColorEnum BLUE = new ColorEnum("Blue");

	protected ColorEnum(String name) {
		super(name);
	}

	public static ColorEnum getEnum(String color) {
		return (ColorEnum) getEnum(ColorEnum.class, color);
	}

	public static Map getEnumMap() {
		return getEnumMap(ColorEnum.class);
	}

	public static List getEnumList() {
		return getEnumList(ColorEnum.class);
	}

	public static Iterator iterator() {
		return iterator(ColorEnum.class);
	}
}
