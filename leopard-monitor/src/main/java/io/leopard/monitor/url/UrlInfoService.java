package io.leopard.monitor.url;

import java.util.ArrayList;
import java.util.List;

public class UrlInfoService {

	private static List<UrlInfo> urlInfoList = new ArrayList<UrlInfo>();
	private static List<String> IGNORE_DIR = new ArrayList<String>();

	static {
		IGNORE_DIR.add("/monitor/");
		IGNORE_DIR.add("/udb/");
		IGNORE_DIR.add("/security/");
		IGNORE_DIR.add("/leopard/");
		
		IGNORE_DIR.add("/loging.do");
		IGNORE_DIR.add("/404.do");
		IGNORE_DIR.add("/pageNotFound.do");
	}

	protected static boolean isIgnore(String uri) {
		for (String dir : IGNORE_DIR) {
			if (uri.startsWith(dir)) {
				return true;
			}
		}
		return false;
	}

	public static void add(String uri) {
		// if (uri.equals("/webservice/flashVersion.do")) {
		// new Exception("err").printStackTrace();
		// }
		if (isIgnore(uri)) {
			return;
		}
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setUrl(uri);

		urlInfoList.add(urlInfo);
	}

	public static List<UrlInfo> list() {
		return urlInfoList;
	}
}
