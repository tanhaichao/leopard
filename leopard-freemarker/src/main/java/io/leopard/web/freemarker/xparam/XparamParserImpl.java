package io.leopard.web.freemarker.xparam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class XparamParserImpl implements XparamParser {
	private static List<XparamParser> list = new ArrayList<XparamParser>();

	static {
		list.add(new XparamParserPageImpl());
		list.add(new XparamParserServerNameImpl());
	}

	@Override
	public Boolean parse(Object[] args, Class<?>[] types, String[] names, HttpServletRequest request) {
		for (XparamParser parser : list) {
			parser.parse(args, types, names, request);
		}
		return true;
	}

}
