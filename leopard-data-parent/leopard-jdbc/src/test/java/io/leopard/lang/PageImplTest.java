package io.leopard.lang;

import org.junit.Assert;
import org.junit.Test;

public class PageImplTest {

	@Test
	public void hasNextPage() {
		Assert.assertFalse(PageImpl.hasNextPage(10, 0, 10));
		Assert.assertTrue(PageImpl.hasNextPage(11, 0, 10));
	}

}