package io.leopard.web.frequency;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

public class Store implements IStore {

	private static final IStore instance = new Store();

	public static IStore getInstance() {
		return instance;
	}

	private IStore stroe;

	public Store() {
		Iterator<IStore> iterator = ServiceLoader.load(IStore.class).iterator();
		while (iterator.hasNext()) {
			IStore stroe = iterator.next();
			this.stroe = stroe;
			break;
		}

		if (this.stroe == null) {// 默认为redis实现
			this.stroe = new StoreRedisImpl();
		}
	}

	@Override
	public Object getPassport(HttpServletRequest request, Object handler) {
		Object passport = this.stroe.getPassport(request, handler);
		if (passport == null) {
			passport = request.getAttribute("passport");
		}
		return passport;
	}

	@Override
	public boolean set(String key) {
		return stroe.set(key);
	}

	@Override
	public void expire(String key, int seconds) {
		stroe.expire(key, seconds);
	}

}
