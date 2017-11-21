package io.leopard.mvc.cors;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CorsConfigTest {
	
	@Test
	public void get() throws MalformedURLException {
		String url = getHostAndPort("http://xxx.baidu.com", "zhhr.com", "*.zhhr.xiaoniu.io");
		System.out.println("url:" + url);
	}

	@Test
	public void getHostAndPort() throws MalformedURLException {
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

	private static String getHostAndPort(String referer, String... origins) throws MalformedURLException {
		List<String> allowOriginList = new ArrayList<String>();
		for (String origin : origins) {
			allowOriginList.add(origin);
		}
		allowOriginList = CorsConfig.toRegexList(allowOriginList);
		String domain = CorsConfig.getHostAndPort(referer, allowOriginList);
		System.err.println("domain:" + domain + " referer:" + referer + " allowOriginList:" + allowOriginList);
		return domain;
	}

}