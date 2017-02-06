package io.leopard.web.servlet;

//import io.leopard.burrow.util.ListUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UriListChecker {

	
	private Set<String> uris = new HashSet<String>();
	private List<String> folders = new ArrayList<String>();

	public UriListChecker() {

	}

	public UriListChecker(List<String> uriList) {
		this.uris = this.getUris(uriList);
		this.folders = this.getFolders(uriList);
	}

	protected Set<String> getUris(List<String> uriList) {
		// uriList = ListUtil.defaultList(uriList);
		Set<String> set = new HashSet<String>();
		if (uriList != null) {
			for (String uri : uriList) {
				if (!uri.endsWith("/")) {
					set.add(uri);
				}
			}
		}
		return set;
	}

	protected List<String> getFolders(List<String> uriList) {
		// uriList = ListUtil.defaultList(uriList);
		List<String> list = new ArrayList<String>();
		if (uriList != null) {
			for (String uri : uriList) {
				if (uri.endsWith("/")) {
					list.add(uri);
				}
			}
		}
		return list;
	}

	public boolean exists(String uri) {
		// uri不经过 过滤器
		if (uris.contains(uri)) {
			return true;
		}
		for (String folder : folders) {
			if (uri.startsWith(folder)) {
				return true;
			}
		}
		return false;
	}
}
