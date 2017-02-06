package io.leopard.web.view;

public class JsonpUtil {

	private static String CALLBACK_REGEX = "^[a-zA-Z0-9_\\.]+$";

	protected static boolean isValidCallback(String callback) {
		return callback.matches(CALLBACK_REGEX);
	}

	// private static List<String> ERROR_DIR = new ArrayList<String>();
	// static {
	// ERROR_DIR.add("/admin/");// TODO ahai 管理目录是用户自定义的，写死会有问题，这是临时方案
	// ERROR_DIR.add("/webservice/");
	// }

	// /**
	// * 是否禁止使用jsonp或script的目录?
	// *
	// * @param request
	// * @return
	// */
	// public static boolean isDenyFolder(HttpServletRequest request) {
	// String uri = RequestUtil.getRequestContextUri(request);
	//
	// for (String dir : ERROR_DIR) {
	// if (uri.startsWith(dir)) {
	// return true;
	// }
	// }
	// return false;
	// }

	public static void checkCallback(String callback) {
		if (callback == null || callback.length() == 0) {
			throw new IllegalArgumentException("参数callback不能为空.");
		}
		if (!isValidCallback(callback)) {
			throw new IllegalArgumentException("非法callback.");
		}
	}

	public static void checkVar(String var) {
		if (var == null || var.length() == 0) {
			throw new IllegalArgumentException("参数var不能为空.");
		}
		if (!isValidCallback(var)) {
			throw new IllegalArgumentException("非法var参数值.");
		}
	}

	// protected static String filter(String content) {
	// content = content.replace("<", "");
	// content = content.replace(">", "");
	// return content;
	// }
}
