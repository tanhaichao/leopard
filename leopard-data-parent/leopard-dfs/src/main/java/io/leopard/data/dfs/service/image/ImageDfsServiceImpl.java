package io.leopard.data.dfs.service.image;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.SystemUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 异步实现.
 * 
 * @author 阿海
 *
 */
@Service
public class ImageDfsServiceImpl extends ImageDfsServiceSyncImpl {

	/**
	 * 是否web环境
	 * 
	 * @return
	 */
	protected boolean isWeb() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return false;
		}
		HttpServletRequest request = attributes.getRequest();
		if (request == null) {
			return false;
		}
		return true;
	}

	@Override
	public String save(final long uid, final String folder, final byte[] data, final String sizeList) throws IOException {
		if (SystemUtils.IS_OS_WINDOWS) {
			if (!this.isWeb()) {
				return super.save(uid, folder, data, sizeList);
			}
		}
		final String uri = folder + uuid() + ".jpg";
		logger.info("async save:" + uri);
		new Thread() {
			@Override
			public void run() {
				try {
					saveByUri(uid, uri, data, sizeList);
					logger.info("save:" + uri);
				}
				catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			};
		}.start();
		// saveByUri(uid, uri, data, sizeList);
		return uri;
	}
}
