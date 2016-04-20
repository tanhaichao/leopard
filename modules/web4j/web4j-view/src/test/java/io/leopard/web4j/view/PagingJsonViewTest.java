package io.leopard.web4j.view;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class PagingJsonViewTest {

	@Test
	public void getTotalCount() {
		PagingJsonView pagingJsonView = new PagingJsonView(1, 10);
		pagingJsonView.setData(null, 11);

		Assert.assertEquals(11, pagingJsonView.getTotalCount());
		Assert.assertEquals(1, pagingJsonView.getPageId());
		Assert.assertEquals(10, pagingJsonView.getPageSize());
		Assert.assertEquals(0, pagingJsonView.getStart());
	}

	@Test
	public void getResult() {
		PagingJsonView pagingJsonView = new PagingJsonView(1, 10);
		pagingJsonView.setData("data", 11);
		Map<String, Object> map = pagingJsonView.getResult();
		Assert.assertEquals(200, map.get("status"));
		Assert.assertEquals("", map.get("message"));
		Assert.assertEquals("data", map.get("data"));
	}

}