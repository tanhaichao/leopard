package io.leopard.web4j.command;

public abstract class AbstractCommand {

	/**
	 * 初始化环境.
	 */
	protected static void initEnv() {
		// boolean isDevEnv = AppProperties.isDevEnv();
		// if (isDevEnv) {
		// DnsConfig.initHosts();
		// }
	}

	protected String[] args;

	/**
	 * 运行命令.
	 * 
	 * @param args
	 * @param clazz
	 */
	public static <E extends AbstractCommand> void start(String[] args, Class<E> clazz) {
		initEnv();
		E command = ContextFactory.getBean(clazz);
		command.start(args);
	}

	/**
	 * 执行命令.
	 * 
	 * @param args
	 */
	public void start(String[] args) {
		this.args = args;
		try {
			this.run(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.exit();
	}

	protected void exit() {
		if (isExit()) {
			ContextFactory.exit();
		}
		else {
			ContextFactory.shutDown();
		}
	}

	/**
	 * 是否执行System.exit(0)，如果有线程在后台运行则不能执行System.exit();
	 * 
	 * @return
	 */
	protected boolean isExit() {
		return true;
	}

	public abstract void run(String[] args);

}
