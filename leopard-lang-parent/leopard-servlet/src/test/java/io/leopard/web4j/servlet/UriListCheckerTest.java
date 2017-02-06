package io.leopard.web4j.servlet;

import io.leopard.web.servlet.UriListChecker;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class UriListCheckerTest  {

	@Test
	public void exists() {
		List<String> uriList = new ArrayList<String>();
		uriList.add("/index.do");
		uriList.add("/user/");

		UriListChecker uriListChecker = new UriListChecker(uriList);
		Assert.assertTrue(uriListChecker.exists("/index.do"));
		Assert.assertTrue(uriListChecker.exists("/user/get.do"));

		Assert.assertFalse(uriListChecker.exists("/user2/get"));
		Assert.assertFalse(uriListChecker.exists("/user2/get1.do"));
	}

}