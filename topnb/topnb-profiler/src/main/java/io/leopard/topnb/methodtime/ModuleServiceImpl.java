package io.leopard.topnb.methodtime;


public class ModuleServiceImpl implements ModuleService {

	@Override
	public String getModuleName(String methodName) {
		String categoryName = getCategoryName(methodName);
		if ("DaoCacheImpl".equals(categoryName)) {
			return PerformanceModuleEnum.DAOCACHEIMPL.getDesc();
		}
		else if (categoryName.startsWith("Dao")) {
			return PerformanceModuleEnum.DAO.getDesc();
		}
		else if ("未知分类".equals(categoryName)) {
			return PerformanceModuleEnum.UNKNOWN.getDesc();
		}
		return categoryName;
	}

	public static String parseClassName(String longMethodName) {
		int index = longMethodName.lastIndexOf(".");
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
}
