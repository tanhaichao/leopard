package io.leopard.mvc.cors;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

public class CorsConfigTest {

	@Test
	public void get() throws Exception {
		String url = getHostAndPort("http://weixin.zhhr.xiaoniu.io", "zhhr.com", "*.zhhr.xiaoniu.io");
		System.err.println("url:" + url);
	}

	@Test
	public void getHostAndPort() throws Exception {
		// getHostAndPort("http://baidu.com", "*");
		// getHostAndPort("http://baidu.com", "zhhr.com");
		// getHostAndPort("http://zhhr.com", "zhhr.com");
		Assert.assertEquals(getHostAndPort("http://local.zhhr.com", "zhhr.com", "*.zhhr.com"), "http://local.zhhr.com");
		Assert.assertEquals(getHostAndPort("http://local.zhhr.com", "*"), "http://local.zhhr.com");
		Assert.assertEquals(getHostAndPort("http://zhhr.com", "zhhr.com", "*.zhhr.com"), "http://zhhr.com");
		Assert.assertEquals(getHostAndPort("http://zhhr.com:8080/abc", "zhhr.com", "*.zhhr.com"), "http://zhhr.com:8080");
		Assert.assertEquals(getHostAndPort("http://xxx.baidu.com", "zhhr.com", "*.zhhr.com"), null);
		Assert.assertEquals(getHostAndPort("http://baidu.com", "zhhr.com", "*.zhhr.com"), null);
		Assert.assertEquals(getHostAndPort("lexfei"), null);
	}

	private static String getHostAndPort(String referer, String... origins) throws Exception {
		String cors = StringUtils.arrayToDelimitedString(origins, ",");
		AllowOriginResolver allowOriginResolver = new AllowOriginResolverImpl();
		allowOriginResolver.setCors(cors);
		CorsConfig corsConfig = new CorsConfig();
		Field allowOriginResolverField = corsConfig.getClass().getDeclaredField("allowOriginResolver");
		allowOriginResolverField.setAccessible(true);
		allowOriginResolverField.set(corsConfig, allowOriginResolver);
		String domain = corsConfig.getHostAndPort(referer);
		System.err.println("domain:" + domain + " referer:" + referer);
		return domain;
	}

}