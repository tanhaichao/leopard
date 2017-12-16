package io.leopard.mvc.cors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class AllowOriginResolverImpl implements AllowOriginResolver {

	protected List<String> originRegexList;

	@Override
	public void setCors(String cors) {
		List<String> originList = new ArrayList<String>();
		if (!(StringUtils.isEmpty(cors) || "false".equals(cors))) {
			String[] blocks = cors.split(",");
			for (int i = 0; i < blocks.length; i++) {
				blocks[i] = blocks[i].trim();
				originList.add(blocks[i]);
			}
		}
		this.originRegexList = toRegexList(originList);
	}

	/**
	 * 转换成正则表达式
	 * 
	 * @param allowOriginList
	 * @return
	 */
	public List<String> toRegexList(List<String> allowOriginList) {
		List<String> regexList = new ArrayList<>();
		for (String origin : allowOriginList) {
			if ("*".equals(origin)) {
				regexList.add("^[a-zA-Z0-9_\\-\\.]+$");
			}
			else if (origin.startsWith("*.")) {
				regexList.add("^[a-zA-Z0-9_\\-\\.]+" + origin.substring(2) + "$");
			}
			else {
				regexList.add("^" + origin + "$");
			}
		}
		return regexList;
	}

	@Override
	public boolean match(String host) {
		for (String regex : originRegexList) {
			// System.err.println("host:" + host + " regex:" + regex + " match:" + host.matches(regex));
			if (host.matches(regex)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean isEnable() {
		return !originRegexList.isEmpty();
	}

}
