package io.leopard.mvc.trynb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.mvc.trynb.model.TrynbInfo;

public class TrynbServiceImpl implements TrynbService {

	protected Log logger = LogFactory.getLog(this.getClass());

	private List<TrynbApi> trynbApiList = new ArrayList<>();

	private final TrynbLogger trynbLogger = new TrynbLoggerImpl();

	public TrynbServiceImpl() {
		Iterator<TrynbApi> iterator = ServiceLoader.load(TrynbApi.class).iterator();
		if (iterator.hasNext()) {
			TrynbApi trynbApi = iterator.next();
			trynbApiList.add(trynbApi);
		}
		trynbApiList.add(new TrynbApiImpl());
	}

	@Override
	public TrynbInfo parse(HttpServletRequest request, String uri, Exception exception) {
		for (TrynbApi trynbApi : trynbApiList) {
			TrynbInfo trynbInfo = trynbApi.parse(trynbLogger, request, uri, exception);
			if (trynbInfo != null) {
				return trynbInfo;
			}
		}
		throw new RuntimeException("未找到异常解析器.");
	}

}
