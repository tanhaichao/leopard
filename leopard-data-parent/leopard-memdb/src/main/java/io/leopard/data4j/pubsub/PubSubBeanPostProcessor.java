package io.leopard.data4j.pubsub;

import io.leopard.redis.Redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class PubSubBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	protected ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	protected Redis getRedis() {
		Redis redis = this.findRedis();
		// AssertUtil.assertNotNull(redis, "找不到redis连接 .");
		if (redis == null) {
			throw new IllegalArgumentException("找不到redis连接 .");
		}
		return redis;
	}

	protected Redis findRedis() {
		Redis redis = this.findRedisBySessionRedis();
		if (redis != null) {
			return redis;
		}
		// redis = this.findRedisByMemcache();
		// if (redis != null) {
		// return redis;
		// }
		return null;
	}

	protected Redis findRedisBySessionRedis() {
		try {
			return (Redis) beanFactory.getBean("sessionRedis");
		}
		catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	// protected Redis findRedisByMemcache() {
	// Memcache memcache;
	// try {
	// memcache = (Memcache) beanFactory.getBean("memcache");
	// }
	// catch (NoSuchBeanDefinitionException e) {
	// return null;
	// }
	//
	// if (memcache == null) {
	// return null;
	// }
	// if (!(memcache instanceof MemcacheRedisImpl)) {
	// return null;
	// }
	// return ((MemcacheRedisImpl) memcache).getRedis();
	// }

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof IPubSub) {
			System.err.println("beanName:" + beanName + " bean:" + bean);
			Publisher.listen((IPubSub) bean, this.getRedis());
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
