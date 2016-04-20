package io.leopard.data4j.env;

import org.apache.commons.lang.SystemUtils;

/**
 * mac开发环境
 * 
 * @author 阿海
 * 
 */
public class EnvLeiMacImpl extends EnvLeiWindowsImpl implements EnvLei {

	@Override
	public boolean isEnabled() {
		return SystemUtils.IS_OS_MAC;
	}

}
