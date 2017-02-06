package io.leopard.web.session;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Store implements IStore {

	private static final IStore instance = new Store();

	public static IStore getInstance() {
		return instance;
	}

	private IStore stroe;

	public Store() {
		Iterator<IStore> iterator = ServiceLoader.load(IStore.class).iterator();
		// System.err.println("Store iterator:" + iterator);
		while (iterator.hasNext()) {
			IStore stroe = iterator.next();
			// System.err.println("Store stroe:" + stroe);
			if (stroe.isEnable()) {
				this.stroe = stroe;
				break;
			}
		}

		if (this.stroe == null) {// 默认为redis实现
			this.stroe = new StoreRedisImpl();
		}
	}

	@Override
	public String get(String key) {
		// System.err.println("Stroe get:" + key);
		if (stroe != null) {
			return stroe.get(key);
		}
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public String set(String key, String value, int seconds) {
		if (stroe != null) {
			return stroe.set(key, value, seconds);
		}
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public Long del(String key) {
		if (stroe != null) {
			return stroe.del(key);
		}
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public boolean isEnable() {
		// System.err.println("Stroe isEnable:" + stroe);
		if (stroe != null) {
			return stroe.isEnable();
		}
		return false;
	}

}
