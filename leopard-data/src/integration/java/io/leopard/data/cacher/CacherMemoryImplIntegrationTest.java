package io.leopard.data.cacher;

import org.junit.Test;

public class CacherMemoryImplIntegrationTest {

	@Test
	public void getSlot() {
		this.getSlot(10000);
		this.getSlot(20001);
		this.getSlot(System.currentTimeMillis());
	}

	public void getSlot(long time) {
		// int slot = CacherMemoryImpl.getSlot(time, 10);
		// System.out.println("slot:" + slot + " time:" + time);
	}

}