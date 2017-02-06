package io.leopard.test;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class TestRunner extends BlockJUnit4ClassRunner {

	public TestRunner(Class<?> klass) throws InitializationError {
		super(klass);

	}

}
