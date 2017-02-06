package io.leopard.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;

public class FillerContext implements BeanFactoryAware {

	protected static Log logger = LogFactory.getLog(FillerContext.class);

	private static List<BeanFiller> fillerList = new ArrayList<BeanFiller>();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		Map<String, BeanFiller> beanMap = ((DefaultListableBeanFactory) beanFactory).getBeansOfType(BeanFiller.class);
		for (Entry<String, BeanFiller> entry : beanMap.entrySet()) {
			fillerList.add(entry.getValue());
		}
		// new Exception("ConverterContext").printStackTrace();
	}

	public static void fill(Object source, Object target) {
		Assert.notNull(source, "参数bean不能为null.");
		Assert.notNull(target, "参数target不能为null.");
		try {
			parse(source, target);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void parse(Object source, Object target) throws Exception {
		Class<?> sourceClass = source.getClass();
		Class<?> targetClass = target.getClass();
		for (BeanFiller filler : fillerList) {
			if (filler.supports(sourceClass, targetClass)) {
				filler.fill(source, target);
			}
		}
	}

}
