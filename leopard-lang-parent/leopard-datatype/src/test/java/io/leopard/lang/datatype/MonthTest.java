package io.leopard.lang.datatype;

import org.junit.Test;

import io.leopard.lang.datatype.Month;

public class MonthTest {

	@Test
	public void Month() {
		Month month = new Month();
		System.out.println("time:" + month.getTime() + " month:" + month);
	}

}