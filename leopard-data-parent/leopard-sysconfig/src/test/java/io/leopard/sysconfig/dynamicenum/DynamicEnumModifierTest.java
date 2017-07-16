package io.leopard.sysconfig.dynamicenum;

import org.junit.Test;

public class DynamicEnumModifierTest {

	@Test
	public void addConstant() throws Exception {
		DynamicEnumModifier.addConstant(Gender.class, "key1", "desc");
	}

}