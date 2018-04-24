package io.leopard.burrow.util;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void uuid() {
		String uuid = StringUtil.uuid();
		System.out.println(uuid);
	}

	@Test
	public void generateNumberKey() {
		String number = StringUtil.generateNumberKey(10);
		System.out.println("number:" + number);
	}

}