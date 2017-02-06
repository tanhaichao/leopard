package io.leopard.web.freemarker.xparam;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class XparamParserPageImpl implements XparamParser {

	
	@Override
	public Boolean parse(Object[] args, Class<?>[] types, String[] names, HttpServletRequest request) {
		int startIndex = ArrayUtils.indexOf(names, "start");
		if (startIndex == -1) {
			// System.out.println("startIndex:" + startIndex);
			return false;
		}
		int sizeIndex = ArrayUtils.indexOf(names, "size");
		if (sizeIndex == -1) {
			// System.out.println("sizeIndex:" + sizeIndex);
			return false;
		}

		int size = NumberUtils.toInt(request.getParameter("size"));
		if (size <= 0) {
			size = 10;
		}

		String startStr = request.getParameter("start");
		int start = 0;
		if (StringUtils.isEmpty(startStr)) {
			String pageIdStr = getPageIdStr(request);
			int pageId = NumberUtils.toInt(pageIdStr);
			if (pageId <= 0) {
				pageId = 1;
			}
			start = (pageId - 1) * size;
		}
		else {
			start = NumberUtils.toInt(startStr);
		}

		args[startIndex] = start;
		args[sizeIndex] = size;
		return true;
	}

	protected String getPageIdStr(HttpServletRequest request) {
		String pageIdStr = request.getParameter("pageId");
		if (pageIdStr == null) {
			pageIdStr = request.getParameter("page");
			if (pageIdStr == null) {
				pageIdStr = request.getParameter("p");
				if (pageIdStr == null) {
					pageIdStr = request.getParameter("pageid");
				}
			}
		}
		return pageIdStr;
	}

}
