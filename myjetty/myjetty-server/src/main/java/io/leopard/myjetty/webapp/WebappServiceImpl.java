package io.leopard.myjetty.webapp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import io.leopard.myjetty.LoggerConstant;

public class WebappServiceImpl implements WebappService {

	protected static Log logger = LoggerConstant.getJettyLogger(WebappServiceImpl.class);

	private static final WebappService contextService = new WebappServiceImpl();

	public static WebappService getInstance() {
		return contextService;
	}

	private WebappDao webappDao = new WebappDaoImpl();
	private DirectoryDao directoryDao = new DirectoryDaoImpl();

	private ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

	private WebappServiceImpl() {
	}

	@Override
	public ContextHandlerCollection getContextHandlerCollection() {
		return contextHandlerCollection;
	}

	@Override
	public void addHandler(Handler handler) {
		this.contextHandlerCollection.addHandler(handler);
	}

	@Override
	public WebAppContext getWebapp(String war) {
		return webappDao.get(war);
	}

	@Override
	public WebAppContext remove(String war) {
		return webappDao.remove(war);
	}

	@Override
	public WebAppContext start(String war) throws Exception {
		Directory directory = directoryDao.get(war);
		if (directory == null) {
			throw new RuntimeException("项目[" + war + "]的目录信息不存在.");
		}
		// String rootDir = directory.getRootDir();
		List<String> hostList = directory.getHostList();
		return this.start(war, hostList);
	}

	@Override
	public WebAppContext start(String war, List<String> hostList) throws Exception {
		if (war == null || war.length() == 0) {
			throw new IllegalArgumentException("war不能为空.");
		}

		WebAppContext webapp = webappDao.add(war, hostList);
		contextHandlerCollection.addHandler(webapp);
		webapp.start();
		return webapp;
	}

	@Override
	public Directory addDirectory(String war, List<String> hostList) {
		Directory directory = new Directory();
		directory.setWar(war);
		directory.setHostList(hostList);
		this.directoryDao.add(directory);
		return directory;
	}

	@Override
	public WebAppContext stop(String war) throws Exception {
		WebAppContext webapp = this.webappDao.remove(war);
		if (webapp != null) {
			webapp.shutdown();
			webapp.stop();
			webapp.destroy();
			contextHandlerCollection.removeHandler(webapp);
		}
		return webapp;
	}
}
