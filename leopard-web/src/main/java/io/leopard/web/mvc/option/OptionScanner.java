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

	@Override
	public void registerDefaultFilters() {
		this.addIncludeFilter(new AnnotationTypeFilter(Option.class));
	}

	@Override
	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(Option.class.getName());
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			String id = holder.getBeanName();
			String className = holder.getBeanDefinition().getBeanClassName();
			System.err.println("holder:" + holder.getBeanName() + " className:" + className);
			OptionData.load(id, className);
			// GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			// definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
			// definition.setBeanClass(FactoryBeanTest.class);
		}
		return beanDefinitions;
	}

}
