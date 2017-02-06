package io.leopard.jetty.configuration;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.annotations.AbstractDiscoverableAnnotationHandler;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationParser;
import org.eclipse.jetty.annotations.ClassNameResolver;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 解决jetty自带的AnnotationConfiguration只扫描WEB-INF/classes的问题，maven编译后的目录为target， 不符合其规则
 * 
 * @author dw_lixuan
 * @version version1
 * @since 2013-02-18
 */
public class EmbedAnnotionConfiguration extends AnnotationConfiguration {
	protected static final Logger LOG = Log.getLogger(EmbedAnnotionConfiguration.class);

	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		// LOG.info("preConfigure");
		super.preConfigure(context);
	}

	@Override
	public void parseContainerPath(final WebAppContext context, final AnnotationParser parser) throws Exception {
		super.parseContainerPath(context, parser);
		// parse(context, parser);
	}

	// @Override
	// public void configure(WebAppContext context) throws Exception {
	// boolean metadataComplete = context.getMetaData().isMetaDataComplete();
	// context.addDecorator(new AnnotationDecorator(context));
	//
	// // Even if metadata is complete, we still need to scan for
	// ServletContainerInitializers - if there are any
	// AnnotationParser parser = null;
	// if (!metadataComplete) {
	// // If metadata isn't complete, if this is a servlet 3 webapp or
	// isConfigDiscovered is true, we need to search for annotations
	// if (context.getServletContext().getEffectiveMajorVersion() >= 3 ||
	// context.isConfigurationDiscovered()) {
	// _discoverableAnnotationHandlers.add(new
	// WebServletAnnotationHandler(context));
	// _discoverableAnnotationHandlers.add(new
	// WebFilterAnnotationHandler(context));
	// _discoverableAnnotationHandlers.add(new
	// WebListenerAnnotationHandler(context));
	// }
	// }
	//
	// // Regardless of metadata, if there are any ServletContainerInitializers
	// with @HandlesTypes, then we need to scan all the
	// // classes so we can call their onStartup() methods correctly
	// createServletContainerInitializerAnnotationHandlers(context,
	// getNonExcludedInitializers(context));
	//
	// if (!_discoverableAnnotationHandlers.isEmpty() ||
	// _classInheritanceHandler != null ||
	// !_containerInitializerAnnotationHandlers.isEmpty()) {
	// parser = createAnnotationParser();
	//
	// parse(context, parser);
	//
	// for (AnnotationParser.DiscoverableAnnotationHandler h :
	// _discoverableAnnotationHandlers) {
	// context.getMetaData().addDiscoveredAnnotations(((AbstractDiscoverableAnnotationHandler)
	// h).getAnnotationList());
	// }
	// }
	// }

	// protected void parse(final WebAppContext context, AnnotationParser parser) throws Exception {
	// List<Resource> _resources = getResources(getClass().getClassLoader());
	//
	// for (Resource _resource : _resources) {
	// if (_resource == null) {
	// return;
	// }
	//
	// parser.clearHandlers();
	// for (AnnotationParser.DiscoverableAnnotationHandler h : _discoverableAnnotationHandlers) {
	// if (h instanceof AbstractDiscoverableAnnotationHandler) {
	// ((AbstractDiscoverableAnnotationHandler) h).setResource(null); //
	// }
	// }
	// parser.registerHandlers(_discoverableAnnotationHandlers);
	// parser.registerHandler(_classInheritanceHandler);
	// parser.registerHandlers(_containerInitializerAnnotationHandlers);
	//
	// parser.parse(_resource, new ClassNameResolver() {
	// public boolean isExcluded(String name) {
	// if (context.isSystemClass(name)) {
	// return true;
	// }
	// if (context.isServerClass(name)) {
	// return false;
	// }
	// return false;
	// }
	//
	// public boolean shouldOverride(String name) {
	// // looking at webapp classpath, found already-parsed class
	// // of same name - did it come from system or duplicate in
	// // webapp?
	// return !context.isParentLoaderPriority();
	// }
	// });
	// }
	// }

	protected List<Resource> getResources(ClassLoader aLoader) throws IOException {
		// if (aLoader instanceof URLClassLoader) {
		List<Resource> _result = new ArrayList<Resource>();
		URL[] _urls = ((URLClassLoader) aLoader).getURLs();
		for (URL _url : _urls) {
			// System.err.println("config _url:"+_url);
			_result.add(Resource.newResource(_url));
		}
		return _result;
		// }
		// return Collections.emptyList();
	}

}
