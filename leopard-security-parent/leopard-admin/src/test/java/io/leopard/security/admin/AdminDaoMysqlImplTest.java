package io.leopard.security.admin;

import io.leopard.security.admin.Admin;
import io.leopard.security.admin.AdminDaoMysqlImpl;
import io.leopard.security.admin.AdminNotFoundException;
import io.leopard.test.mock.LeopardMockRunner;
import io.leopard.test.mock.MockRequest;
import io.leopard.test.mock.MockResponse;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(LeopardMockRunner.class)
public class AdminDaoMysqlImplTest {

	protected AdminDaoMysqlImpl adminLoginDaoMysqlImpl = new AdminDaoMysqlImpl();

	@Test
	public void forwardLoginUrl() throws IOException {
		adminLoginDaoMysqlImpl.forwardLoginUrl(null, null);
	}

	@Test
	public void getUsername() {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		request.setSessionAttribute("sessUid", "1");
		Assert.assertEquals("1", adminLoginDaoMysqlImpl.getUid(request, response));
	}

	// @Test
	// public void getSessionUsername() {
	// MockRequest request = new MockRequest();
	// Assert.assertNull(adminLoginDaoMysqlImpl.getSessionUsername(request.getSession()));
	// request.setSessionAttribute("sessUsername", "hctan");
	// Assert.assertEquals("hctan", adminLoginDaoMysqlImpl.getSessionUsername(request.getSession()));
	//
	// request.setSessionAttribute("sessUsername", "");
	// request.setSessionAttribute("sessYyuid", 1);
	// UclientFacadeService uclientFacadeService = Mockito.mock(UclientFacadeService.class);
	//
	// PowerMockito.when(UclientFacadeModule.getUclientFacadeService()).thenReturn(uclientFacadeService);
	// Mockito.doReturn("hctan2").when(uclientFacadeService).getPassport(1);
	// Assert.assertEquals("hctan2", adminLoginDaoMysqlImpl.getSessionUsername(request.getSession()));
	// }

	// @Test
	// public void get() {
	// Jdbc jdbc = Mockito.mock(Jdbc.class);
	// LeopardMockito.setProperty(adminLoginDaoMysqlImpl, jdbc);
	//
	// Admin admin = new Admin();
	// admin.setUsername("hctan");
	// Mock.doReturn(admin).when(jdbc).query(Mockito.anyString(), (Class<?>) Mockito.any(), Mockito.any(StatementParameter.class));
	// // Admin admin = jdbc.query(sql, Admin.class, param);
	// Assert.assertEquals("hctan", adminLoginDaoMysqlImpl.get("username").getUsername());
	// }

	@Test
	public void login() {
		AdminDaoMysqlImpl adminLoginDaoMysqlImpl = Mockito.spy(new AdminDaoMysqlImpl());
		Mockito.doReturn(null).when(adminLoginDaoMysqlImpl).get(1);
		try {
			adminLoginDaoMysqlImpl.login(1, null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (AdminNotFoundException e) {

		}
		Admin admin = Mockito.mock(Admin.class);
		Mockito.doReturn(admin).when(adminLoginDaoMysqlImpl).get(1);
		adminLoginDaoMysqlImpl.login(1, null);
	}
}