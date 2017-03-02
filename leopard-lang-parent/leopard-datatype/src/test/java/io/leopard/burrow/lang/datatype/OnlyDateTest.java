package io.leopard.burrow.lang.datatype;

import org.junit.Test;

public class OnlyDateTest {

	@Test
	public void OnlyDate() {
		OnlyDate day = new OnlyDate();
		System.out.println("time:" + day.getTime() + " day:" + day);
	}

}