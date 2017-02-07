package io.leopard.web.mvc.option;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import io.leopard.beans.Option;

public class OptionScanner extends ClassPathBeanDefinitionScanner {

	public OptionScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public void registerDefaultFilters() {
		this.addIncludeFilter(new AnnotationTypeFilter(Option.class));
	}

	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			System.err.println("holder:" + holder.getBeanName());
			// GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			// definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
			// definition.setBeanClass(FactoryBeanTest.class);
		}
		return beanDefinitions;
	}

	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(Option.class.getName());
	}

}
