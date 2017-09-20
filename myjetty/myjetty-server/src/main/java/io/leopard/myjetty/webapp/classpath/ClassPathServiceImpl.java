package io.leopard.myjetty.webapp.classpath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceImpl implements ClassPathService {

	private List<ClassPathService> list = new ArrayList<ClassPathService>();

	public ClassPathServiceImpl() {
		// if (true) {
		// Iterator<IResourceLoader> iterator = ServiceLoader.load(IResourceLoader.class).iterator();
		// while (iterator.hasNext()) {
		// IResourceLoader loader = iterator.next();
		// System.err.println("ResourceLoaderImpl loader:" + loader.getClass().getName());
		// }
		// }
		// list.add(new ClassPathServiceDataSourceImpl());
		Iterator<ClassPathService> iterator = ServiceLoader.load(ClassPathService.class).iterator();
		while (iterator.hasNext()) {
			ClassPathService classPathService = iterator.next();
			// new Exception("classPathService:" + classPathService.getClass().getName()).printStackTrace();
			list.add(classPathService);
		}
	}

	@Override
	public List<Resource> preConfigure(WebAppContext context, List<String> hostList, String war) throws Exception {
		List<Resource> result = new ArrayList<>();
		for (ClassPathService service : list) {
			List<Resource> resourceList = service.preConfigure(context, hostList, war);
			if (resourceList != null) {
				result.addAll(resourceList);
			}
		}
		return result;
	}

}
