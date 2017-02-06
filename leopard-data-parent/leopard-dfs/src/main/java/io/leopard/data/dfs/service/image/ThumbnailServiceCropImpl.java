package io.leopard.data.dfs.service.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.data.dfs.service.DfsService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class ThumbnailServiceCropImpl implements ThumbnailService {

	@Autowired
	private DfsService dfsService;

	@Override
	public byte[] read(String filename) throws IOException {
		String filename2 = filename.replace("_crop.jpg", ".jpg");

		ImageFileInfo imageFileInfo = ThumbnailUtil.parse(filename2);
		if (imageFileInfo == null) {
			throw new FileNotFoundException("非法文件名[" + filename + "]");
		}
		String originalName = imageFileInfo.getOriginalName();
		byte[] data = dfsService.read(originalName);
		long uid = 0;
		return this.crop(uid, originalName, imageFileInfo.getSize(), data);
	}

	protected byte[] crop(long uid, String uri, String size, byte[] data) throws IOException {
		if (data.length <= 0) {
			throw new IllegalArgumentException("图片文件不能为空.");
		}
		String[] list = size.split("x");
		int width = Integer.parseInt(list[0]);
		int height = Integer.parseInt(list[1]);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		// Thumbnails.of(input).size(width, height).toOutputStream(output);

		Thumbnails.of(input).crop(Positions.CENTER).size(width, height).keepAspectRatio(true).toOutputStream(output);

		// http://rensanning.iteye.com/blog/1545708 Java生成缩略图之Thumbnailator

		// Thumbnails.of(input).asBufferedImage();
		// ImageIO.getImageReader(writer)
		String uri2 = uri.replaceFirst(".jpg", "_" + size + "_crop.jpg");
		byte[] data2 = output.toByteArray();
		dfsService.write(uri2, data2, uid);
		return data2;
	}

	public boolean isCropFilename(String filename) {
		return filename.endsWith("_crop.jpg");
	}

}
