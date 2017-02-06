package com.zhongcao.app.image;

import java.util.List;

public interface ImageService {

	boolean add(String parentId, long uid, String uri, int displayOrder);

	Image get(String imageId);

	boolean delete(String imageId, long opuid);

	List<Image> list(String parentId);

	boolean clean(String parentId);
}
