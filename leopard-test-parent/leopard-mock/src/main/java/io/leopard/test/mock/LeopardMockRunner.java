package io.leopard.test.mock;

import org.powermock.modules.junit4.common.internal.PowerMockJUnitRunnerDelegate;
import org.powermock.modules.junit4.common.internal.impl.AbstractCommonPowerMockRunner;

public class LeopardMockRunner extends AbstractCommonPowerMockRunner {
	
	public LeopardMockRunner(Class<?> klass) throws Exception {
		this(klass, LoepardMockRunnerDelegateImpl.class);
	}

	public LeopardMockRunner(Class<?> klass, Class<? extends PowerMockJUnitRunnerDelegate> runnerDelegateImplClass) throws Exception {
		super(klass, runnerDelegateImplClass);
	}
	

	// /**
	// * Clean up some state to avoid OOM issues
	// */
	// @Override
	// public void run(RunNotifier notifier) {
	// // TODO ahai 这个方法有什么用?
	// Description description = getDescription();
	// try {
	// super.run(notifier);
	// System.out.println("notifier:" + notifier);
	// }
	// finally {
	// Whitebox.setInternalState(description, "fAnnotations", new Annotation[]
	// {});
	// }
	// }
}
