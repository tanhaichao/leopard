package io.leopard.web.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.JstlView;

/**
 * 文件缓存视图.
 * 
 * 需求:<br/>
 * nginx先到memcached查询数据，如果有有数据直接返回给浏览器。如果没有数据则交给java处理(需要用到jsp视图)， java将返回给浏览器的内容写入一份到memcached，下次直接从memcached返回。<br/>
 * 
 * 旧实现:<br/>
 * 1、Browser -> Nginx -> Memcached。<br/>
 * 2、Nginx -> ResponseWrapperFilter -> Controller<br/>
 * 3、ResponseWrapperFilter:将Response输出流写入memcached.<br/>
 * 4、配置web.xml启用ResponseWrapperFilter<br/>
 * 
 * 新实现(Leopard):<br/>
 * 1、Browser -> Nginx -> Memcached。<br/>
 * 2、Nginx -> Controller -> FileCacheView.<br/>
 * 
 * @author 阿海
 * 
 */
public abstract class FileCacheView extends ModelAndView {

	public FileCacheView(String viewName) {
		super.setView(this.createView(viewName));
	}

	protected JstlView createView(String viewName) {
		return new JstlView("/WEB-INF/jsp/" + viewName + ".jsp") {
			@Override
			protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				ServletOutputStream output = response.getOutputStream();

				ServletContext servletContext = request.getServletContext();
				super.setServletContext(servletContext);

				CacheResponseWrapper cacheResponseWrapper = new CacheResponseWrapper(response);
				// System.err.println("renderMergedOutputModel");
				super.renderMergedOutputModel(model, request, cacheResponseWrapper);

				byte[] data = cacheResponseWrapper.getResponseData();
				String content = new String(data);
				response(content);

				output.write(data);
				output.flush();
			}
		};
	}

	public abstract void response(String content);

	protected static class CacheResponseWrapper extends HttpServletResponseWrapper {

		private ByteArrayOutputStream buffer = null;
		private ServletOutputStream out = null;
		private PrintWriter writer = null;

		public CacheResponseWrapper(HttpServletResponse response) throws IOException {
			super(response);
			buffer = new ByteArrayOutputStream();// 真正存储数据的流
			out = new WapperedOutputStream(buffer);
			writer = new PrintWriter(new OutputStreamWriter(buffer, "UTF-8"));
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return out;
		}

		@Override
		public PrintWriter getWriter() throws UnsupportedEncodingException {
			return writer;
		}

		@Override
		public void flushBuffer() throws IOException {
			// if (out != null) {
			out.flush();
			// }
			// if (writer != null) {
			writer.flush();
			// }
		}

		@Override
		public void reset() {
			buffer.reset();
		}

		public byte[] getResponseData() throws IOException {
			flushBuffer();// 将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
			return buffer.toByteArray();
		}

		// 内部类，对ServletOutputStream进行包装
		protected static class WapperedOutputStream extends ServletOutputStream {

			private ByteArrayOutputStream bos = null;

			public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
				bos = stream;
			}

			@Override
			public void write(int b) throws IOException {
				bos.write(b);
			}

			public boolean isReady() {
				//TODO ahai 更换servlet3.1后未测试
				return true;
			}

			public void setWriteListener(WriteListener arg0) {

			}
		}
	}

}
