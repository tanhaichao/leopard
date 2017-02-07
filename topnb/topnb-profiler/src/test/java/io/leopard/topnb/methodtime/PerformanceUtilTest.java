package io.leopard.topnb.methodtime;

import org.junit.Assert;
import org.junit.Test;

public class PerformanceUtilTest {

	@Test
	public void PerformanceUtil() {
		new ProfilerUtil();
	}

	@Test
	public void avgTime() {
		// Assert.assertEquals(0d, PerformanceUtil.avgTime());
		Assert.assertEquals(0.01, ProfilerUtil.avgTime(100011, 1000), 0);
		Assert.assertEquals(1000.0d, ProfilerUtil.avgTime(1, 1000), 0);
	}

	@Test
	public void getCategoryName() {
		Assert.assertEquals("DaoMysqlImpl", ProfilerUtil.getCategoryName("UserDaoMysqlImpl.get"));
		Assert.assertEquals("DaoMemoryImpl", ProfilerUtil.getCategoryName("UserDaoMemoryImpl.get"));
		Assert.assertEquals("DaoMemcachedImpl", ProfilerUtil.getCategoryName("UserDaoMemcachedImpl.get"));
		Assert.assertEquals("DaoCacheImpl", ProfilerUtil.getCategoryName("UserDaoCacheImpl.get"));
		Assert.assertEquals("DaoRedisImpl", ProfilerUtil.getCategoryName("UserDaoRedisImpl.get"));
		Assert.assertEquals("外部接口", ProfilerUtil.getCategoryName("UserDaoHttpImpl.get"));
		Assert.assertEquals("外部接口", ProfilerUtil.getCategoryName("UserDaoOutsideImpl.get"));
		Assert.assertEquals("外部接口", ProfilerUtil.getCategoryName("OutsideDaoMysqlImpl.get"));
		Assert.assertEquals("Service", ProfilerUtil.getCategoryName("UserServiceImpl.get"));
		Assert.assertEquals("Handler", ProfilerUtil.getCategoryName("UserHandlerImpl.get"));
		Assert.assertEquals("Controller", ProfilerUtil.getCategoryName("UserControllerImpl.get"));
		Assert.assertEquals("Controller", ProfilerUtil.getCategoryName("UserController.get"));
		Assert.assertEquals("未知分类", ProfilerUtil.getCategoryName("User.get"));
		Assert.assertEquals("Leopard", ProfilerUtil.getCategoryName("UserinfoServiceImpl.get"));

	}

	

	@Test
	public void isLeopardBean() {
		Assert.assertTrue(ProfilerUtil.isLeopardBean("UserinfoServiceImpl.get"));
		Assert.assertTrue(ProfilerUtil.isLeopardBean("LoginServiceImpl.get"));
		Assert.assertTrue(ProfilerUtil.isLeopardBean("AdminLoginServiceImpl.get"));
		Assert.assertTrue(ProfilerUtil.isLeopardBean("AdminLoginDaoMysqlImpl.get"));
		Assert.assertFalse(ProfilerUtil.isLeopardBean("UserServiceImpl.get"));
	}

	@Test
	public void parseClassName() {
		Assert.assertEquals("com.duowan.UserServiceImpl", ProfilerUtil.parseClassName("com.duowan.UserServiceImpl.get"));
	}

	@Test
	public void parseSimpleMethodName() {
		Assert.assertEquals("UserServiceImpl.get", ProfilerUtil.parseSimpleMethodName("com.duowan.UserServiceImpl.get"));
		Assert.assertEquals("UserServiceImpl.get", ProfilerUtil.parseSimpleMethodName("UserServiceImpl.get"));
	}

	public static interface UserService {

	}

	public static interface UserDao {

	}

	public static class UserServiceImpl implements UserDao, UserService {

	}

	@Test
	public void getInterfaceName() {
		String className = UserServiceImpl.class.getName();
		// System.out.println("className:" + className);
		Assert.assertEquals("io.leopard.monitor.performance.util.PerformanceUtilTest$UserService", ProfilerUtil.getInterfaceName(className + ".get"));
	}

	@Test
	public void parseSimpleClassName() {

	}

	@Test
	public void getCategoryOrderByMethodName() {
		//
		System.out.println("order1:" + ProfilerUtil.getCategoryOrderByMethodName("PermissionDaoMysqlImpl.get"));
		System.out.println("order2:" + ProfilerUtil.getCategoryOrderByMethodName("PassportDaoHttpImpl.get"));
		System.out.println("order3:" + ProfilerUtil.getCategoryOrderByMethodName("NewsDaoMemoryImpl.map"));
	}

	@Test
	public void formatAvgTime() {
		System.out.println("avgtime:" + ProfilerUtil.formatAvgTime(1.2));
		System.out.println("avgtime:" + ProfilerUtil.formatAvgTime(196));
		System.out.println("avgtime:" + ProfilerUtil.formatAvgTime(196.1234567));
	}
}