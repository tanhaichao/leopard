package io.leopard.web4j.nobug.xss;

public class XssCheckerEncodeImpl implements XssChecker {

	// 一、HTML转义
	// 1、10进制
	// <script>alert('ok');</script>
	// &#60;&#115;&#99;&#114;&#105;&#112;&#116;&#62;&#97;&#108;&#101;&#114;&#116;&#40;&#39;&#111;&#107;&#39;&#41;&#59;&#60;&#47;&#115;&#99;&#114;&#105;&#112;&#116;&#62;
	//
	// 2、16进制
	// &#x003c;&#x0073;&#x0063;&#x0072;&#x0069;&#x0070;&#x0074;&#x003e;&#x0061;&#x006c;&#x0065;&#x0072;&#x0074;&#x0028;&#x0027;&#x006f;&#x006b;&#x0027;&#x0029;&#x003b;&#x003c;&#x002f;&#x0073;&#x0063;&#x0072;&#x0069;&#x0070;&#x0074;&#x003e;
	//
	// 二、JS转义
	// 1、unicode
	// \u003c\u0073\u0063\u0072\u0069\u0070\u0074\u003e\u0061\u006c\u0065\u0072\u0074\u0028\u0027\u006f\u006b\u0027\u0029\u003b\u003c\u002f\u0073\u0063\u0072\u0069\u0070\u0074\u003e
	//
	// 2、base16
	// \x3c\x73\x63\x72\x69\x70\x74\x3e\x61\x6c\x65\x72\x74\x28\x27\x6f\x6b\x27\x29\x3b\x3c\x2f\x73\x63\x72\x69\x70\x74\x3e
	//
	// 三、URI编码
	// %3Cscript%3Ealert('ok')%3B%3C%2Fscript%3E

	@Override
	public boolean check(String value) {
		for (String encode : encodeList) {
			if (value.indexOf(encode) != -1) {
				return true;
			}
		}
		return false;
	}

	private static String[] encodeList = { "&#", "\\u" };//, "%"

}
