package io.leopard.commons.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrowserUtil {
	
	/**
	 * 获取IE版本号</br>
	 * @param userAgent 请求信息中的userAgent 
	 * @return IE版本号
	 */
	public static int getIeVersion(String userAgent){
		int version = 7;
		Pattern p = Pattern.compile("MSIE");
		Matcher matcher = p.matcher(userAgent);
		if(matcher.find()){
			int start = matcher.start() + 5;
			int end = matcher.end() + 4;
			String content = userAgent.substring(start, end);
			version = (int)Double.parseDouble(content); 
		}
		return version;
	}
	
}
