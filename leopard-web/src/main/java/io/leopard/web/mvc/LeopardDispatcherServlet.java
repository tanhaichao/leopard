package io.leopard.web.mvc;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.ServletWebRequest;

import io.leopard.web.servlet.ServerInitializerImpl;

public class LeopardDispatcherServlet extends OptionsDispatcherServlet {

	private static final long serialVersionUID = 1L;

	public LeopardDispatcherServlet() {
		// System.err.println("classLoader:" + this.getClass().getClassLoader());
		new ServerInitializerImpl().run();
	}

	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		String mimeType = getServletContext().getMimeType(uri);
		// System.err.println("request:" + uri + " mimeType:" + mimeType);
		InputStream input = request.getServletContext().getResourceAsStream(uri);
		if (input == null) {
			super.noHandlerFound(request, response);
			return;
		}

		{
			URL url = request.getServletContext().getResource(uri);
			File file = new File(url.getFile());
			if (new ServletWebRequest(request, response).checkNotModified(file.lastModified())) {
				logger.debug("Resource not modified - returning 304");
				return;
			}
		}

		byte[] bytes = IOUtils.toByteArray(input);

		response.setContentLength(bytes.length);
		if (mimeType != null) {
			response.setContentType(mimeType);
		}

		ServletOutputStream out = response.getOutputStream();
		out.write(bytes);
	}

	// @Override
	// protected void initStrategies(ApplicationContext context) {
	// super.initStrategies(context);
	//
	// try {
	// Field field = DispatcherServlet.class.getDeclaredField("handlerMappings");
	// field.setAccessible(true);
	// @SuppressWarnings("unchecked")
	// List<HandlerMapping> handlerMappings = (List<HandlerMapping>) field.get(this);
	// for (HandlerMapping mapping : handlerMappings) {
	// logger.info("initStrategies mapping:" + mapping);
	// }
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	//
	// }
}
