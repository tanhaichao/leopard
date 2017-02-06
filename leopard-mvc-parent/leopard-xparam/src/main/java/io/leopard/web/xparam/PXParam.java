package io.leopard.web.xparam;

import org.springframework.stereotype.Service;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */@Service
public class PXParam extends PageIdXParam {

	@Override
	public String getKey() {
		return "p";
	}

}
