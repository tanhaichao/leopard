package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */
@Service
public class StartXParam implements XParam {

	@Autowired
	private PageIdXParam pageIdXParam;
	@Autowired
	private SizeXParam sizeXParam;

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String value = request.getParameter("start");

		int start;
		if (StringUtils.isEmpty(value)) {
			int pageId = (Integer) pageIdXParam.getValue(request, parameter);
			int size = (Integer) sizeXParam.getValue(request, parameter);
			start = getPageStart(pageId, size);
		}
		else {
			start = XParamUtil.toInt(request.getParameter("start"));
			if (start <= 0) {
				start = 0;
			}
		}
		return start;
	}

	/**
	 * 计算默认起始记录
	 * 
	 * @param pageid 分页编号
	 * @param size 分页大小
	 * @return 当前分页的起始记录编号
	 */
	public static int getPageStart(int pageId, int size) {
		if (pageId < 1) {
			throw new IllegalArgumentException("pageid不能小于1.");
		}
		if (size < 1) {
			throw new IllegalArgumentException("size不能小于1.");
		}
		return (pageId - 1) * size;
	}

	@Override
	public String getKey() {
		return "start";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
