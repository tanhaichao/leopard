package io.leopard.web.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import io.leopard.spring.LeopardBeanFactoryAware;

public class VoFillerImpl implements VoFiller {
	private List<VoFiller> fillerHandlers;

	public void init() {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) LeopardBeanFactoryAware.getBeanFactory();
		Map<String, VoFiller> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, VoFiller.class, true, false);
		if (!matchingBeans.isEmpty()) {
			this.fillerHandlers = new ArrayList<VoFiller>(matchingBeans.values());
			AnnotationAwareOrderComparator.sort(this.fillerHandlers);
		}
	}

	@Override
	public Object fill(Object data) {
		if (fillerHandlers != null) {
			for (VoFiller filler : fillerHandlers) {
				data = filler.fill(data);
			}
		}
		return data;
	}

}
