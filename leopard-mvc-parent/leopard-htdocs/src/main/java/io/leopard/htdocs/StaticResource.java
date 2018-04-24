package io.leopard.htdocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * 静态资源文件
 * 
 * @author 谭海潮
 *
 */
public class StaticResource implements Resource {

	private String path;

	private File file;

	public StaticResource(File dir, String path) {
		if (StringUtils.isEmpty(path) || "/".equals(path)) {
			throw new IllegalArgumentException("非法文件路径[" + path + "]");
		}
		this.path = path;
		this.file = new File(dir, path);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public boolean isReadable() {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public boolean isOpen() {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public URL getURL() throws IOException {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public URI getURI() throws IOException {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public File getFile() throws IOException {
		return this.file;
	}

	@Override
	public long contentLength() throws IOException {
		return this.file.length();
	}

	@Override
	public long lastModified() throws IOException {
		return file.lastModified();
	}

	@Override
	public Resource createRelative(String relativePath) throws IOException {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public String getFilename() {
		return this.path;
	}

	@Override
	public String getDescription() {
		return this.path;
	}

}
