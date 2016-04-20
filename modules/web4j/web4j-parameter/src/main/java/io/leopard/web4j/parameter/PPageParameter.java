package io.leopard.web4j.parameter;

import org.springframework.stereotype.Service;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */@Service
public class PPageParameter extends PageIdPageParameter {

	@Override
	public String getKey() {
		return "p";
	}

}
