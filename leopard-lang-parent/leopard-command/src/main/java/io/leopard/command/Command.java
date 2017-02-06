package io.leopard.command;

public interface Command {

	/**
	 * 是否后台运行.
	 * 
	 * @return
	 */
	boolean isDaemon();
}
