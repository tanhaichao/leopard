package io.leopard.web.taglib.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

public class MenuTagTest {

	@Test
	public void getHtml() throws JspException {
		// private String base;
		// private String className;
		// private String selectedClass;
		PageContext pageContext = Mockito.mock(PageContext.class);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/base/index.do");
		// Mock.doReturn(request).when(pageContext).getRequest();

		MenuTag tag = new MenuTag();
		tag.setPageContext(pageContext);
		tag.setBase("/base");
		tag.setClassName("className");
		tag.setSelectedClass("selectedClass");
		String body = "<a href=\"/index.do\">menu1</a>";
		body += "<a href=\"/index.do\">menu2</a>";

		// System.out.println("content:" + content);
		Assert.assertTrue(tag.getHtml(body).indexOf("selectedClass") != -1);

		tag.setSelectedClass(null);
		Assert.assertTrue(tag.getHtml(body).indexOf("selectedClass") == -1);
		tag.setSelectedClass("selectedClass");
		request.setRequestURI("/base/index1.do");
		Assert.assertTrue(tag.getHtml(body).indexOf("selectedClass") == -1);

	}

	@Test
	public void doAfterBody() throws JspException {

		// BodyContent bodyContent = getBodyContent();
		// JspWriter out = bodyContent.getEnclosingWriter();
		PageContext pageContext = Mockito.mock(PageContext.class);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/base/index.do");
		// Mock.doReturn(request).when(pageContext).getRequest();
		BodyContent bodyContent = Mockito.mock(BodyContent.class);

		JspWriter out = Mockito.mock(JspWriter.class);
		Mockito.doReturn("a").when(bodyContent).getString();
		Mockito.doReturn(out).when(bodyContent).getEnclosingWriter();

		MenuTag tag = Mockito.spy(new MenuTag());
		tag.setPageContext(pageContext);
		Mockito.doReturn(bodyContent).when(tag).getBodyContent();

		tag.doAfterBody();
	}
}