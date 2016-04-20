package io.leopard.web.permission;

import io.leopard.test.IntegrationTests;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "permission/applicationContext.xml", "applicationContext-mysql.xml" })
public class PermissionIntegrationTest extends IntegrationTests {

}
