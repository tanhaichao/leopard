package io.leopard.core;

import io.leopard.burrow.lang.SynchronizedLRUMap;
import io.leopard.test.kit.MultiThreadedCaller;

import org.junit.Test;

public class SynchronizedLRUMapIntegrationTest {
	private SynchronizedLRUMap<Integer, String> map = new SynchronizedLRUMap<Integer, String>(200, 200);

	private static int count = 0;

	protected void test() {
		int size = 10000 * 1;
		for (int i = 0; i < 200; i++) {
			for (int n = 0; n < size; n++) {
				map.get(i);
				count++;
			}
		}
	}

	@Test
	public void put() {
		for (int i = 0; i < 200; i++) {
			map.put(i, "value" + i);
		}

		MultiThreadedCaller caller = new MultiThreadedCaller(10);

		caller.add(new Invoker() {
			@Override
			public void execute() {
				test();
			}

		});

		long time = caller.execute();
		System.out.println("time:" + time + " count:" + count);

	}

}