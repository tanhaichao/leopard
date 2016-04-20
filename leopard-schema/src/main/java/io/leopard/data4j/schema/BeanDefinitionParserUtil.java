package io.leopard.data4j.schema;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class BeanDefinitionParserUtil {

	public static BeanDefinition createBean(ParserContext parserContext, final String beanId, final Class<?> beanClass) {
		final boolean enabledInitMethod = true;
		return createBean(parserContext, beanId, beanClass, enabledInitMethod);
	}

	public static BeanDefinition createBean(ParserContext parserContext, final String beanId, final Class<?> beanClass, final boolean enabledInitMethod) {
		return createBean(parserContext, beanId, beanClass, enabledInitMethod, new DoParser() {
			@Override
			public void doParse(BeanDefinitionBuilder builder) {

			}
		});
	}

	public static interface DoParser {
		void doParse(BeanDefinitionBuilder builder);
	}

	public static BeanDefinition createBean(ParserContext parserContext, final String beanId, final Class<?> beanClass, final boolean enabledInitMethod, final DoParser parser) {
		Element element = new ElementImpl();
		element.setAttribute("id", beanId);

		BeanDefinitionParser beanDefinitionParser = new AbstractSingleBeanDefinitionParser() {
			@Override
			protected Class<?> getBeanClass(Element element) {
				return beanClass;
			}

			@Override
			protected void doParse(Element element, BeanDefinitionBuilder builder) {
				// builder.addPropertyValue("id", beanId);
				if (enabledInitMethod) {
					builder.setInitMethodName("init");
					builder.setDestroyMethodName("destroy");
				}

				parser.doParse(builder);
				// builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
			}
		};
		// System.err.println("createBean:" + beanId + " " + beanClass.getName());
		return beanDefinitionParser.parse(element, parserContext);
	}

	//
	// public static void registerBeanDefinition(String beanName, Class<?> clazz, final boolean enabledInitMethod) {
	// // createBean(new ElementImpl(), parserContext, clazz, beanName, enabledInitMethod);
	// registerBeanDefinition(LeopardBeanFactoryAware.getBeanFactory(), beanName, clazz, enabledInitMethod);
	// }
	//
	public static boolean registerBeanDefinition(BeanFactory beanFactory, String beanName, Class<?> clazz, final boolean enabledInitMethod) {
		// FIXME ahai 这种方式注册bean有问题.

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

		if (enabledInitMethod) {
			beanDefinitionBuilder.setInitMethodName("init");
			beanDefinitionBuilder.setDestroyMethodName("destroy");
		}
		registerBeanDefinition(beanFactory, beanName, beanDefinitionBuilder);
		return true;
	}

	// public static void registerBeanDefinition(String beanName, BeanDefinitionBuilder beanDefinitionBuilder) {
	// registerBeanDefinition(LeopardBeanFactoryAware.getBeanFactory(), beanName, beanDefinitionBuilder);
	// }

	public static void registerBeanDefinition(BeanFactory beanFactory, String beanName, BeanDefinitionBuilder beanDefinitionBuilder) {
		BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) beanFactory;
		BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

		beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);

		beanFactory.getBean(beanName);
	}

	public static boolean isEnable(String enable) {
		if ("enable".equals(enable) || "true".equals(enable)) {
			return true;
		}
		return false;
	}
	// public static void registerBeanDefinition(String beanName, String className) {
	// Class<?> clazz = ClassUtil.forName(className);
	// LeopardBeanFactoryAware.addBean(beanName, clazz);
	// }

	// public static void printParserContext(Class<?> clazz, ParserContext parserContext) {
	// System.err.println(clazz.getName() + " parserContext:" + parserContext);
	// }
}
