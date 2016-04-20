package io.leopard.web4j.command;

import io.leopard.burrow.timer.Timer;
import io.leopard.burrow.timer.TimerUtil;
import io.leopard.burrow.util.ListUtil;
import io.leopard.burrow.util.StringUtil;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

public class CommandRunner {

	protected Method getMainMethod(Class<?> clazz) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			CommandMain commandMain = method.getAnnotation(CommandMain.class);
			if (commandMain != null) {
				return method;
			}
		}
		throw new RuntimeException("没有找到有包含@CommandMain的方法.");
	}

	// protected String[] parseValues(String[] names, String[] args) throws
	// ParseException {
	// Options options = new Options();
	// for (String name : names) {
	// // System.out.println("name:" + name);
	// options.addOption(name, true, "");
	// }
	//
	// CommandLineParser parser = new PosixParser();
	// CommandLine cmd = parser.parse(options, args);
	// String[] values = new String[names.length];
	// for (int i = 0; i < names.length; i++) {
	// if (cmd.hasOption(names[i])) {
	// String value = cmd.getOptionValue(names[i]);
	// values[i] = value;
	// }
	// }
	//
	// return values;
	// }
	protected void runTimer(Object command) {
		List<Thread> threadList = TimerUtil.start((Timer) command);
		threadList = ListUtil.defaultList(threadList);
		for (Thread thread : threadList) {
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				// logger.error(e.getMessage(), e);
			}
		}
	}

	protected void run(Object command, String[] args) throws Exception {
		if (command instanceof Timer) {
			this.runTimer(command);
			return;
		}
		else {
			Class<?> clazz = command.getClass();
			Method method = getMainMethod(clazz);
			// String[] names = CtClassUtil.getParameterNames(method);
			// System.out.println("method:" + method);
			// System.out.println("values:" + StringUtils.join(values, ","));
			Object[] methodArgs = this.parseMethodArgs(method.getParameterTypes(), args);
			method.invoke(command, methodArgs);
		}
	}

	protected Object[] parseMethodArgs(Class<?>[] types, String[] values) {
		Object[] methodArgs = new Object[types.length];
		for (int i = 0; i < methodArgs.length; i++) {
			methodArgs[i] = this.parseMethodArg(types[i], values[i]);
		}
		return methodArgs;
	}

	protected Object parseMethodArg(Class<?> type, String value) {
		if (type.equals(String.class)) {
			return value;
		}
		else if (type.equals(int.class) || type.equals(Integer.class)) {
			return NumberUtils.toInt(value);
		}
		else {
			throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
		}
	}

	public void start(String[] args) {
		String beanName = StringUtil.firstCharToLowerCase(args[0]);
		if (!beanName.endsWith("Command")) {
			throw new IllegalArgumentException("Bean名称[" + beanName + "]不是以Command结尾的.");
		}
		args = removeFirst(args);

		this.start(beanName, args);
	}

	/**
	 * 移除数组的第一个元素</br>
	 * 
	 * @param args
	 *            数组
	 * @return 移除后的数组
	 */
	public static String[] removeFirst(String[] args) {
		String[] args2 = new String[args.length - 1];
		System.arraycopy(args, 1, args2, 0, args2.length);
		return args2;
	}

	public void start(String beanName, String[] args) {
		beanName = StringUtil.firstCharToLowerCase(beanName);
		Object command = null;
		try {
			command = ContextFactory.getBean(beanName);
			this.run(command, args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.exit(command);
	}

	/**
	 * 是否执行System.exit(0)，如果有线程在后台运行则不能执行System.exit();
	 * 
	 * @return
	 */
	protected boolean isDaemon(Object command) {
		if (command == null) {
			return false;
		}

		if (command instanceof Command) {
			Command command2 = (Command) command;
			return command2.isDaemon();
		}
		return false;
	}

	protected void exit(Object command) {
		if (isDaemon(command)) {
			ContextFactory.shutDown();
		}
		else {
			ContextFactory.exit();
		}
	}

	// // public static <T> void start(Class<T> clazz, Object... args) {
	// // T command = ContextFactory.getBean(clazz);
	// // Method method = getMainMethod(clazz);
	// // try {
	// // method.invoke(command, args);
	// // }
	// // catch (Exception e) {
	// // e.printStackTrace();
	// // }
	// // }
	//
	// public static class TestCommand {
	//
	// /**
	// * 入口.
	// *
	// * @param newsId
	// * 消息ID.
	// */
	// @CommandMain
	// public void start(int newsId, String username) {
	// System.out.println("newsId:" + newsId + " username:" + username);
	// }
	// }
	//
	public static void main(String[] args) {
		// args = new String[] { "--newsId", "1", "--username", "hctan" };
		new CommandRunner().start(args);
	}
}
