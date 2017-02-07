package io.leopard.topnb.methodtime;

import io.leopard.topnb.TopnbBeanFactory;

import org.junit.Assert;
import org.junit.Test;

public class MethodTimeServiceImplTest {

	protected MethodTimeServiceImpl performanceService = (MethodTimeServiceImpl) TopnbBeanFactory.getMethodTimeService();

	@Test
	public void add() {
		performanceService.add("methodName", 1);
		performanceService.add("methodName", 50 * 1000L);
		performanceService.add("methodName", 500 * 1000L);

		Assert.assertNotNull(performanceService.listAll("all"));
	}

	// @Test
	// public void listAllThreadName() {
	// Assert.assertNotNull(performanceService.listAllEntryName());
	// }

	//
	// @Test
	// public void getTypeName() {
	// Assert.assertEquals("全部入口", this.performanceService.getTypeName(""));
	// Assert.assertEquals("/index.do", this.performanceService.getTypeName("/index.do"));
	// // Assert.assertEquals("按入口:threadName", this.performanceService.getTypeName("threadName", "threadName", ""));
	// // Assert.assertEquals("今天的统计数据", this.performanceService.getTypeName("today", null, null));
	// }

	@Test
	public void listAll() {

	}

	// @Test
	// public void getCount() {
	// Assert.assertEquals(0, performanceService.getCount("entry"));
	// }

	@Test
	public void isIgnoreMethod() {
		Assert.assertTrue(this.performanceService.isIgnoreMethod("io.leopard.web.userinfo.service.UserinfoServiceImpl"));
		Assert.assertFalse(this.performanceService.isIgnoreMethod("io.leopard.web.userinfo.service.OtherServiceImpl"));
	}
}