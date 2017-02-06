package io.leopard.data.dfs.service.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.data.dfs.service.DfsService;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class ThumbnailServiceSizeImpl implements ThumbnailService {

	@Autowired
	private DfsService dfsService;

	@Override
	public byte[] read(String filename) throws IOException {
		ImageFileInfo imageFileInfo = ThumbnailUtil.parse(filename);
		if (imageFileInfo == null) {
			throw new FileNotFoundException("非法文件名[" + filename + "]");
		}
		String originalName = imageFileInfo.getOriginalName();
		byte[] data = dfsService.read(originalName);
		long uid = 0;
		return this.small(uid, originalName, imageFileInfo.getSize(), data);
	}

	protected byte[] small(long uid, String uri, String size, byte[] data) throws IOException {
		String[] list = size.split("x");
		int width = Integer.parseInt(list[0]);
		int height = Integer.parseInt(list[1]);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		Thumbnails.of(input).size(width, height).toOutputStream(output);

		// http://rensanning.iteye.com/blog/1545708 Java生成缩略图之Thumbnailator

		// Thumbnails.of(input).asBufferedImage();
		// ImageIO.getImageReader(writer)
		String uri2 = uri.replaceFirst(".jpg", "_" + size + ".jpg");
		byte[] data2 = output.toByteArray();
		if (data2.length <= 0) {
			throw new FileNotFoundException("怎么压缩后的图片会为空[" + uri2 + "]?");
		}
		dfsService.write(uri2, data2, uid);
		return data2;
	}

	public boolean isSizeFilename(String filename) {
		return ThumbnailUtil.isMatcher(filename);
	}
}
