package io.leopard.web.passport;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
@WebServlet(name = "passportLoginServlet", urlPatterns = "/passport/login.leo")
public class PassportLoginServlet extends HttpServlet {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "sessUid";
		}
		boolean flag;
		try {
			flag = Finder.getInstance().find(type).login(request, response);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.output(response, e.getMessage());
			return;
		}
		if (!flag) {
			this.output(response, "未实现PassportValidate.login接口");
			return;
		}
	}

	private void output(HttpServletResponse response, String text) throws IOException {
		byte[] bytes = text.getBytes();
		response.setContentType("text/plain; charset=UTF-8");
		response.setContentLength(bytes.length);
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();
	}

}
