package io.leopard.myjetty.workbench;

import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlushServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");

		System.out.println("size:" + response.getBufferSize());
		System.out.println("response:" + response.getClass());
		// response.setBufferSize(10);
		System.out.println("size:" + response.getBufferSize());
		// response.setHeader("Transfer-Encoding", "chunked");
		response.setContentType("text/html; charset=UTF-8");

		// response.setContentLength(10);

		ServletOutputStream out = response.getOutputStream();
		out.println("<!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->");
		out.println("<!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->");
		out.println("<!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->");

		out.println("start");

		// response.flushBuffer();

		for (int i = 0; i < 10; i++) {
			String line = i
					+ ":linelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelinelineline time:"
					+ new Date().toString() + "\n";
			out.println(line + "");
			out.flush();
			// response.flushBuffer();

			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// out.flush();
		out.close();

	}

}
