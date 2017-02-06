package io.leopard.timer;

/**
 * 一次性.
 * 
 * @author 阿海
 * 
 */
public class OncePeriod implements Period {

	@Override
	public boolean sleep() {
		return false;// 不继续执行
	}

}
