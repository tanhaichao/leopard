package io.leopard.sysconfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

import io.leopard.data4j.pubsub.IPubSub;
import io.leopard.data4j.pubsub.Publisher;
import io.leopard.jdbc.Jdbc;
import io.leopard.redis.Redis;

public class SysconfigBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware, SysconfigResolver, IPubSub {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected ConfigurableListableBeanFactory beanFactory;

	private Jdbc jdbc;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
		jdbc = beanFactory.getBean(Jdbc.class);
		Redis redis = (Redis) beanFactory.getBean("sessionRedis");
		Publisher.listen(this, redis);
	}

	private List<FieldInfo> fieldList = new ArrayList<FieldInfo>();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// System.err.println("postProcessBeforeInitialization:" + beanName);
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Value annotation = field.getAnnotation(Value.class);
			if (annotation == null) {
				continue;
			}
			field.setAccessible(true);
			Object value = resolveValue(annotation, field);
			if (value == null) {
				continue;
			}

			try {
				field.set(bean, value);
				FieldInfo fieldInfo = new FieldInfo();
				fieldInfo.setBean(bean);
				fieldInfo.setField(field);
				fieldList.add(fieldInfo);
			}
			catch (IllegalAccessException e) {
				throw new BeanInstantiationException(clazz, e.getMessage(), e);

			}
		}
		return bean;
	}

	private SysconfigDao sysconfigDao;

	protected Object resolveValue(Value anno, Field field) {
		String value = anno.value();
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		if (sysconfigDao == null) {
			sysconfigDao = new SysconfigDaoJdbcImpl(jdbc);
		}

		// logger.info("jdbc:" + jdbc);
		// ${aws.oss.endpoint}
		String key = value.replace("${", "").replace("}", "");
		return sysconfigDao.resolve(key, field.getType());
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public boolean update() {
		this.sysconfigDao.loadData();

		for (FieldInfo fieldInfo : fieldList) {
			Field field = fieldInfo.getField();
			Value annotation = field.getAnnotation(Value.class);
			Object value = resolveValue(annotation, field);

			if (value == null) {
				throw new NullPointerException("参数值怎么会为空?");
			}
			try {
				field.set(fieldInfo.getBean(), value);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		Publisher.publish(this, "update");
		return true;
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		logger.info("subscribe message:" + message + " isMySelf:" + isMySelf);
		if (isMySelf) {
			return;
		}
		this.update();
	}

}
