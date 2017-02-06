package io.leopard.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

public class Base64MultipartFile implements MultipartFile {

	private byte[] data;

	private String extName;

	private String contentType;

	public Base64MultipartFile(String content) {
		if (StringUtils.isEmpty(content)) {
			throw new NullPointerException("图片内容不能为空.");
		}
		if (!content.startsWith("data:image")) {
			throw new IllegalArgumentException("非法图片格式.");
		}
		else if (content.startsWith("data:image/png;base64,")) {
			this.data = Base64Utils.decodeFromString(content.substring(22));
			this.extName = "png";
		}
		else if (content.startsWith("data:image/gif;base64,")) {
			this.data = Base64Utils.decodeFromString(content.substring(22));
			this.extName = "gif";
		}
		else if (content.startsWith("data:image/jpeg;base64,")) {
			this.data = Base64Utils.decodeFromString(content.substring(23));
			this.extName = "jpg";
		}
		else {
			throw new IllegalArgumentException("未知图片类型[" + StringUtils.substring(content, 0, 30) + "].");
		}
	}

	@Override
	public String getName() {
		return "base64." + this.extName;
	}

	@Override
	public String getOriginalFilename() {
		return "base64." + this.extName;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public long getSize() {
		return data.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return this.data;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(data);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		throw new UnsupportedOperationException("not impl.");
	}

}
