package io.leopard.mvc.trynb;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

import io.leopard.mvc.trynb.model.TrynbInfo;

public class TrynbApiImpl implements TrynbApi {

	private TrynbApi trynbApi;

	public TrynbApiImpl() {
		Iterator<TrynbApi> iterator = ServiceLoader.load(TrynbApi.class).iterator();
		if (iterator.hasNext()) {
			trynbApi = iterator.next();
		}
	}

	@Override
	public TrynbInfo parse(TrynbLogger trynbLogger, HttpServletRequest request, String uri, Exception exception) {
		if (trynbApi == null) {
			return null;
		}
		return trynbApi.parse(trynbLogger, request, uri, exception);
	}

}
