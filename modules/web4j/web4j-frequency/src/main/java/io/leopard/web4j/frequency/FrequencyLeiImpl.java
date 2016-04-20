package io.leopard.web4j.frequency;

import io.leopard.redis.Redis;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class FrequencyLeiImpl implements FrequencyLei, BeanFactoryAware {

	private Redis redis;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory context = (DefaultListableBeanFactory) beanFactory;
		Map<String, Redis> map = context.getBeansOfType(Redis.class);
		if (!map.isEmpty()) {
			Redis redis = map.get("sessionRedis");
			if (redis == null) {
				redis = map.entrySet().iterator().next().getValue();// 获取第一个
			}
			this.redis = redis;
		}
	}

	// public void setRedis(Redis redis) {
	// this.redis = redis;
	// }

	protected String getKey(String user, String uri) {
		return "ul:" + user + ":" + uri;
	}

	@Override
	public void request(String user, String uri, int seconds) throws FrequencyException {
		String key = this.getKey(user, uri);
		Long result = redis.setnx(key, "");
		if (result == 0) {
			throw new FrequencyException(user, uri);
		}
		redis.expire(key, seconds);
	}
}
