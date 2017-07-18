package io.leopard.sysconfig.dynamicenum;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;

import io.leopard.burrow.util.StringUtil;
import io.leopard.lang.inum.daynamic.DynamicEnum;

public abstract class DynamicEnumScanner extends ClassPathBeanDefinitionScanner {

	public DynamicEnumScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public void registerDefaultFilters() {
		this.addIncludeFilter(new AssignableTypeFilter(DynamicEnum.class));
	}

	@Override
	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		String beanClassName = beanDefinition.getBeanClassName();
		String simpleName = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
		String enumId = StringUtil.firstCharToLowerCase(simpleName);
		registerEnum(enumId, beanClassName);
		System.err.println("beanDefinition..." + beanDefinition.getBeanClassName() + " enumId:" + enumId);
		return false;// 不注册Spring Bean
	}

	protected abstract void registerEnum(String enumId, String beanClassName);

}
