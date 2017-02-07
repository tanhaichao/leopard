package io.leopard.topnb.methodtime;

import org.junit.Assert;
import org.junit.Test;

public class ProfilerUtilTest {

	@Test
	public void parseClassName() {
		Assert.assertEquals("com.zhongcao.app.user.UserServiceImpl", ProfilerUtil.parseClassName("com.zhongcao.app.user.UserServiceImpl.getUid"));
		Assert.assertEquals("com.zhongcao.app.web.controller.InventoryController",
				ProfilerUtil.parseClassName("com.zhongcao.app.web.controller.InventoryController$$EnhancerBySpringCGLIB$$487fed1c.list2"));
	}

}