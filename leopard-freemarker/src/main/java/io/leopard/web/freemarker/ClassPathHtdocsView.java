package io.leopard.web.freemarker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import io.leopard.web.freemarker.htdocs.ClassPathHtdocs;

public class ClassPathHtdocsView extends ModelAndView {
	public ClassPathHtdocsView() {
		super(createView(null));
	}

	public ClassPathHtdocsView(String folder) {
		super(createView(folder));
	}

	protected static View createView(String folder) {
		HtdocsView view = new HtdocsView(folder);
		return view;
	}

	public static class HtdocsView extends ClassPathHtdocs implements View {

		private static final long serialVersionUID = 1L;

		private String folder;

		private ResourceLoader resourceLoader = new DefaultResourceLoader();

		@Override
		public InputStream readFile(HttpServletRequest request, String filename) throws IOException {
			filename = filename.replaceAll("/+", "/");
			isValidFilename(filename);

			if (!filename.startsWith("/")) {
				throw new IllegalArgumentException("非法文件名称[" + filename + "].");
			}

			String path;
			if (StringUtils.isNotEmpty(folder)) {
				path = "/htdocs" + folder + filename.substring(filename.indexOf("/", 2));
			}
			else {
				path = "/htdocs" + filename;
			}
			// System.out.println("path:" + path);
			Resource resource = resourceLoader.getResource(path);
			if (resource == null || !resource.exists()) {
				throw new FileNotFoundException(path);
			}
			return resource.getInputStream();
		}

		public HtdocsView(String folder) {
			this.folder = folder;
		}

		@Override
		public String getContentType() {
			return "text/html; charset=UTF-8";
		}

		@Override
		public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String filename = request.getRequestURI();
			// System.out.println("render filename:" + filename);
			this.doFile(request, response, filename);
		}

		@Override
		public String getHtdocsPath() {
			throw new NotImplementedException();
		}

	}
}
