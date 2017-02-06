package io.leopard.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * 集成测试基础类(用于开发阶段测试完整流程).
 * 
 * @author 阿海
 * 
 */
@ContextConfiguration(loader = TestContextLoader.class)
@ActiveProfiles(value = "dev", inheritProfiles = false)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, SqlScriptsTestExecutionListener.class })
@RunWith(IntegrationRunner.class)
public class IntegrationTests extends AbstractJUnit4SpringContextTests {

	protected Log logger = LogFactory.getLog(this.getClass());

	static {
		System.setProperty("env.junit", "true");
	}

	@AfterClass
	public static void leave() {
		// TODO
		// boolean useH2 = DefaultH2DataSource.isUseH2();
		// if (useH2) {
		// if (!DefaultH2DataSource.isAutoCommit()) {
		// H2Util.cleanDir("integration");
		// }
		// }
	}

}