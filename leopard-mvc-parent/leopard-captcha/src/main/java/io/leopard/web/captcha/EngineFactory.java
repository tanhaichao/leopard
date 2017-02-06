package io.leopard.web.captcha;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EngineFactory {

	private static final Map<String, CaptchaEngine> engineMap = new ConcurrentHashMap<String, CaptchaEngine>();

	protected static CaptchaEngine getCaptchaEngine(final int width, final int height, Class<? extends CaptchaEngine> engineClazz) {
		String key = width + ":" + height + ":" + engineClazz.getName();
		CaptchaEngine engine = engineMap.get(key);
		if (engine != null) {
			return engine;
		}
		engine = createCaptchaEngine(key, width, height, engineClazz);
		return engine;
	}

	protected static synchronized CaptchaEngine createCaptchaEngine(String key, final int width, final int height, Class<? extends CaptchaEngine> engineClazz) {
		CaptchaEngine engine = engineMap.get(key);
		if (engine != null) {
			return engine;
		}
		try {
			engine = engineClazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		engine.setHeight(height);
		engine.setWidth(width);

		engine.initialFactories();
		engineMap.put(key, engine);
		return engine;
	}
}
