package io.leopard.web.taglib.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DateTagTest {

	@Test
	public void doStartTag() throws JspException {
		PageContext pageContext = Mockito.mock(PageContext.class);
		JspWriter jspWriter = Mockito.mock(JspWriter.class);
		Mockito.doReturn(jspWriter).when(pageContext).getOut();

		DateTag tag = new DateTag();
		tag.setPageContext(pageContext);

		tag.doStartTag();
	}

	@Test
	public void getContent() {
		DateTag tag = new DateTag();
		tag.setName("name");
		tag.setValue("value");
		tag.setFormat("format");
		Assert.assertTrue(tag.getContent().indexOf("class=") == -1);
		tag.setClassName("className");
		Assert.assertTrue(tag.getContent().indexOf("class=") != -1);
		tag.setOnchange("onchange");
		Assert.assertTrue(tag.getContent().indexOf("onpicked:") != -1);
	}

}