package io.leopard.data.dfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UrlMultipartFile implements MultipartFile {

	private String url;

	public UrlMultipartFile(String url) {
		this.url = url;
	}

	@Override
	public String getName() {
		return url;
	}

	@Override
	public String getOriginalFilename() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return StringUtils.isEmpty(this.getName());
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {

	}

}
