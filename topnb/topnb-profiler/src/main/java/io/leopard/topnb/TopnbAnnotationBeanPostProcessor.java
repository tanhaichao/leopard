package io.leopard.topnb;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

//@Component
public class TopnbAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

	public TopnbAnnotationBeanPostProcessor() {
		System.out.println("new TopnbAnnotationBeanPostProcessor");
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		// if (!(bean instanceof MethodTimeController)) {
		// return super.postProcessAfterInstantiation(bean, beanName);
		// }
		// System.err.println("TopnbAnnotationBeanPostProcessor bean:" + bean);
		// Class<?> clazz = bean.getClass();
		// for (Field field : clazz.getDeclaredFields()) {
		// if (field.getAnnotation(Autowired.class) == null) {
		// continue;
		// }
		//
		// System.err.println("field.getType():" + field.getType() + ":" + field.getName());
		//
		// Object value = TopnbBeanFactory.getApplicationContext().getBean(field.getType());
		//
		// field.setAccessible(true);
		// try {
		// field.set(bean, value);
		// }
		// catch (IllegalArgumentException e) {
		// throw new BeanInitializationException(e.getMessage(), e);
		// }
		// catch (IllegalAccessException e) {
		// throw new BeanInitializationException(e.getMessage(), e);
		// }
		//
		// }
		return false;
	}
}
