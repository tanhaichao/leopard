package io.leopard.jetty.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;

import org.eclipse.jetty.util.resource.Resource;

@SuppressWarnings("deprecation")
public class StringResource extends Resource {

	private String content;

	public StringResource(String content) {
		this.content = content;
	}

	@Override
	public boolean isContainedIn(Resource r) throws MalformedURLException {
		throw new RuntimeException("not impl.");
	}

	@Override
	public void close() {

	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public long lastModified() {
		return 0;
	}

	@Override
	public long length() {
		return this.content.getBytes().length;
	}

	@Override
	public URL getURL() {
		throw new RuntimeException("not impl.");
	}

	@Override
	public File getFile() throws IOException {
		throw new RuntimeException("not impl.");
	}

	@Override
	public String getName() {
		throw new RuntimeException("not impl.");
	}

	// @SuppressWarnings("deprecation")
	@Override
	public InputStream getInputStream() throws IOException {
		return new StringBufferInputStream(content);
	}

	@Override
	public ReadableByteChannel getReadableByteChannel() throws IOException {
		// throw new FileNotFoundException("not impl.");
		return null;
	}

	@Override
	public boolean delete() throws SecurityException {
		throw new SecurityException("not impl.");
	}

	@Override
	public boolean renameTo(Resource dest) throws SecurityException {
		throw new SecurityException("not impl.");
	}

	@Override
	public String[] list() {
		// throw new SecurityException("not impl.");
		throw new RuntimeException("not impl.");
	}

	@Override
	public Resource addPath(String path) throws IOException, MalformedURLException {
		throw new RuntimeException("not impl.");
	}

}
