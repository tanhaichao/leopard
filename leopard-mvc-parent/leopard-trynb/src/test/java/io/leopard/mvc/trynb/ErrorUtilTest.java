package io.leopard.mvc.trynb;

import java.lang.reflect.GenericSignatureFormatError;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

//@RunWith(LeopardMockRunner.class)
public class ErrorUtilTest {

	@Test
	public void ErrorUtil() {
		new ErrorUtil();
	}

	// @Test
	// public void isUseFtl() {
	// BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
	// PowerMockito.when(LeopardBeanFactoryAware.getBeanFactory()).thenReturn(beanFactory);
	// Assert.assertFalse(ErrorUtil.isUseFtl());
	//
	// FreeMarkerViewResolver resolver =
	// Mockito.mock(FreeMarkerViewResolver.class);
	//
	// Mockito.doReturn(resolver).when(beanFactory).getBean("viewResolver");
	// Assert.assertTrue(ErrorUtil.isUseFtl());
	//
	// Mockito.doReturn("ok").when(beanFactory).getBean("viewResolver");
	// Assert.assertFalse(ErrorUtil.isUseFtl());
	// }

	// @Test
	// public void replaceMessage() {
	// Assert.assertNull(ErrorUtil.replaceMessage((String) null));
	// Assert.assertEquals("错误消息有HTML代码.", ErrorUtil.replaceMessage("<font>"));
	// Assert.assertEquals("错误消息有HTML代码.", ErrorUtil.replaceMessage("font>"));
	// Assert.assertEquals("错误消息有XSS风险.", ErrorUtil.replaceMessage("src="));
	// Assert.assertEquals("message", ErrorUtil.replaceMessage("message"));
	// Assert.assertEquals("message", ErrorUtil.replaceMessage(new Exception("message")));
	// }

	@Test
	public void parseMessage() {
		// Assert.assertEquals("操作数据库出错，请稍后重试.", ErrorUtil.parseMessage(new InvalidParamDataAccessException("message")));
		Assert.assertEquals("操作数据库出错，请稍后重试.", ErrorUtil.parseMessage(new SQLException("message")));
		// Assert.assertEquals("操作数据库出错，请稍后重试.", ErrorUtil.parseMessage(new JedisConnectionException("message")));
		// Assert.assertEquals("访问外部接口出错，请稍后重试.", ErrorUtil.parseMessage(new OutSideException("message")));
		Assert.assertEquals("更新程序后，还没有重启服务.", ErrorUtil.parseMessage(new GenericSignatureFormatError()));

		Assert.assertEquals("message", ErrorUtil.parseMessage(new IllegalArgumentException("message")));
		// Assert.assertEquals("message", ErrorUtil.parseMessage(new LeopardRuntimeException("message")));
		// Assert.assertEquals("message", ErrorUtil.parseMessage(new LeopardException("message")));
		Assert.assertEquals("message", ErrorUtil.parseMessage(new RuntimeException("message")));
		Assert.assertEquals("未知错误.", ErrorUtil.parseMessage(new Exception("message")));
	}

	@Test
	public void getClientInfo() {
		// MockRequest request = new MockRequest();
		// ErrorUtil.getClientInfo(request, "/index.do", "message");
	}

	@Test
	public void fillterDebugInfo() {
		Assert.assertEquals("abc", ErrorUtil.fillterDebugInfo("a[a]bc"));
		Assert.assertEquals("abc", ErrorUtil.fillterDebugInfo("a[]bc"));
		Assert.assertEquals("abc", ErrorUtil.fillterDebugInfo("a[a]b[b]c"));
		Assert.assertEquals("abc", ErrorUtil.fillterDebugInfo("a[a[b]]bc"));
		Assert.assertEquals("abc", ErrorUtil.fillterDebugInfo("a[a[b]][a]bc"));

	}
}