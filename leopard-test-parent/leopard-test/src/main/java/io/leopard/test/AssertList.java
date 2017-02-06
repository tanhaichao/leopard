package io.leopard.test;

import java.util.List;

import org.junit.Assert;

import io.leopard.util.FieldUtil;

public class AssertList {

	public static void assertTrue(String fieldName, Object value, List<?> list) {
		// Json.printList(list, "list");
		boolean contains = containsKey(fieldName, value, list);
		if (!contains) {
			Assert.fail("元素[" + fieldName + "." + value + "]不存在.");
		}
	}

	public static boolean containsKey(String fieldName, Object value, List<?> list) {
		// Json.printList(list, "list");
		boolean containsKey = false;
		for (Object element : list) {
			Object tmp = FieldUtil.getFieldValue(element, fieldName);
			// System.out.println("tmp:" + tmp + " value:" + value);
			if (tmp.toString().equals(value.toString())) {// TODO ahai 未完整实现
				containsKey = true;
				break;
			}
		}
		return containsKey;
	}

	public static void assertFalse(String fieldName, Object value, List<?> list) {
		// Json.printList(list, "list");
		boolean contains = containsKey(fieldName, value, list);
		if (contains) {
			Assert.fail("元素[" + fieldName + "." + value + "]存在.");
		}
	}
}
