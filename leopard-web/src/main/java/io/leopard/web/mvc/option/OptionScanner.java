package io.leopard.web.mvc.option;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import io.leopard.lang.inum.Option;

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

	protected String getValue(BeanDefinition beanDefinition) {
		AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(Option.class.getName(), false));
		return attributes.getString("value");
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			String id = getValue(definition);
			if (StringUtils.isEmpty(id)) {
				id = holder.getBeanName();
			}
			String className = holder.getBeanDefinition().getBeanClassName();
			// logger.info("holder:" + holder.getBeanName() + " className:" + className);
			OptionData.load(id, className);
			definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
			definition.setBeanClass(OptionFactoryBean.class);
		}
		return beanDefinitions;
	}

}
