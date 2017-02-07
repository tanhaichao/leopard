package io.leopard.topnb.methodtime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModuleServiceImplTest {

	@Autowired
	private ModuleServiceImpl moduleService;

	@Test
	public void getModuleName() {
		Assert.assertEquals("DAO", moduleService.getModuleName("UserDaoMysqlImpl.get"));
		Assert.assertEquals("DaoCacheImpl", moduleService.getModuleName("UserDaoCacheImpl.get"));
		Assert.assertEquals("Service", moduleService.getModuleName("UserServiceImpl.get"));
		Assert.assertEquals("Handler", moduleService.getModuleName("UserHandlerImpl.get"));
		Assert.assertEquals("Controller", moduleService.getModuleName("UserControllerImpl.get"));
		Assert.assertEquals("Controller", moduleService.getModuleName("UserController.get"));
		Assert.assertEquals("未知模块", moduleService.getModuleName("User.get"));
	}

}