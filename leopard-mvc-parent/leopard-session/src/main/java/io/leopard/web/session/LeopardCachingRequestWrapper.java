package io.leopard.web.session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * getInputStream方法缓存
 * 
 * @author 谭海潮
 *
 */
public class LeopardCachingRequestWrapper extends SessionRequestWrapper {

	private ByteArrayOutputStream cachedContent;

	private ServletInputStream inputStream;

	public LeopardCachingRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (this.inputStream == null) {
			int contentLength = this.getContentLength();
			this.cachedContent = new ByteArrayOutputStream(contentLength >= 0 ? contentLength : 1024);
			this.inputStream = new ContentCachingInputStream(getRequest().getInputStream());
		}
		return this.inputStream;
	}

	private class ContentCachingInputStream extends ServletInputStream {

		private final ServletInputStream is;

		public ContentCachingInputStream(ServletInputStream is) {
			this.is = is;
		}

		@Override
		public int read() throws IOException {
			int ch = this.is.read();
			if (ch != -1) {
				cachedContent.write(ch);
			}
			return ch;
		}

		@Override
		public boolean isFinished() {// servlet 3.1
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {// servlet 3.1
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {// servlet 3.1
			// TODO Auto-generated method stub

		}
	}
}
