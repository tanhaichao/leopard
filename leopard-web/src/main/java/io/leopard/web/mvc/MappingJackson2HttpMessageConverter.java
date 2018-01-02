package io.leopard.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import io.leopard.mvc.cors.CorsConfig;
import io.leopard.web.servlet.RequestUtil;

public class MappingJackson2HttpMessageConverter implements HttpMessageConverter<Object> {

	protected Log logger = LogFactory.getLog(this.getClass());

	// TODO ahai 这里使用application/json是否会有安全性问题?
	public static final MediaType jsonType = MediaType.valueOf("application/json;charset=UTF-8");

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

	@Autowired
	private CorsConfig corsConfig;

	@Override
	public void write(Object body, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		outputMessage.getHeaders().setContentType(jsonType);

		// outputMessage.getHeaders().setAccessControlAllowOrigin("*");// FIXME 暂时的写法

		if (corsConfig.isEnable()) {
			HttpServletRequest request = RequestUtil.getCurrentRequest();
			String allowOrigin = corsConfig.getAccessControlAllowOrigin(request);
			if (StringUtils.isNotEmpty(allowOrigin)) {
				outputMessage.getHeaders().set("Access-Control-Allow-Origin", allowOrigin);
				outputMessage.getHeaders().set("Access-Control-Allow-Credentials", "true");
				// outputMessage.getHeaders().set("Access-Control-Allow-Methods", "GET, POST");
				// outputMessage.getHeaders().set("Access-Control-Allow-Headers", "x_requested_with,content-type");
			}
		}

		// System.err.println("ok");
		outputMessage.getBody().write(((String) body).getBytes());
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		// new Exception().printStackTrace();
		// logger.info("canWrite clazz:" + clazz + " mediaType:" + mediaType);
		return true;
	}

}
