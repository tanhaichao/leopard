package io.leopard.web.freemarker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.web.freemarker.htdocs.ClassPathHtdocs;

/**
 * 资源文件访问.
 * 
 * @author 阿海
 */
public abstract class AbstractFileServlet extends ClassPathHtdocs {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("f");
		if (StringUtils.isEmpty(filename)) {
			throw new IllegalArgumentException("文件名不能为空.");
		}
		try {
			this.doFile(request, response, filename);
		}
		catch (Exception e) {
			logger.warn(e.getMessage(), e);
			StringBuilder sb = new StringBuilder();
			sb.append("<html>\n");
			sb.append("<head>\n");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\n");
			sb.append("<title>文件找不到</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("<h2>HTTP ERROR: 404</h2>\n");
			sb.append("<pre>    程序发布中，请耐心等候，预计还有xx秒重启完成.</pre></p>\n");
			sb.append("<hr /><i>Powered by Leopard</i>\n");
			sb.append("</body>\n");
			sb.append("</html>\n");

			byte[] bytes;
			try {
				bytes = sb.toString().getBytes("UTF-8");
			}
			catch (UnsupportedEncodingException e1) {
				throw new RuntimeException(e1.getMessage(), e1);
			}
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			OutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
		}
	}

	// @Override
	// public String getHtdocsPath() {
	// throw new NotImplementedException();
	// }

}
