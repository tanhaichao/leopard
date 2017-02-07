package io.leopard.myjetty.workbench;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import io.leopard.web.freemarker.AbstractFileServlet;

/**
 * 接口文档
 * 
 * @author 阿海
 */
public class FileServlet extends AbstractFileServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected InputStream getRealAsInputStream(HttpServletRequest request, String filename) {
		return FileServlet.class.getResourceAsStream("/myjetty/" + filename);
		// String path = request.getServletContext().getRealPath(".");
		// System.out.println("filename:" + filename);
		// System.out.println("path:" + path);
		// File file = new File(path);
		// if (!file.exists()) {
		// return null;
		// }
		// if (!file.isFile()) {
		// return null;
		// }
		// try {
		// return new FileInputStream(path);
		// }
		// catch (FileNotFoundException e) {
		// return null;
		// }
	}

	@Override
	public String getHtdocsPath() {
		return "/myjetty/htdocs";
	}

}
