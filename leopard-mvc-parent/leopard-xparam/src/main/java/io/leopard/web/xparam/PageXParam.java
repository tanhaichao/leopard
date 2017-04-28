package io.leopard.web.xparam;

import org.springframework.stereotype.Service;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */
@Service
@Deprecated
public class PageXParam extends PageIdXParam {

	@Override
	public String getKey() {
		return "page";// TODO page是分页的变量名称，不建议用来做页码参数名称
	}

}
