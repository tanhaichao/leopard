package io.leopard.web4j.captcha;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.octo.captcha.image.ImageCaptcha;

/**
 * 验证码View.
 * 
 * @author 阿海
 * 
 */
public class CaptchaView extends ModelAndView {

	private static final String SESSION_KEY = "sessCaptcha";

	public static String getSessionKey(String captchaGroupId) {
		if (StringUtils.isEmpty(captchaGroupId)) {
			return SESSION_KEY;
		}
		else {
			return SESSION_KEY + ":" + captchaGroupId;
		}
	}

	private static final Map<String, LeopardEngine> engineMap = new ConcurrentHashMap<String, LeopardEngine>();

	protected static LeopardEngine getLeopardEngine(final int width, final int height, Class<? extends LeopardEngine> engineClazz) {
		String key = width + ":" + height + ":" + engineClazz.getName();
		LeopardEngine engine = engineMap.get(key);
		if (engine != null) {
			return engine;
		}
		engine = createLeopardEngine(key, width, height, engineClazz);
		return engine;
	}

	protected LeopardEngine getLeopardEngine() {
		return getLeopardEngine(width, height, engineClazz);
	}

	protected static synchronized LeopardEngine createLeopardEngine(String key, final int width, final int height, Class<? extends LeopardEngine> engineClazz) {
		LeopardEngine engine = engineMap.get(key);
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
		// engine = new LeopardEngineImpl() {
		// @Override
		// public int getWidth() {
		// return width;
		// }
		//
		// @Override
		// public int getHeight() {
		// return height;
		// }
		// };
		engineMap.put(key, engine);
		return engine;
	}

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {
		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setDateHeader("Expires", 0);
			// Set standard HTTP/1.1 no-cache headers.
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			// Set standard HTTP/1.0 no-cache header.
			response.setHeader("Pragma", "no-cache");
			// return a jpeg
			response.setContentType("image/jpeg");
			// create the image with the text
			HttpSession session = request.getSession();
			// String sessionId = session.getId();

			// String key = "captcha:" + sessionId;
			// imageCaptchaService.setCaptchaEngine(new DuowanEngine());
			ImageCaptcha imageCaptcha = getLeopardEngine().getNextImageCaptcha();
			String code = imageCaptcha.getTextChallenge();
			String captchaGroupId = (String) request.getAttribute("captchaGroupId");

			String sessionKey = getSessionKey(captchaGroupId);
			session.setAttribute(sessionKey, code);

			// System.out.println("session:" + session.getId() + " code:" + code + " captchaGroupId:" + captchaGroupId + " url:" + request.getRequestURI());

			BufferedImage bi = imageCaptcha.getImageChallenge();
			// BufferedImage bi = imageCaptchaService.getImageChallengeForID(sessionId);
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			try {
				out.flush();
			}
			finally {
				out.close();
			}
		}
	};

	private int width;
	private int height;

	private Class<? extends LeopardEngine> engineClazz;

	public CaptchaView() {
		this(200, 70);
	}

	public CaptchaView(int width, int height) {
		this(width, height, LeopardEngineImpl.class);
	}

	public CaptchaView(int width, int height, Class<? extends LeopardEngine> engineClazz) {
		super.setView(view);
		this.width = width;
		this.height = height;
		this.engineClazz = engineClazz;
	}

}
