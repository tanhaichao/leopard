package io.leopard.data.schema;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

public class BeanDefinitionParserRegister {

	protected Log logger = LogFactory.getLog(this.getClass());

	public boolean isUseH2() {
		String useH2 = System.getProperty("useH2");
		return "true".equals(useH2);
	}

	protected BeanDefinitionParser getBeanDefinitionParser(String elementName, String className) throws Exception {
		boolean useH2 = isUseH2();
		if (!useH2) {
			return (BeanDefinitionParser) Class.forName(className).newInstance();
		}

		String h2ClassName = className.replace("io.leopard.schema", "io.leopard.schema.h2");
		Class<?> clazz;
		try {
			clazz = Class.forName(h2ClassName);
		}
		catch (ClassNotFoundException e) {
			clazz = Class.forName(className);
		}
		return (BeanDefinitionParser) clazz.newInstance();
	}
}
