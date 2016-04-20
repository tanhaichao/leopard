package io.leopard.commons.utility;

/**
 * Resin相关方法类.<p>
 * 
 * @author 阿海
 * 
 */
import javax.naming.Context;
import javax.naming.InitialContext;

public final class Resin {

	private static final boolean IS_RESIN;

	
	static {
		IS_RESIN = check();
	}

	/**
	 * 判断是否为resin
	 * 
	 * @return boolean
	 */
	public static boolean isResin() {
		return IS_RESIN;
	}

	/**
	 * 判断是否初始化上下文实例
	 * 
	 * @return boolean
	 */
	public static boolean check() {
		try {
			Context env = (Context) new InitialContext().lookup("java:comp/env");
			if (env == null) {
				return false;
			}
		}
		catch (javax.naming.NamingException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 设置resin线程名称
	 * 
	 * @return boolean
	 */
	public static boolean setShortThreadName() {
		if (!Resin.isResin()) {
			String threadName = Thread.currentThread().getName();
			if (threadName.length() > 50) {
				Thread.currentThread().setName(threadName.substring(0, 50));
				return true;
			}
		}
		return false;
	}

}
