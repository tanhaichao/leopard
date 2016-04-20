package io.leopard.web4j.view;

import org.junit.Assert;
import org.junit.Test;

public class PagingViewTest {

	@Test
	public void getPageId() {
		PagingView pagingView = new PagingView("list", 1, 10);
		Assert.assertEquals(1, pagingView.getPageId());
		Assert.assertEquals(0, pagingView.getStart());
		Assert.assertEquals(10, pagingView.getPageSize());
		pagingView.setTotalCount(11);
		Assert.assertEquals(11, pagingView.getTotalCount());

	}

}