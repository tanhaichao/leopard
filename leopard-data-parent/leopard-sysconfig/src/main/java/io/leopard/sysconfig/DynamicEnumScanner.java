package io.leopard.sysconfig;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.StringUtils;

import io.leopard.lang.inum.daynamic.DynamicEnum;

public class DynamicEnumScanner extends ClassPathBeanDefinitionScanner {

	public DynamicEnumScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public void registerDefaultFilters() {
		this.addIncludeFilter(new AssignableTypeFilter(DynamicEnum.class));
	}

	// @Override
	// public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
	// return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(Option.class.getName());
	// }

	// protected String getValue(BeanDefinition beanDefinition) {
	// // AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
	// String className = beanDefinition.getBeanClassName();
	// // AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(Option.class.getName(), false));
	// return className;
	// }

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			String id = holder.getBeanName();
			String className = holder.getBeanDefinition().getBeanClassName();
			logger.info("doScan id:" + id + " className:" + className);
			// OptionData.load(id, className);
			// definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
			// definition.setBeanClass(OptionFactoryBean.class);
		}
		return null;
	}

}
