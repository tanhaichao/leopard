package io.leopard.web.taglib.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SelectTagTest {

	@Test
	public void doStartTag() throws JspException {
		PageContext pageContext = Mockito.mock(PageContext.class);
		JspWriter jspWriter = Mockito.mock(JspWriter.class);
		Mockito.doReturn(jspWriter).when(pageContext).getOut();

		SelectTag tag = new SelectTag();
		tag.setPageContext(pageContext);

		tag.doStartTag();
		tag.doEndTag();
	}

	@Test
	public void getHtml() {
		SelectTag tag = new SelectTag();
		Assert.assertNotNull(tag.getHtml().indexOf("ok"));
		tag.setStyle("style");
		Assert.assertTrue(tag.getHtml().indexOf("style=") != -1);
		tag.setTabindex("tabindex");
		Assert.assertTrue(tag.getHtml().indexOf("tabindex=") != -1);
		tag.setMultiple(true);
		Assert.assertTrue(tag.getHtml().indexOf("multiple") != -1);
		tag.setTitle("title");
		Assert.assertTrue(tag.getHtml().indexOf("title") != -1);

	}

	@Test
	public void setTitle() {
		// AUTO
	}

}