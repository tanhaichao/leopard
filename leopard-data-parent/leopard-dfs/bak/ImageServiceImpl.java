package com.zhongcao.app.image;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.burrow.util.StringUtil;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;

	@Override
	public boolean add(String parentId, long uid, String uri, int displayOrder) {
		String imageId = StringUtil.uuid();

		Image image = new Image();
		image.setImageId(imageId);
		image.setUid(uid);
		image.setParentId(parentId);
		image.setUri(uri);
		image.setDisplayOrder(displayOrder);
		image.setPosttime(new Date());
		return imageDao.add(image);
	}

	@Override
	public Image get(String imageId) {
		return imageDao.get(imageId);
	}

	@Override
	public boolean delete(String imageId, long opuid) {
		return imageDao.delete(imageId, opuid, new Date());
	}

	@Override
	public List<Image> list(String parentId) {
		return imageDao.list(parentId);
	}

	@Override
	public boolean clean(String parentId) {
		return imageDao.clean(parentId);
	}

}
