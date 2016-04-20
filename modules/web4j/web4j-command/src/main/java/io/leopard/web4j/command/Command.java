package io.leopard.web4j.command;

public interface Command {

	/**
	 * 是否后台运行.
	 * 
	 * @return
	 */
	boolean isDaemon();
}
