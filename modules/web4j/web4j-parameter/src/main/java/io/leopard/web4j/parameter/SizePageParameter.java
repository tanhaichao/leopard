package io.leopard.web4j.parameter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */
@Service
public class SizePageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		int size = NumberUtils.toInt(request.getParameter("size"));
		if (size <= 0) {
			size = 10;
		}
		return Integer.toString(size);
	}

	@Override
	public String getKey() {
		return "size";
	}

}
