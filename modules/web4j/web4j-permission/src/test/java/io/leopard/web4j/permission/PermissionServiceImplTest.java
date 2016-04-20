package io.leopard.web4j.permission;


public class PermissionServiceImplTest {

	// private static PermissionDao permissionDaoCacheImpl = Mockito.mock(PermissionDao.class);
	// private static ConfigHandler loginHandler = new ConfigHandler();
	//
	// protected static PermissionServiceImpl newInstance() {
	// PermissionServiceImpl permissionService = new PermissionServiceImpl();
	//
	// LeopardMockito.setProperty(permissionService, permissionDaoCacheImpl);
	// LeopardMockito.setProperty(permissionService, loginHandler);
	// return permissionService;
	// }
	//
	// @Test
	// public void isMonitorServer() {
	// PermissionServiceImpl permissionService = new PermissionServiceImpl();
	// LeopardMockito.setProperty(permissionService, loginHandler);
	// Assert.assertTrue(permissionService.isMonitorServer("113.106.251.82"));
	// }
	//
	// @Test
	// public void checkMonitorServer() throws ForbiddenException {
	// PermissionServiceImpl permissionService = new PermissionServiceImpl();
	// LeopardMockito.setProperty(permissionService, loginHandler);
	// permissionService.checkMonitorServer("127.0.0.1");
	// }
	//
	// @Test
	// public void hasPermission() {
	// PermissionServiceImpl permissionService = new PermissionServiceImpl();
	// LeopardMockito.setProperty(permissionService, permissionDaoCacheImpl);
	// LeopardMockito.setProperty(permissionService, loginHandler);
	//
	// Assert.assertFalse(permissionService.hasPermission("uri", "ip"));
	// Mock.doReturn("{uri:uri}").when(permissionDaoCacheImpl).get(Mockito.any(PermissionKey.class));
	// Assert.assertTrue(permissionService.hasPermission("uri", "ip"));
	//
	// }
	//
	// @Test
	// public void checkPermission() throws ForbiddenException {
	// PermissionServiceImpl permissionService = Mockito.spy(new PermissionServiceImpl());
	// LeopardMockito.setProperty(permissionService, permissionDaoCacheImpl);
	// LeopardMockito.setProperty(permissionService, loginHandler);
	// permissionService.checkPermission("uri", "127.0.0.1");
	// Mock.doReturn(true).when(permissionService).hasPermission("uri", "127.0.0.2");
	// permissionService.checkPermission("uri", "127.0.0.2");
	// Mock.doReturn(false).when(permissionService).hasPermission("uri", "127.0.0.2");
	// try {
	// permissionService.checkPermission("uri", "127.0.0.2");
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (ForbiddenException e) {
	//
	// }
	// }

}