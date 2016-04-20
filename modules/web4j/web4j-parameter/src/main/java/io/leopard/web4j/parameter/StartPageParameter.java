package io.leopard.web4j.parameter;

import io.leopard.burrow.util.NumberUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */
@Service
public class StartPageParameter implements PageParameter {

	@Autowired
	private PageIdPageParameter pageIdPageParameter;
	@Autowired
	private SizePageParameter sizePageParameter;

	@Override
	public String getValue(HttpServletRequest request) {
		String value = request.getParameter("start");

		int start;
		if (StringUtils.isEmpty(value)) {
			int pageId = Integer.parseInt(pageIdPageParameter.getValue(request));
			int size = Integer.parseInt(sizePageParameter.getValue(request));
			start = NumberUtil.getPageStart(pageId, size);
		}
		else {
			start = NumberUtils.toInt(request.getParameter("start"));
			if (start <= 0) {
				start = 0;
			}
		}
		return Integer.toString(start);
	}

	@Override
	public String getKey() {
		return "start";
	}

}
