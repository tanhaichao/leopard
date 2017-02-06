package io.leopard.jetty;

import java.lang.reflect.Field;

import javax.servlet.UnavailableException;

import org.eclipse.jetty.server.ResourceCache;
import org.eclipse.jetty.servlet.DefaultServlet;

/**
 * 为了解决jetty useFileMappedBuffer=false失效的bug.
 * 
 * @author 阿海
 * 
 */
public class LeopardDefaultServlet extends DefaultServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws UnavailableException {
		super.init();
		try {
			this.initUseFileMappedBuffer();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void initUseFileMappedBuffer() throws Exception {
		Field field = DefaultServlet.class.getDeclaredField("_cache");
		field.setAccessible(true);
		ResourceCache cache = (ResourceCache) field.get(this);
		Field bufferField = ResourceCache.class.getDeclaredField("_useFileMappedBuffer");
		bufferField.setAccessible(true);
		bufferField.set(cache, false);
		// _useFileMappedBuffer
		// cache.
	}
}
