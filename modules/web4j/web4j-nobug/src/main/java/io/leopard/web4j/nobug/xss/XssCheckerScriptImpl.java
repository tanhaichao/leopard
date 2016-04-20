package io.leopard.web4j.nobug.xss;

public class XssCheckerScriptImpl implements XssChecker {

	private static XssChecker instance = new XssCheckerScriptImpl();

	public static XssChecker getInstance() {
		return instance;
	}

	// "</script>",
	// "<script",
	// "src=",
	// "eval(",
	// "expression(",
	// "javascript:",
	// "vbscript:",
	// "onload=",
	// "&#", // &#0000106 , &#106; ...

	private static String[] STRS = { "<script", "</script>", "<iframe", "src=", "eval(", "expression(", "javascript:", "vbscript:", "seekSegmentTime=" };

	// return preg_match('/(?:javascript|jav\s+ascript|\&#\d+|\&#x)/i', $img);
	@Override
	public boolean check(String value) {
		// TODO ahai XSS判断够完整了吗?

		for (String str : STRS) {
			int index = value.indexOf(str);
			// System.err.println("index:" + index);
			if (index != -1) {
				return true;
			}
		}
		return false;
	}

}
