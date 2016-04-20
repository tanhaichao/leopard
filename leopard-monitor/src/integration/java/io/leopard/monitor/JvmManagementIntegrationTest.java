package io.leopard.monitor;

import io.leopard.commons.utility.SystemUtil;

import org.junit.Test;

public class JvmManagementIntegrationTest {

	@Test
	public void printAllInfo() {
		for (int i = 0; i < 100; i++) {
			JvmManagement.printAllInfo();
			SystemUtil.sleep(1000);
			// new Thread() {
			// public void run() {
			// test();
			// };
			// }.start();
		}
	}

	public void test() {
		System.out.println("ok");
		SystemUtil.sleep(10000);
	}
}