package io.leopard.myjetty.webapp.classpath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceImpl implements ClassPathService {

	private List<ClassPathService> list = new ArrayList<ClassPathService>();

	public ClassPathServiceImpl() {
		// list.add(new ClassPathServiceDataSourceImpl());

		Iterator<ClassPathService> iterator = ServiceLoader.load(ClassPathService.class).iterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
	}

	@Override
	public void preConfigure(WebAppContext context, List<String> hostList, String war) throws Exception {
		for (ClassPathService service : list) {
			service.preConfigure(context, hostList, war);
		}
	}

}
