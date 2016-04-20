package io.leopard.web.mvc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import io.leopard.jdbc.LeopardBeanFactoryAware;

public class ImageUrlConverterImpl implements ImageUrlConverter {

	private static final ImageUrlConverter instance = new ImageUrlConverterImpl();

	private ImageUrlConverter imageUrlConverter;

	public static ImageUrlConverter getInstance() {
		return instance;
	}

	public ImageUrlConverterImpl() {
		// new Exception().printStackTrace();
		BeanFactory beanFactory = LeopardBeanFactoryAware.getBeanFactory();
		if (beanFactory != null) {
			try {
				this.imageUrlConverter = beanFactory.getBean(ImageUrlConverter.class);
			}
			catch (NoSuchBeanDefinitionException e) {

			}
		}
	}

	@Override
	public String convert(String uri) {
		if (imageUrlConverter != null) {
			return imageUrlConverter.convert(uri);
		}
		return uri;
	}

}
