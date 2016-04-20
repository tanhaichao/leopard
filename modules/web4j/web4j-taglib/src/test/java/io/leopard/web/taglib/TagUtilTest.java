package io.leopard.web.taglib;

import io.leopard.web.taglib.tags.DateTag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

public class TagUtilTest {

	@Test
	public void TagUtil() {
		new TagUtil();
	}

	@Test
	public void getCurrentUri() {
		// /WEB-INF/jsp/index.jsp
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/WEB-INF/jsp/index.jsp");
		MockPageContext pageContext = new MockPageContext(null, request);
		// pageContext.getRequest();

		Assert.assertEquals("/index.do", TagUtil.getCurrentUri(pageContext));
	}

	@Test
	public void write() throws JspException, IOException {
		PageContext pageContext = Mockito.mock(PageContext.class);
		JspWriter jspWriter = Mockito.mock(JspWriter.class);
		Mockito.doReturn(jspWriter).when(pageContext).getOut();

		DateTag tag = new DateTag();
		tag.setPageContext(pageContext);

		TagUtil.write(pageContext, "str");

		Mockito.doThrow(new IOException("")).when(jspWriter).write(Mockito.anyString());
		try {
			TagUtil.write(pageContext, "str");
			Assert.fail("为什么没有抛异常?");
		}
		catch (JspException e) {

		}
	}

}