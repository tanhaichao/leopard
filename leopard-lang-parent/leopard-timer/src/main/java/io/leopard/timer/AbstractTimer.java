package io.leopard.timer;

/**
 * 定时器.
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractTimer implements Timer {
	// protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	@Override
	public int getThreadCount() {
		return 1;
	}

}
