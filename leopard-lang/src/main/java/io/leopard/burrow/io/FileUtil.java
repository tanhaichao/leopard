package io.leopard.burrow.io;

import io.leopard.core.exception.IORuntimeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	protected static final Log logger = LogFactory.getLog(FileUtil.class);

	/**
	 * 向文件末尾处添加内容
	 * 
	 * @param filename
	 *            文件名
	 * @param content
	 *            内容
	 * @throws IOException
	 */
	public static void appendFile(final String filename, final String content) throws IOException {
		FileOutputStream output = new FileOutputStream(filename, true);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		writer.write(content);
		writer.close();
		output.close();
	}

	// /**
	// * 将上传的MultipartFile转成String
	// *
	// * @param multfile
	// * 上传的文件
	// * @return 转换后的String
	// * @throws IOException
	// */
	// public static String toString(MultipartFile multfile) {
	// if (multfile == null || multfile.isEmpty()) {
	// return null;
	// }
	// byte[] bs;
	// try {
	// bs = multfile.getBytes();
	// }
	// catch (IOException e) {
	// throw new IORuntimeException(e.getMessage(), e);
	// }
	// String content;
	// try {
	// content = FileUtil.toString(bs);
	// }
	// catch (UnsupportedEncodingException e) {
	// throw new IORuntimeException(e.getMessage(), e);
	// }
	// return content;
	// }

	// /**
	// * 按行读取文件内容
	// *
	// * @param filename
	// * 文件名
	// * @return List，保存文件的每一行内容
	// * @throws IOException
	// */
	// public static List<String> readLines(String filename) throws IOException {
	// ClassPathResource resource = new ClassPathResource(filename);
	// String encoding = null;
	// InputStream in = resource.getInputStream();
	// try {
	// return IOUtils.readLines(in, encoding);
	// }
	// finally {
	// IOUtils.closeQuietly(in);
	// }
	// }

	public static List<String> readLines(File file) {
		try {
			return FileUtils.readLines(file);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 字节数组转成字符串
	 * 
	 * @param bs
	 *            字节数组
	 * @return 字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String toString(byte[] bs) throws UnsupportedEncodingException {
		if (bs == null) {
			return null;
		}
		if (bs.length == 0) {
			return "";
		}
		String content;
		if (bs[0] == -17 && bs[1] == -69 && bs[2] == -65) {
			content = new String(bs, "UTF-8");
		}
		else {
			content = new String(bs, "GBK");
		}
		int num = content.charAt(0);
		if (num == 65279) {// UTF-8 BOM,EF BB BF
			return content.substring(1);
		}
		return content;
	}

	// /**
	// * 通过ClassPathResource加载资源文件
	// *
	// * @param filename
	// * 文件名
	// * @return 文件内容
	// * @throws IOException
	// */
	// public static String readByClassPath(String filename) throws IOException {
	// ClassPathResource resource = new ClassPathResource(filename);
	// InputStream input = resource.getInputStream();
	// try {
	// return IOUtils.toString(input);
	// }
	// finally {
	// IOUtils.closeQuietly(input);
	// }
	// }

	public static void writeStringToFile(String filename, String data) {
		try {
			FileUtils.writeStringToFile(new File(filename), data);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	public static String readFileToString(File file) {
		try {
			return FileUtils.readFileToString(file);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filename
	 *            文件名
	 * @return String，文件内容
	 * @throws IOException
	 */
	public static String readFileToString(String filename) throws IOException {
		byte[] bs = FileUtils.readFileToByteArray(new File(filename));
		return toString(bs);
	}

	public static void forceDelete(File file) {
		try {
			FileUtils.forceDelete(file);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	// /**
	// * 从文件中获取日期Date
	// *
	// * @param filename
	// * 文件名
	// * @param defaultBeforeMinute
	// * 指定分钟数
	// * @return 若文件中不包含字符串日期，返回指定分钟数之前的时间值
	// */
	// public static Date getTime(String filename, int defaultBeforeMinute) {
	// // String filename = "/data/notice/lastclean.txt";
	// try {
	// String content = FileUtils.readFileToString(new File(filename));
	// content = StringUtils.trim(content);
	// if (StringUtils.isEmpty(content)) {
	// return DateUtil.addTime(-defaultBeforeMinute);
	// }
	// else {
	// return DateUtil.toDate(content);
	// }
	// }
	// catch (IOException e) {
	// logger.error(e.getMessage(), e);
	// return DateUtil.addTime(-defaultBeforeMinute);
	// }
	// }
}
