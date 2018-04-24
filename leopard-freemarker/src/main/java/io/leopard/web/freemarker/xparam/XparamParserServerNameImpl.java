package io.leopard.web.freemarker.xparam;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

public class XparamParserServerNameImpl implements XparamParser {

	@Override
	public Boolean parse(Object[] args, Class<?>[] types, String[] names, HttpServletRequest request) {
		int index = ArrayUtils.indexOf(names, "serverName");
		if (index == -1) {
			return false;
		}
		String serverName = request.getServerName();
		args[index] = serverName;
		return true;
	}

}
