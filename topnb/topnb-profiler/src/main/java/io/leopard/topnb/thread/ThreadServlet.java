package io.leopard.topnb.thread;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.web.Menu;
import io.leopard.topnb.web.freemarker.TopnbView;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
@WebServlet(name = "topnbThreadServlet", urlPatterns = "/topnb/thread.leo")
public class ThreadServlet extends HttpServlet implements Menu {

	private static final long serialVersionUID = 1L;

	private static ThreadService threadService = TopnbBeanFactory.getThreadService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ThreadInfo> threadInfoList = threadService.listAll();

		TopnbView view = new TopnbView("thread");
		view.addObject("threadInfoList", threadInfoList);
		view.render(request, response);
	}

	@Override
	public String getName() {
		return "线程数量";
	}

	@Override
	public String getUrl() {
		return "/topnb/thread.leo";
	}
}
