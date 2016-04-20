package io.leopard.web.taglib.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

public class PageTagTest {

	@Test
	public void getContent() {
		PageContext pageContext = Mockito.mock(PageContext.class);
		JspWriter jspWriter = Mockito.mock(JspWriter.class);
		Mockito.doReturn(jspWriter).when(pageContext).getOut();

		MockHttpServletRequest request = new MockHttpServletRequest();
		// Object pageIdObj = request.getAttribute("pageId");
		// Object totalCountObj = request.getAttribute("totalCount");
		// Object pageSizeObj = request.getAttribute("pageSize");
		request.setAttribute("pageId", 1);
		request.setAttribute("totalCount", 9);
		request.setAttribute("pageSize", 10);
		// Mock.doReturn(request).when(pageContext).getRequest();

		PageTag tag = new PageTag();
		tag.setPageContext(pageContext);
		tag.setAlign("left");
		tag.setStyle("mac");
		tag.setGo(false);

		Assert.assertNotNull(tag.getContent());

		request.setAttribute("pageSize", 0);
		try {
			tag.getContent();
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		request.setAttribute("pageSize", 10);
		tag.setStyle("win");
		tag.getContent();
		tag.setStyle("easy");
		tag.getContent();
		tag.setStyle("other");
		try {
			tag.getContent();
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void getUrl() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("name1", "value1");
		request.setParameter("name2", "value2");
		request.setParameter("pageId", "1");
		request.setRequestURI("/index.do");
		PageTag tag = new PageTag();

		String url = tag.getUrl(request);

		Assert.assertEquals("/index.do?name1=value1&name2=value2", url);
		System.out.println("url:" + url);

	}

}