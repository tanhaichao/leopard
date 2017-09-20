package io.leopard.myjetty.webapp.classpath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.eclipse.jetty.webapp.WebAppContext;

import io.leopard.jetty.handler.IResourceLoader;

public class ClassPathServiceImpl implements ClassPathService {

	private List<ClassPathService> list = new ArrayList<ClassPathService>();

	public ClassPathServiceImpl() {
		if (true) {
			Iterator<IResourceLoader> iterator = ServiceLoader.load(IResourceLoader.class).iterator();
			while (iterator.hasNext()) {
				IResourceLoader loader = iterator.next();
				System.err.println("ResourceLoaderImpl loader:" + loader.getClass().getName());
			}
		}

		// list.add(new ClassPathServiceDataSourceImpl());
		Iterator<ClassPathService> iterator = ServiceLoader.load(ClassPathService.class).iterator();
		while (iterator.hasNext()) {
			ClassPathService classPathService = iterator.next();
			// new Exception("classPathService:" + classPathService.getClass().getName()).printStackTrace();
			list.add(classPathService);
		}
	}

	@Override
	public void preConfigure(WebAppContext context, List<String> hostList, String war) throws Exception {
		for (ClassPathService service : list) {
			service.preConfigure(context, hostList, war);
		}
	}

}
