package io.leopard.data.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class LeopardAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator implements BeanNameGenerator {

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {

		String beanName = super.buildDefaultBeanName(definition);
		beanName = this.replaceBeanName(beanName);
		this.initPrimaryBean(definition);
		return beanName;
	}

	protected String replaceBeanName(String beanName) {
		if (beanName.endsWith("ServiceImpl")) {
			beanName = beanName.replace("ServiceImpl", "Service");
		}
		else if (beanName.endsWith("QueueImpl")) {
			beanName = beanName.replace("QueueImpl", "Queue");
		}
		else if (beanName.endsWith("HandlerImpl")) {
			beanName = beanName.replace("HandlerImpl", "Handler");
		}
		if (beanName.endsWith("LogicImpl")) {
			beanName = beanName.replace("LogicImpl", "Logic");
		}
		return beanName;
	}

	protected void initPrimaryBean(BeanDefinition definition) {
		String className = definition.getBeanClassName();
		if (className.endsWith("DaoCacheImpl")) {
			definition.setPrimary(true);
		}
		if (className.endsWith("ServiceTestImpl")) {
			definition.setPrimary(true);
			System.out.println("className:" + className);
		}
	}

}