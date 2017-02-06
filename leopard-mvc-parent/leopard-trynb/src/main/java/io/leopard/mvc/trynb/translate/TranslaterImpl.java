package io.leopard.mvc.trynb.translate;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import io.leopard.jdbc.LeopardBeanFactoryAware;

public class TranslaterImpl implements Translater {

	private static final Translater instance = new TranslaterImpl();

	public static Translater getInstance() {
		return instance;
	}

	private TranslaterImpl() {

	}

	protected Translater translater;

	private boolean loaded;

	private void load() {
		try {
			translater = LeopardBeanFactoryAware.getBeanFactory().getBean(Translater.class);
		}
		catch (NoSuchBeanDefinitionException e) {

		}
		this.loaded = true;
	}

	@Override
	public String translate(String message) {
		if (!loaded) {
			this.load();
		}
		if (translater == null) {
			return message;
		}
		return translater.translate(message);
	}

	@Override
	public boolean isEnable() {
		if (!loaded) {
			this.load();
		}
		if (translater == null) {
			return false;
		}
		return translater.isEnable();
	}

}
