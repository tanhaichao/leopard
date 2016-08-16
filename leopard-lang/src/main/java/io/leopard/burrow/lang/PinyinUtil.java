package io.leopard.burrow.lang;

import io.leopard.burrow.util.StringUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	private static final HanyuPinyinOutputFormat defaultFormat;

	static {
		defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	/**
	 * 获取首字母.
	 * 
	 * @param content
	 * @return
	 */
	public static Character getFirstLetter(String content) {
		char c = content.charAt(0);
		if (StringUtil.isLetter(c)) {
			return Character.toUpperCase(c);
		}
		String pinyin = PinyinUtil.getPinyin(c);// 首字母
		if (pinyin == null) {
			// throw new NullPointerException("[" + content + "]");
			return null;
		}
		char firstLetter = pinyin.charAt(0);
		// return firstLetter.toUpperCase();
		return Character.toUpperCase(firstLetter);
	}

	/**
	 * 获取汉字串拼音，英文字符不变
	 * 
	 * @param chinese 汉字串
	 * @return 汉语拼音
	 */
	public static String getPinyin(Character c) {
		String[] strs;
		try {
			strs = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
		}
		catch (BadHanyuPinyinOutputFormatCombination e) {
			throw new RuntimeException(e.getMessage());
		}
		if (strs == null) {
			return null;
		}
		return strs[0];
	}

	public static String getPinyin(String content, String split) {
		// TODO 转拼音没有做缓存.
		StringBuilder sb = new StringBuilder();
		for (char c : content.toCharArray()) {
			String pinyin = getPinyin(c);
			if (sb.length() > 0) {
				sb.append(split);
			}
			if (pinyin != null) {
				sb.append(pinyin);
			}
		}
		return sb.toString();
	}
}
