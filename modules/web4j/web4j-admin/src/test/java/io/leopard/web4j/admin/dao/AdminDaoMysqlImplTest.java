package io.leopard.web4j.admin.dao;

import io.leopard.core.exception.LeopardRuntimeException;
import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.test4j.mock.MockRequest;
import io.leopard.test4j.mock.MockResponse;
import io.leopard.web4j.admin.Admin;

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
		request.setSessionAttribute("sessUsername", "hctan");
		Assert.assertEquals("hctan", adminLoginDaoMysqlImpl.getUsername(request, response));
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
		Mockito.doReturn(null).when(adminLoginDaoMysqlImpl).get("hctan");
		try {
			adminLoginDaoMysqlImpl.login("hctan", null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (LeopardRuntimeException e) {

		}
		Admin admin = Mockito.mock(Admin.class);
		Mockito.doReturn(admin).when(adminLoginDaoMysqlImpl).get("hctan");
		adminLoginDaoMysqlImpl.login("hctan", null);
	}
}