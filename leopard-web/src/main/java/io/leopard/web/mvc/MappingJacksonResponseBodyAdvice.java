package io.leopard.web.mvc;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import io.leopard.data.env.EnvUtil;

public class MappingJacksonResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	private ObjectWriter formatWriter;

	private ObjectMapper mapper; // can reuse, share

	private VoFillerImpl voFiller = new VoFillerImpl();

	@Value("${xparam.underline}")
	private String underline;

	@PostConstruct
	public void init() {
		voFiller.init();

		boolean enable = !"false".equals(underline);
		// System.err.println("MappingJacksonResponseBodyAdvice underline:" + underline + " enable:" + enable);
		if (enable) {
			this.formatWriter = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES).writer().withDefaultPrettyPrinter();
			this.mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		}
		else {
			this.formatWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
			this.mapper = new ObjectMapper();
		}
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest req,
			ServerHttpResponse response) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String callback = request.getParameter("callback");
		boolean format = "true".equals(request.getParameter("format"));
		// System.err.println("write t:" + t);

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", "success");

		if (EnvUtil.isDevEnv()) {// TODO 这里加上开发环境判断
			Object debugInfo = request.getAttribute("debug");
			if (debugInfo != null) {
				map.put("debug", debugInfo);
			}
			data = voFiller.fill(data);
		}
		map.put("data", data);

		String json = null;
		if (format) {
			try {
				json = formatWriter.writeValueAsString(map);
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				json = this.mapper.writeValueAsString(map);
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("result.json", json);
		request.setAttribute("result.data", data);

		if (StringUtils.isNotEmpty(callback)) {
			if (!isValidCallback(callback)) {
				callback = "callback";
			}
			return callback + "(" + json + ");";
		}
		return json;
	}

	protected static boolean isValidCallback(String callback) {
		if (StringUtils.isEmpty(callback)) {
			throw new IllegalArgumentException("参数callback不能为空.");
		}
		boolean isValidCallback = callback.matches("^[a-zA-Z0-9_\\.]+$");
		return isValidCallback;
	}
}
