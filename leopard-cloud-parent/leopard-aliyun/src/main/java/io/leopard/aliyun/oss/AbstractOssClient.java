package io.leopard.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractOssClient implements OssClient {

	@Override
	public String add(String dir, MultipartFile file) throws IOException {
		return this.add(dir, file, extnameSet);
	}

	@Override
	public String add(String dir, MultipartFile file, Set<String> extnameSet) throws IOException {
		if (file == null) {
			throw new NullPointerException("文件不能为空.");
		}
		checkExtname(file.getOriginalFilename(), extnameSet);
		return this.add(file.getInputStream(), dir, file.getOriginalFilename(), file.getSize());
	}

	@Override
	public String add(InputStream input, String dir, String filename, long lenght) throws IOException {
		return this.add(input, dir, filename, lenght, null);
	}

	/** 支持的图片后缀，支持大小写 */
	private static Set<String> extnameSet = new HashSet<String>();
	static {
		extnameSet.add("jpg");
		extnameSet.add("png");
		extnameSet.add("gif");
		extnameSet.add("jpeg");
	}

	/**
	 * 检查文件扩展名
	 * 
	 * @param fileName
	 * @param picPrefix
	 * @return
	 */
	@Override
	public void checkExtname(String filename, Set<String> extnameSet) {
		if (StringUtils.isEmpty(filename)) {
			throw new IllegalArgumentException("文件名称不能为空.");
		}
		if (extnameSet == null || extnameSet.isEmpty()) {
			return;
		}
		String extname = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		if (!extnameSet.contains(extname)) {
			throw new IllegalArgumentException("文件格式不合法[" + extname + "].");
		}
	}

	@Override
	public String toUuidFileName(String filename) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		return uuid + filename.substring(filename.lastIndexOf("."));
	}
}
