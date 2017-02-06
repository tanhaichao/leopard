package io.leopard.data.dfs.service.image;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.data.dfs.service.DfsService;

@Service
public class ThumbnailServiceImpl implements ThumbnailService {

	@Autowired
	private ThumbnailService thumbnailServiceSizeImpl;
	@Autowired
	private ThumbnailService thumbnailServiceCropImpl;

	@Autowired
	private DfsService dfsService;

	@Override
	public byte[] read(String filename) throws IOException {
		try {
			return dfsService.read(filename);
		}
		catch (FileNotFoundException e) {
			if (((ThumbnailServiceCropImpl) thumbnailServiceCropImpl).isCropFilename(filename)) {
				return thumbnailServiceCropImpl.read(filename);
			}
			else if (((ThumbnailServiceSizeImpl) thumbnailServiceSizeImpl).isSizeFilename(filename)) {
				return thumbnailServiceSizeImpl.read(filename);
			}
			else {
				throw e;
			}
		}
	}

}
