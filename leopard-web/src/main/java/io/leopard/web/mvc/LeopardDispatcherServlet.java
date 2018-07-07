package io.leopard.web.mvc;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.web.servlet.ServerInitializerImpl;

public class LeopardDispatcherServlet extends OptionsDispatcherServlet {

	private static final long serialVersionUID = 1L;

	private List<ModelAndViewRender> renderList;

	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext context = super.initWebApplicationContext();
		{
			Map<String, ModelAndViewRender> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ModelAndViewRender.class, true, false);
			if (!matchingBeans.isEmpty()) {
				this.renderList = new ArrayList<ModelAndViewRender>(matchingBeans.values());
				AnnotationAwareOrderComparator.sort(this.renderList);
			}
		}

		return context;
	}

	public LeopardDispatcherServlet() {
		// System.err.println("classLoader:" + this.getClass().getClassLoader());
		new ServerInitializerImpl().run();
	}

	@Override
	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// logger.info("render:" + mv.getClass().getName() + " renderList:" + renderList);
		if (renderList != null) {
			for (ModelAndViewRender render : renderList) {
				render.render(mv, request, response);
			}
		}
		super.render(mv, request, response);
	}

	@Override
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
