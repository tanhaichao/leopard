package io.leopard.web.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;

public class BeanLoader {

	public static <T> List<T> load(BeanFactory beanFactory, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		{
			ListableBeanFactory factory = (ListableBeanFactory) beanFactory;
			Map<String, T> map = factory.getBeansOfType(clazz);
			for (T bean : map.values()) {
				list.add(bean);
			}
		}

		{
			Iterator<T> iterator = ServiceLoader.load(clazz).iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
		}

		return list;
	}
}
