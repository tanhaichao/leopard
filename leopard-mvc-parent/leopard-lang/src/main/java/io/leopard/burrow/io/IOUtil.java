package io.leopard.burrow.io;

import io.leopard.core.exception.IORuntimeException;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class IOUtil {

	// public static String readByClassPath(String filename) {
	// try {
	// return FileUtil.readByClassPath(filename);
	// }
	// catch (IOException e) {
	// throw new IORuntimeException(e.getMessage(), e);
	// }
	// }

	public static String readFileToString(String filename) {
		return readFileToString(new File(filename));
	}

	public static String readFileToString(File file) {
		try {
			String content = FileUtils.readFileToString(file);
			return content;
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	public static boolean writeStringToFile(File file, String data) {
		try {
			FileUtils.writeStringToFile(file, data);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
		return true;
	}

	public static void forceMkdir(File directory) {
		try {
			FileUtils.forceMkdir(directory);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

}
