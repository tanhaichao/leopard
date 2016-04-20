package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.MockRequest;

import org.junit.Assert;
import org.junit.Test;

public class PageIdPageParameterTest {
	PageIdPageParameter page = new PageIdPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("pageId", page.getKey());
	}

	@Test
	public void getValue() {
		{
			MockRequest request = new MockRequest();
			request.setParameter("pageid", "2");
			Assert.assertEquals("2", page.getValue(request));
		}
		{
			MockRequest request = new MockRequest();
			request.setParameter("pageId", "2");
			Assert.assertEquals("2", page.getValue(request));
		}
		{
			MockRequest request = new MockRequest();
			request.setParameter("page", "2");
			Assert.assertEquals("2", page.getValue(request));
		}
		{
			MockRequest request = new MockRequest();
			request.setParameter("p", "2");
			Assert.assertEquals("2", page.getValue(request));
		}
		{
			MockRequest request = new MockRequest();
			// request.setParameter("p", "2");
			Assert.assertEquals("1", page.getValue(request));
		}
	}

}