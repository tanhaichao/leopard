package io.leopard.data.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Element;

public class ComponentScanBeanDefinitionParser extends org.springframework.context.annotation.ComponentScanBeanDefinitionParser {

	protected List<String> timerList = new ArrayList<String>();

	// name-generator="io.leopard.core.beans.LeopardAnnotationBeanNameGenerator"

	private ParserContext parserContext;

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// BeanDefinitionParserUtil.printParserContext(ComponentScanBeanDefinitionParser.class, parserContext);
		this.parserContext = parserContext;

		boolean qualifiedBeanName = "true".equals(element.getAttribute("qualified-name"));

		LeopardAnnotationBeanNameGenerator.setQualifiedBeanName(qualifiedBeanName);

		element.setAttribute("name-generator", LeopardAnnotationBeanNameGenerator.class.getName());

		// ahai 排除定时器?
		BeanDefinition beanDefinition = super.parse(element, parserContext);

		if (!timerList.isEmpty()) {
			this.createTimerService();
		}
		return beanDefinition;
	}

	@Override
	protected void registerComponents(XmlReaderContext readerContext, Set<BeanDefinitionHolder> beanDefinitions, Element element) {
		super.registerComponents(readerContext, beanDefinitions, element);
		for (BeanDefinitionHolder beanDefHolder : beanDefinitions) {
			String beanName = beanDefHolder.getBeanName();
			// TODO ahai 未规范实现
			if (beanName.endsWith("Timer")) {
				timerList.add(beanName);
			}
		}
	}

	protected BeanDefinition createTimerService() {
		String className = "io.leopard.web.timer.TimerServiceImpl";
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			return null;
		}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

		ManagedList<RuntimeBeanReference> timerBeanList = new ManagedList<RuntimeBeanReference>();
		for (String beanName : timerList) {
			timerBeanList.add(new RuntimeBeanReference(beanName));
		}
		builder.addPropertyValue("timers", timerBeanList);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		return RegisterComponentUtil.registerComponent(parserContext, builder, "timerService");
	}

	// protected void registerComponents(XmlReaderContext readerContext,
	// Set<BeanDefinitionHolder> beanDefinitions, Element element) {
	// super.registerComponents(readerContext, beanDefinitions, element);
	// }

}