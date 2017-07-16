package io.leopard.sysconfig.dynamicenum;

import java.util.List;

import org.junit.Test;

public class ColorEnumTest {

	@Test
	public void ColorEnum() {
		List list = ColorEnum.getEnumList();
		for (Object constant : list) {
			System.out.println(constant);
		}
	}

}