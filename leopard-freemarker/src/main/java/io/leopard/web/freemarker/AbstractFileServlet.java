package io.leopard.web.freemarker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import io.leopard.web.freemarker.htdocs.ClassPathHtdocs;

/**
 * 资源文件访问.
 * 
 * @author 阿海
 */
public abstract class AbstractFileServlet extends ClassPathHtdocs {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("f");
		if (StringUtils.isEmpty(filename)) {
			throw new IllegalArgumentException("文件名不能为空.");
		}
		this.doFile(request, response, filename);
	}

	// @Override
	// public String getHtdocsPath() {
	// throw new NotImplementedException();
	// }

}
