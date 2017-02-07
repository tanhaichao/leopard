package io.leopard.topnb.methodtime;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 
 * @author 阿海
 * 
 */
public class ProfilerUtil {

	protected static Date SYSTEM_START_TIME = new Date();// 系统启动时间

	public static Date getSystemStartTime() {
		return SYSTEM_START_TIME;
	}

	// public static String getSystemStartHumanTime() {
	// long time = SYSTEM_START_TIME.getTime();
	// int seconds = (int) ((System.currentTimeMillis() - time) / 1000);
	// return getSystemStartHumanTime(seconds);
	// }
	//
	// protected static String getSystemStartHumanTime(int seconds) {
	// return null;
	// }

	// public static String getModuleName(String methodName) {
	// String categoryName = getCategoryName(methodName);
	// if ("DaoCacheImpl".equals(categoryName)) {
	// return PerformanceModuleEnum.DAOCACHEIMPL.getDesc();
	// }
	// else if (categoryName.startsWith("Dao")) {
	// return PerformanceModuleEnum.DAO.getDesc();
	// }
	// else if ("未知分类".equals(categoryName)) {
	// return PerformanceModuleEnum.UNKNOWN.getDesc();
	// }
	// return categoryName;
	// }

	protected static boolean isLeopardBean(String simpleMethodName) {
		if (simpleMethodName.startsWith("UserinfoServiceImpl.")) {
			return true;
		}
		else if (simpleMethodName.startsWith("LoginServiceImpl.")) {
			return true;
		}
		else if (simpleMethodName.startsWith("AdminLoginServiceImpl.")) {
			return true;
		}
		else if (simpleMethodName.startsWith("AdminLoginDaoMysqlImpl.")) {
			return true;
		}
		else if (simpleMethodName.startsWith("UserinfoFilter.")) {
			return true;
		}
		return false;
	}

	public static int getCategoryOrderByMethodName(String methodName) {
		String categoryName = ProfilerUtil.getCategoryName(methodName);
		// System.out.println("categoryName:" + categoryName);
		return PerformanceCategory.toEnumByDesc(categoryName).getKey();
	}

	public static String getCategoryName(String longMethodName) {
		String simpleMethodName = parseSimpleMethodName(longMethodName);
		if (isLeopardBean(simpleMethodName)) {
			return PerformanceCategory.LEOPARD.getDesc();
		}
		else if (simpleMethodName.indexOf("OutsideDao") != -1) {
			return PerformanceCategory.OUTSIDE.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoMysqlImpl.") != -1) {
			return PerformanceCategory.DAOMYSQLIMPL.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoMemoryImpl.") != -1) {
			return PerformanceCategory.DAOMEMORYIMPL.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoCacheImpl.") != -1) {
			return PerformanceCategory.DAOCACHEIMPL.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoRedisImpl.") != -1) {
			return PerformanceCategory.DAOREDISIMPL.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoMemcachedImpl.") != -1) {
			return PerformanceCategory.DAOMEMCACHEDIMPL.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoHttpImpl.") != -1) {
			return PerformanceCategory.OUTSIDE.getDesc();
		}
		else if (simpleMethodName.indexOf("DaoOutsideImpl.") != -1) {
			return PerformanceCategory.OUTSIDE.getDesc();
		}

		else if (simpleMethodName.indexOf("ServiceImpl.") != -1) {
			return PerformanceCategory.SERVICE.getDesc();
		}
		else if (simpleMethodName.indexOf("HandlerImpl.") != -1) {
			return PerformanceCategory.HANDLER.getDesc();
		}
		else if (simpleMethodName.indexOf("Controller.") != -1) {
			return PerformanceCategory.Controller.getDesc();
		}
		else if (simpleMethodName.indexOf("ControllerImpl.") != -1) {
			return PerformanceCategory.Controller.getDesc();
		}
		else if (simpleMethodName.startsWith("RedisImpl.")) {
			return PerformanceCategory.DATASOURCE.getDesc();
		}
		else if (simpleMethodName.startsWith("Base64.")) {
			return PerformanceCategory.ENCODER.getDesc();
		}
		else if (simpleMethodName.startsWith("Base16.")) {
			return PerformanceCategory.ENCODER.getDesc();
		}
		else if (simpleMethodName.startsWith("Json.")) {
			return PerformanceCategory.ENCODER.getDesc();
		}
		else if (simpleMethodName.startsWith("SessionService.")) {
			return PerformanceCategory.LEOPARD.getDesc();
		}
		else if (simpleMethodName.startsWith("EncryptUtil.")) {
			return PerformanceCategory.ENCODER.getDesc();
		}
		return PerformanceCategory.UNKNOWN.getDesc();
	}

	public static double avgTime(long totalCount, long totalMilliSeconds) {
		double avg = totalMilliSeconds * 1.0 / totalCount;
		return formatAvgTime(avg);
	}

	public static double formatAvgTime(double avg) {
		// System.out.println("avg:" + avg + " totalMilliSeconds:" +
		// totalMilliSeconds + " totalCount:" + totalCount);
		String avgTime;
		if (avg < 0.01) {
			avgTime = new DecimalFormat(",000").format(avg);
		}
		else if (avg < 0.1) {
			avgTime = new DecimalFormat(",00").format(avg);
		}
		else {
			avgTime = new DecimalFormat(".").format(avg);
		}
		// String avgTime = new DecimalFormat(",###").format(avg); NumberUtil.format(avg, n);
		return Double.parseDouble(avgTime);
	}

	public static String parseClassName(String longMethodName) {
		// com.zhongcao.app.web.controller.InventoryController$$EnhancerBySpringCGLIB$$487fed1c
		// int index = longMethodName.lastIndexOf(".");
		int index = longMethodName.lastIndexOf("$$EnhancerBySpringCGLIB");
		if (index == -1) {
			// System.err.println("parseClassName:" + longMethodName);
			index = longMethodName.lastIndexOf(".");
		}
		return longMethodName.substring(0, index);
	}

	public static String parseSimpleMethodName(String longMethodName) {
		String className = parseClassName(longMethodName);
		int index = className.lastIndexOf(".");
		String simpleMethodName = longMethodName.substring(index + 1);
		return simpleMethodName;
	}

	public static String parseSimpleClassName(String longMethodName) {
		String className = parseClassName(longMethodName);
		int index = className.lastIndexOf("$");
		if (index == -1) {
			index = className.lastIndexOf(".");
		}
		String simpleClassName = className.substring(index + 1);
		return simpleClassName;
	}

	public static String getInterfaceName(String longMethodName) {
		System.out.println("longMethodName:" + longMethodName);
		String className = parseClassName(longMethodName);
		String simpleClassName = parseSimpleClassName(longMethodName);
		// System.out.println("simpleClassName:" + simpleClassName);
		// if (className.startsWith("$Proxy")) {
		// return "$Proxy";
		// }
		if (className.indexOf("$Proxy") != -1) {
			return "$Proxy";
		}

		// com.zhongcao.app.web.controller.InventoryController$$EnhancerBySpringCGLIB$$487fed1c
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			System.err.println("longMethodName:" + longMethodName);
			throw new RuntimeException(e.getMessage(), e);
		}
		Class<?>[] classes = clazz.getInterfaces();

		for (Class<?> cls : classes) {
			// System.out.println("cls:" + cls.getName());
			String simpleName = cls.getSimpleName();
			// System.out.println("simpleClassName:" + simpleClassName +
			// " simpleName:" + simpleName);
			if (simpleClassName.startsWith(simpleName)) {
				return cls.getName();
			}
		}
		return className;
	}

}
