package io.leopard.data.dfs.service.image;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThumbnailUtil {
	private static Pattern p = Pattern.compile("_([0-9]+x[0-9]+)\\.jpg$");

	public static ImageFileInfo parse(String filename) {
		// ImageFileParser
		Matcher m = p.matcher(filename);
		StringBuffer sb = new StringBuffer();
		if (!m.find()) {
			return null;
		}
		String size = m.group(1);
		m.appendReplacement(sb, ".jpg");
		m.appendTail(sb);

		String originalName = sb.toString();

		ImageFileInfo imageFileInfo = new ImageFileInfo();
		imageFileInfo.setOriginalName(originalName);
		imageFileInfo.setSize(size);
		return imageFileInfo;
	}

	public static boolean isMatcher(String filename) {
		Matcher m = p.matcher(filename);
		return m.find();
	}
}
