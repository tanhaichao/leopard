package io.leopard.data.dfs.service.image;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图片查看.
 * 
 * @author 阿海
 *
 */
@Controller
public class ImageController {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ThumbnailService thumbnailService;

	/**
	 * @uri /image.do
	 * 
	 */
	// @SkipFilter
	@RequestMapping
	public void image(HttpServletRequest request, HttpServletResponse response) {
		// logger.info("image");
		String filename = request.getParameter("f");
		// logger.info("image filename:" + filename);
		// AssertUtil.assertNotEmpty(filename, "文件名不能为空.");
		if (filename == null || filename.length() == 0) {
			throw new IllegalArgumentException("文件名不能为空.");
		}
		filename = filename.replaceFirst("/file/", "/");

		byte[] data;
		try {
			data = thumbnailService.read(filename);
		}
		catch (Exception e) {
			logger.error("filename:" + filename + " message:" + e.getMessage(), e);
			byte[] body = e.toString().getBytes();
			this.write(response, "text/plain", 404, body);
			return;
		}
		this.write(response, "image/jpeg", 200, data);
	}

	protected void write(HttpServletResponse response, String contentType, int status, byte[] data) {
		int contentLength = data.length;

		response.setStatus(status);
		response.setContentLength(contentLength);
		response.setContentType(contentType);
		response.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 3600 * 1);

		try {
			ServletOutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();

		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
