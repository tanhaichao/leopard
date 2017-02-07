package io.leopard.myjetty.webapp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * 重启中的判断.
 * 
 * @author ahai
 *
 */
public class RestartingHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (response.isCommitted() || baseRequest.isHandled()) {
			return;
		}
		
		if (true) {
			// return;
		}
		System.out.println("RestartingHandler:" + target);
		baseRequest.setHandled(true);
		// response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		response.setContentType("text/html; charset=UTF-8");

		// response.sendError(arg0);
		byte[] body = getData();
		response.setContentLength(body.length);
		OutputStream out = response.getOutputStream();
		out.write(body);
		out.close();
	}

	protected byte[] getData() {

		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\n");
		sb.append("<title>服务器重启中</title>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<h2>HTTP ERROR: 503</h2>\n");
		sb.append("<p>访问 /topnb/index.leo出错. 原因:\n");
		sb.append("<pre>    程序重启中，预计还有xx秒重启完成.</pre></p>\n");
		sb.append("<hr /><i>Powered by MyJetty(Leopard Server)</i>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");

		try {
			return sb.toString().getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
