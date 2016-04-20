package io.leopard.data.signature;

import io.leopard.commons.utility.SystemUtil;

import org.junit.Test;
import org.mockito.Mockito;

public class SignatureQueueManagerTest {

	public static class SignatureQueueManagerImpl extends SignatureQueueManager {
		@Override
		public void consume(String sha1) {
			System.out.println("sha1:" + sha1);
		}
	}

	private SignatureQueueManager queueManager = Mockito.spy(new SignatureQueueManagerImpl());

	@Test
	public void SignatureQueueManager() {
		queueManager.add("sha1");
		SystemUtil.sleep(1000);
		// Mockito.verify(queueManager).consume("sha1");
	}

}