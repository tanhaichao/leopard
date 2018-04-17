package io.leopard.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class DownloadView extends ModelAndView {

	private File file;

	public DownloadView(final String filename, final String name) throws FileNotFoundException {
		this(new File(filename), name);
	}

	public DownloadView(final File file, final String name) throws FileNotFoundException {
		this(new FileInputStream(file), name);
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public DownloadView(final InputStream input, final String name) {
		AbstractUrlBasedView view = new AbstractUrlBasedView() {
			@Override
			protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				String name2 = URLEncoder.encode(name, "UTF-8");
				response.setContentType("application/force-download");
				response.addHeader("Content-Disposition", "attachment;filename=" + name2);
				byte[] bytes = new byte[1024];
				ServletOutputStream out = response.getOutputStream();
				int readLength = 0;
				while ((readLength = input.read(bytes)) != -1) {
					out.write(bytes, 0, readLength);
				}
				// TODO ahai 释放资源
				input.close();
				out.flush();
				out.close();
			}
		};
		super.setView(view);
	}
}
