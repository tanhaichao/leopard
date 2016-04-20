package io.leopard.web.taglib.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Test;
import org.mockito.Mockito;

public class LayoutTagTest {

	@Test
	public void doStartTag() throws JspException {
		PageContext pageContext = Mockito.mock(PageContext.class);
		JspWriter jspWriter = Mockito.mock(JspWriter.class);
		Mockito.doReturn(jspWriter).when(pageContext).getOut();

		LayoutTag tag = new LayoutTag();
		tag.setPageContext(pageContext);

		tag.doStartTag();
		tag.doEndTag();
	}

}