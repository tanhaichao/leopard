package io.leopard.test.mocker;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

public class Mocker {

	public static void mock(String expression, Object value) {

	}

	public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier) {

		// return groupingBy(classifier, toList());
		return null;
	}

	protected void test() {

	}

	public static void modifyTheValue(int valueToBeOperated, Function<Integer, Integer> function) {
		int newValue = function.apply(valueToBeOperated);
		System.out.println(newValue);
	}

	public static void main(String[] args) {

		int incr = 20;
		int myNumber = 10;
		modifyTheValue(myNumber, val -> val + incr);
		myNumber = 15;
		// modifyTheValue(myNumber, val -> val * 10);
		modifyTheValue(myNumber, val -> val - 100);
		// modifyTheValue(myNumber, val -> "somestring".length() + val - 100);
	}

}
