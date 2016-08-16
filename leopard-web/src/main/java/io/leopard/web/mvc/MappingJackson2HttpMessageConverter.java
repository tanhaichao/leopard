package io.leopard.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class MappingJackson2HttpMessageConverter implements HttpMessageConverter<Object> {

	protected Log logger = LogFactory.getLog(this.getClass());

	// TODO ahai 这里使用application/json是否会有安全性问题?
	private static final MediaType jsonType = MediaType.valueOf("application/json;charset=UTF-8");

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

	private static List<String> allowedHeaders = new ArrayList<String>();
	private static List<HttpMethod> allowedMethods = new ArrayList<HttpMethod>();
	static {
		allowedHeaders.add("x-requested-with");
		allowedHeaders.add("content-type");

		// TODO ahai 这里是否结合Controller定义输出?
		allowedMethods.add(HttpMethod.GET);
		allowedMethods.add(HttpMethod.POST);
	}

	@Override
	public void write(Object body, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		outputMessage.getHeaders().setContentType(jsonType);

		// header('content-type:application:json;charset=utf8');
		// header('Access-Control-Allow-Origin:*');
		// header('Access-Control-Allow-Methods:POST');
		// header('Access-Control-Allow-Headers:x-requested-with,content-type');

		// logger.info("setAccessControlAllowOrigin:*");
		outputMessage.getHeaders().setAccessControlAllowOrigin("*");// FIXME 暂时的写法
		// outputMessage.getHeaders().setAccessControlAllowMethods(allowedMethods);
		// outputMessage.getHeaders().setAccessControlAllowHeaders(allowedHeaders);

		outputMessage.getBody().write(((String) body).getBytes());
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		// new Exception().printStackTrace();
		// logger.info("canWrite clazz:" + clazz + " mediaType:" + mediaType);
		return true;
	}

}
