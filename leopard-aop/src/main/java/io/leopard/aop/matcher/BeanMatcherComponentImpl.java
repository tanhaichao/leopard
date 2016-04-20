package io.leopard.aop.matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务组件匹配器.
 * 
 * @author 阿海
 *
 */
public class BeanMatcherComponentImpl implements BeanMatcher {

	private List<String> beanNameRegex = new ArrayList<String>();

	public BeanMatcherComponentImpl() {
		beanNameRegex.add("^.*Controller$");
		beanNameRegex.add("^.*Service$");
		beanNameRegex.add("^.*DaoMysqlImpl$");
		beanNameRegex.add("^.*DaoRedisImpl$");
		beanNameRegex.add("^.*DaoHbaseImpl$");
		beanNameRegex.add("^.*DaoMemcachedImpl$");
		beanNameRegex.add("^.*DaoMemoryImpl$");
		beanNameRegex.add("^.*DaoFileImpl$");
		beanNameRegex.add("^.*DaoHttpImpl$");
	}

	@Override
	public Boolean matche(Object bean, String beanName, String className) {
		for (String regex : beanNameRegex) {
			// System.out.println("regex:" + regex);
			if (beanName.matches(regex)) {
				return true;
			}
		}
		return null;
	}

}
