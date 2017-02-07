package io.leopard.myjetty.workbench;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.util.StringUtils;

import io.leopard.myjetty.webapp.WebappService;

public class WebappController {

	private static final WebappController instance = new WebappController();

	public static WebappController getInstance() {
		return instance;
	}

	private WebappService webappService;

	public WebappService getWebappService() {
		return webappService;
	}

	public void setWebappService(WebappService webappService) {
		this.webappService = webappService;
	}

	public String start(String projectId) throws IOException {
		if (StringUtils.isEmpty(projectId)) {
			throw new IllegalArgumentException("projectId不能为空.");
		}
		try {
			webappService.start(projectId);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return "OK";
	}

	public String stop(String projectId) throws IOException {
		if (StringUtils.isEmpty(projectId)) {
			throw new IllegalArgumentException("projectId不能为空.");
		}
		try {
			webappService.stop(projectId);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return "OK";
	}

	public String restart(String projectId) throws IOException {
		try {
			this.stop(projectId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.start(projectId);
		return "OK";
	}

	public String install(String projectId) throws IOException {
		String path = getPath(projectId);
		String message = Shell.execute("mvn clean install", path);
		return message;
	}

	public String apidoc(String projectId) throws IOException {
		String path = getPath(projectId);

		String apidocDir = path + "/" + projectId + "-web/src/main/resources/apidoc";
		String classesDir = path + "/" + projectId + "-web/target/classes";
		//
		// svn up /data/src/zhongcao/zhongcao-web/src/main/resources/apidoc/
		// cp -f /data/src/zhongcao/zhongcao-web/src/main/resources/apidoc/*.txt /data/src/zhongcao/zhongcao-web/target/classes/apidoc/
		// cp -f /data/src/zhongcao/zhongcao-web/src/main/resources/apidoc/view-object/*.txt /data/src/zhongcao/zhongcao-web/target/classes/apidoc/view-object/
		Shell.execute("svn up", apidocDir);
		Shell.execute("cp -f " + apidocDir + "/*.txt " + classesDir + " apidoc/", path);
		Shell.execute("cp -f " + apidocDir + "/view-object/*.txt " + classesDir + " apidoc/view-object/", path);
		return "OK";
	}

	public String compile(String projectId, OutputStream output) throws IOException {
		String path = getPath(projectId);
		String message = Shell.execute("mvn compile", path, output);
		return message;
	}

	public String packaging(String projectId, OutputStream output) throws IOException {
		String path = getPath(projectId);
		String message = Shell.execute("mvn clean package -U", path, output);
		return message;
	}

	public String svnupdate(String projectId, OutputStream output) throws IOException {
		String path = getPath(projectId);
		String message = Shell.execute("svn up", path, output);
		return message;
	}

	protected String getPath(String projectId) {
		if (System.getProperty("os.name").startsWith("Windows")) {
			return "/work/olla/" + projectId;
		}
		else {
			return "/data/src/" + projectId;
		}

	}

}
