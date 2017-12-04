package io.leopard.util;

import java.io.File;

import com.github.junrar.extract.ExtractArchive;

import net.lingala.zip4j.core.ZipFile;

/**
 * 压缩包处理类
 * 
 * @author 谭海潮
 *
 */
public class ZipUtil {

	/**
	 * 解压zip格式压缩包
	 */
	public static void unzip(String sourceZip, String destDir) throws Exception {
		ZipFile zipFile = new ZipFile(sourceZip);
		zipFile.extractAll(destDir);
	}

	/**
	 * 解压rar格式压缩包。
	 */
	public static void unrar(String sourceRar, String destDir) {
		final File rar = new File(sourceRar);
		final File destinationFolder = new File(destDir);
		ExtractArchive extractArchive = new ExtractArchive();
		extractArchive.extractArchive(rar, destinationFolder);
	}

}
