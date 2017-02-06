package io.leopard.data.dfs.service.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import io.leopard.data.dfs.UrlMultipartFile;
import io.leopard.data.dfs.service.DfsService;

public class ImageDfsServiceSyncImpl implements ImageDfsService {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private DfsService dfsService;

	@Override
	public String save(long uid, String folder, MultipartFile file, String sizeList, int width, int height) throws IOException {
		String uri = save(uid, folder, file, sizeList);
		if (uri != null) {
			if (width > 0 && height > 0) {
				uri += "#" + width + "_" + height;
			}
		}
		return uri;
	}

	@Override
	public String save(long uid, String folder, MultipartFile file, String sizeList) throws IOException {
		// logger.info("save:" + file);
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("文件不能为空.");
		}
		String uri;
		if (file instanceof UrlMultipartFile) {
			uri = ((UrlMultipartFile) file).getName();
		}
		else {
			uri = this.save(uid, folder, file.getBytes(), sizeList);
		}
		return uri;
	}

	@Override
	public List<String> save(long uid, String folder, List<MultipartFile> pictureList, String sizeList) throws IOException {
		List<String> imageUrlList = new ArrayList<String>();
		if (pictureList != null) {
			for (MultipartFile file : pictureList) {
				// if (file.isEmpty()) {
				// continue;
				// }
				// String uri;
				// if (file instanceof UrlMultipartFile) {
				// uri = ((UrlMultipartFile) file).getName();
				// }
				// else {
				// uri = this.save(uid, folder, file.getBytes(), sizeList);
				// }
				String uri = this.save(uid, folder, file, sizeList);
				if (uri == null) {
					continue;
				}
				imageUrlList.add(uri);
			}
		}
		return imageUrlList;
	}

	@Override
	public String save(long uid, String folder, byte[] data, String sizeList) throws IOException {
		final String uri = folder + uuid() + ".jpg";
		this.saveByUri(uid, uri, data, sizeList);
		return uri;
	}

	protected String saveByUri(long uid, String uri, byte[] data, String sizeList) throws IOException {
		dfsService.write(uri, data, uid);
		if (sizeList != null && sizeList.length() > 0) {
			String[] list = sizeList.split(",");
			for (String size : list) {
				size = size.trim();
				// this.small(uid, uri, size, data); //TODO ahai 还没有默认生成缩列图.
			}
		}
		return uri;
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}

}
