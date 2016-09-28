package io.leopard.web.mvc.condition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 泛域名配置
 * 
 * @author 谭海潮
 *
 */
public class ExtensiveDomain {

	protected final List<String> nonList = new ArrayList<String>();
	protected final List<String> nonIgnoreList = new ArrayList<String>();
	protected final List<String> regexList = new ArrayList<String>();

	private final Set<String> IGNORE_DOMAIN_SET = new HashSet<String>();

	/**
	 * 忽略的域名
	 * 
	 * @param domain
	 */
	public void addIgnoreDomain(String domain) {
		IGNORE_DOMAIN_SET.add(domain);
	}

	/**
	 * 非域名
	 * 
	 * @param domain
	 */
	public void addNonDomain(String domain, String ignoreDomain) {
		nonList.add(domain);
		nonIgnoreList.add(ignoreDomain);
	}

	/**
	 * 泛域名
	 * 
	 * @param domain
	 */
	public void addExtensiveDomain(String domainPattern) {
		String regex = domainPattern.replace("*.", "^[A-Za-z0-9_\\-]+\\.");
		regexList.add(regex + "$");
	}

	public boolean match(String serverName) {
		if (!nonList.isEmpty()) {
			int index = 0;
			for (String domain : nonList) {
				String ignoreDomai = this.nonIgnoreList.get(index);
				if (serverName.endsWith(ignoreDomai)) {
					continue;
				}
				if (!serverName.endsWith(domain)) {
					return true;
				}
				index++;
			}
		}
		if (IGNORE_DOMAIN_SET.contains(serverName)) {
			return false;
		}
		for (String regex : regexList) {
			boolean matches = serverName.matches(regex);
			if (matches) {
				return true;
			}
		}
		return false;
	}
}
