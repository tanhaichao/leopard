package io.leopard.web.xparam.api;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

public class UserinfoResolverImpl implements UserinfoResolver {

	private static BeanFactory beanFactory;

	public static void setBeanFactory(BeanFactory beanFactory) {
		UserinfoResolverImpl.beanFactory = beanFactory;
	}

	private static final UserinfoResolver instance = new UserinfoResolverImpl();

	public static UserinfoResolver getInstance() {
		return instance;
	}

	protected UserinfoResolver getUserinfoResolver() {
		if (beanFactory == null) {
			return null;
		}
		try {
			UserinfoResolver userinfoResolver = beanFactory.getBean(UserinfoResolver.class);
			return userinfoResolver;
		}
		catch (BeansException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Userinfo getUserinfo(long uid) {
		UserinfoResolver userinfoResolver = this.getUserinfoResolver();
		if (userinfoResolver == null) {
			return null;
		}
		return userinfoResolver.getUserinfo(uid);
	}

	@Override
	public long getUid(String passport) {
		UserinfoResolver userinfoResolver = this.getUserinfoResolver();
		if (userinfoResolver == null) {
			return 0;
		}
		return userinfoResolver.getUid(passport);
	}

}
