package io.leopard.lang.datatype;

import org.junit.Test;

import io.leopard.lang.datatype.OnlyDate;

public class OnlyDateTest {

	@Test
	public void OnlyDate() {
		OnlyDate day = new OnlyDate();
		System.out.println("time:" + day.getTime() + " day:" + day);
	}

}