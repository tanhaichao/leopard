package io.leopard.web.mvc;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class LeopardHandlerMappingTest {

	protected LeopardHandlerMapping leopardHandlerMapping = new LeopardHandlerMapping();

	@RequestMapping
	public ModelAndView test() {
		return null;
	}

	@RequestMapping(value = "test2")
	public ModelAndView test2List() {
		return null;
	}

	public ModelAndView test3() {
		return null;
	}

	@RequestMapping
	public static class TestController {
		public ModelAndView test3() {
			return null;
		}
	}

	// @Test
	// public void createRequestMappingInfo() throws SecurityException, NoSuchMethodException {
	// Method method = LeopardHandlerMappingTest.class.getDeclaredMethod("test");
	// RequestMapping annotation = method.getAnnotation(RequestMapping.class);
	// RequestMappingInfo requestMappingInfo = leopardHandlerMapping.createRequestMappingInfo(annotation, null, method);
	// Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
	// System.out.println("patterns:" + patterns);
	// Assert.assertEquals("[/test.do]", patterns.toString());
	// }

	// @Test
	// public void createRequestMappingInfo2() throws SecurityException, NoSuchMethodException {
	// Method method = LeopardHandlerMappingTest.class.getDeclaredMethod("test2List");
	// RequestMapping annotation = method.getAnnotation(RequestMapping.class);
	// {
	// RequestMappingInfo requestMappingInfo = leopardHandlerMapping.createRequestMappingInfo(annotation, null, method);
	// Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
	// Assert.assertEquals("[/test2]", patterns.toString());
	// }
	// {
	// RequestMappingInfo requestMappingInfo = leopardHandlerMapping.createRequestMappingInfo(annotation, null, null);
	// Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
	// Assert.assertEquals("[/test2]", patterns.toString());
	// }
	// }

	@Test
	public void getMappingForMethod() throws SecurityException, NoSuchMethodException {
		Method method = LeopardHandlerMappingTest.class.getDeclaredMethod("test");
		RequestMappingInfo requestMappingInfo = leopardHandlerMapping.getMappingForMethod(method, LeopardHandlerMappingTest.class);
		Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
		Assert.assertEquals("[/test.do]", patterns.toString());
	}

	@Test
	public void getMappingForMethod2() throws SecurityException, NoSuchMethodException {
		Method method = LeopardHandlerMappingTest.class.getDeclaredMethod("test3");
		RequestMappingInfo requestMappingInfo = leopardHandlerMapping.getMappingForMethod(method, LeopardHandlerMappingTest.class);
		Assert.assertNull(requestMappingInfo);
	}

	@Test
	public void getMappingForMethod3() throws SecurityException, NoSuchMethodException {
		Method method = LeopardHandlerMappingTest.class.getDeclaredMethod("test");
		RequestMappingInfo requestMappingInfo = leopardHandlerMapping.getMappingForMethod(method, TestController.class);
		Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
		Assert.assertEquals("[/test.do]", patterns.toString());
	}

	@Test
	public void LeopardHandlerMapping() {

	}

}