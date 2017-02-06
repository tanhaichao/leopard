package io.leopard.burrow.lang;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextImpl implements Context {
	protected Log logger = LogFactory.getLog(this.getClass());
	private Log beanLogger = LogFactory.getLog("BEANLOG." + this.getClass().getName());

	@PostConstruct
	@Override
	public void init() {
		beanLogger.info("init");
	}

	@PreDestroy
	@Override
	public void destroy() {
		beanLogger.info("destroy");
	}
}
