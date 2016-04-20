package io.leopard.data.memdb;

import io.leopard.commons.utility.Clock;
import io.leopard.data4j.memdb.Memdb;
import io.leopard.data4j.memdb.MemdbLruImpl;

import org.junit.Test;

public class MemdbLruImplIntegrationTest {

	// private Memdb memdb = new MemdbImpl();
	private Memdb memdb = new MemdbLruImpl(1000);

	@Test
	public void test() {
		Clock clock = Clock.start();
		for (int i = 0; i < 1000000; i++) {
			String key = i + "";
			memdb.set(key, key);
		}
		clock.time("time");
	}
}