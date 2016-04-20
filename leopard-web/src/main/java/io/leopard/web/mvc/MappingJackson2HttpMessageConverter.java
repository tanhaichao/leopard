package io.leopard.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class MappingJackson2HttpMessageConverter implements HttpMessageConverter<Object> {

	protected Log logger = LogFactory.getLog(this.getClass());

	private static final MediaType jsonType = MediaType.valueOf("text/plain;charset=UTF-8");

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		// logger.info("canRead clazz:" + clazz.getName() + " mediaType:" + mediaType.getType());
		return true;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType> list = new ArrayList<MediaType>();
		// list.add(MediaType.TEXT_PLAIN);
		return list;
	}

	@Override
	public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		// logger.info("read:" + clazz);
		return null;
	}

	@Override
	public void write(Object body, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// logger.info("write:" + data + " contentType:" + contentType.getType());
		// HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		outputMessage.getHeaders().setContentType(jsonType);
		outputMessage.getBody().write(((String) body).getBytes());
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		// new Exception().printStackTrace();
		// logger.info("canWrite clazz:" + clazz + " mediaType:" + mediaType);
		return true;
	}

}
