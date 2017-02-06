package io.leopard.timer;

import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;

public class TimerBeanFactoryAware implements BeanFactoryAware {
	protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	private static boolean isInitialized = false;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (isInitialized) {
			// 为了防止spring重复配置导致定时器启动多次。
			throw new RuntimeException("定时器已启动.");
			// new RuntimeException("定时器已启动.").printStackTrace();
		}
		isInitialized = true;

		Map<String, Timer> map = ((ListableBeanFactory) beanFactory).getBeansOfType(Timer.class);

		for (Timer timer : map.values()) {
			TimerUtil.start(timer);
		}

	}

	@PreDestroy
	public void destroy() {
		isInitialized = false;
	}

}
