package io.leopard.topnb.methodtime;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.web.AbstractHttpServlet;
import io.leopard.topnb.web.Menu;
import io.leopard.topnb.web.freemarker.TopnbView;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
@WebServlet(name = "topnbMethodTimeServlet", urlPatterns = "/topnb/index.leo")
public class MethodTimeServlet extends AbstractHttpServlet implements Menu {

	private static final long serialVersionUID = 1L;

	// private static MethodTimeService performanceService = TopnbBeanFactory.getMethodTimeService();

	private static MethodTimeHandler performanceHandler = TopnbBeanFactory.getPerformanceHandler();

	private static EntryService entryService = TopnbBeanFactory.getEntryService();

	@Override
	public String getName() {
		return "方法耗时";
	}

	@Override
	public String getUrl() {
		return "/topnb/index.leo";
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// if (true) {
		// super.output(response, "test");
		// return;
		// }
		String entryName = request.getParameter("entryName");
		String order = request.getParameter("order");

		List<MethodDto> performanceVOList = performanceHandler.list(entryName);
		List<String> entryNameList = entryService.listAllEntryName();
		String typeName = performanceHandler.getTypeName(entryName);

		Collections.sort(performanceVOList, MethodTimeComparator.get(order));

		TopnbView view = new TopnbView("method_time");
		view.addObject("performanceVOList", performanceVOList);
		view.addObject("entryNameList", entryNameList);

		view.addObject("currentEntryName", entryName);
		view.addObject("typeName", typeName);
		// view.addObject("anomalyDetector", new MethodDetector(performanceVOList));
		view.render(request, response);
	}

}
