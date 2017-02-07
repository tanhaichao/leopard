package io.leopard.myjetty.workbench;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import io.leopard.myjetty.web.freemarker.JsonView;
import io.leopard.myjetty.web.freemarker.MyJettyView;
import io.leopard.myjetty.webapp.WebappServiceImpl;

public class WebappServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private WebappController webappController = WebappController.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println("doGet uri:" + uri);
		if ("/workbench/webapp".equals(uri)) {
			this.index(request, response);
		}
		else if ("/workbench/webapp/".equals(uri)) {
			this.index(request, response);
		}
		else if ("/workbench/webapp/index".equals(uri)) {
			this.index(request, response);
		}
		else if ("/workbench/webapp/start".equals(uri)) {
			this.start(request, response);
		}
		else if ("/workbench/webapp/stop".equals(uri)) {
			this.stop(request, response);
		}
		else if ("/workbench/webapp/restart".equals(uri)) {
			this.restart(request, response);
		}
		else if ("/workbench/webapp/update".equals(uri)) {
			this.update(request, response);
		}
		else if ("/workbench/webapp/compile".equals(uri)) {
			this.compile(request, response);
		}
		else if ("/workbench/webapp/deploy".equals(uri)) {
			this.deploy(request, response);
		}
		else if ("/workbench/webapp/packaging".equals(uri)) {
			this.packaging(request, response);
		}

		else {
			throw new ServletException("未知地址[" + uri + "].");
		}
	}

	protected void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String projectId = this.getProjectId(request);

		MyJettyView view = new MyJettyView("webapp");
		view.addObject("projectId", projectId);
		view.render(request, response);
	}

	protected String getProjectId(HttpServletRequest request) {
		String projectId = request.getParameter("projectId");
		if (StringUtils.isEmpty(projectId)) {
			// TODO ahai
			// projectId = WebappServiceImpl.getInstance().getDefaultProjectId();
			// if (projectId == null) {
			// projectId = "";
			// }
		}
		return projectId;
	}

	protected void start(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		String projectId = this.getProjectId(request);
		this.webappController.start(projectId);

		JsonView view = new JsonView("starting");
		view.render(request, response);
	}

	protected void stop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		String projectId = this.getProjectId(request);
		this.webappController.stop(projectId);

		JsonView view = new JsonView("stop");
		view.render(request, response);
	}

	protected void restart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		String projectId = this.getProjectId(request);
		webappController.restart(projectId);

		JsonView view = new JsonView("restart");
		view.render(request, response);
	}

	protected void deploy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		String projectId = this.getProjectId(request);

		OutputStream output = response.getOutputStream();
		webappController.svnupdate(projectId, output);
		webappController.packaging(projectId, output);
		webappController.restart(projectId);

		// JsonView view = new JsonView("deploy");
		// view.render(request, response);
	}

	protected void packaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		String projectId = this.getProjectId(request);
		this.webappController.packaging(projectId, response.getOutputStream());

		response.flushBuffer();

		// JsonView view = new JsonView("packaging");
		// view.render(request, response);
	}

	protected void compile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		String projectId = this.getProjectId(request);
		this.webappController.compile(projectId, response.getOutputStream());

		// JsonView view = new JsonView("compile");
		// view.render(request, response);
	}

	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");

		String projectId = this.getProjectId(request);
		this.webappController.svnupdate(projectId, response.getOutputStream());
	}
}
