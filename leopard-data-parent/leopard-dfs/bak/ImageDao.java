package com.zhongcao.app.image;

import java.util.Date;
import java.util.List;

import io.leopard.data4j.cache.api.uid.IDelete;

public interface ImageDao extends IDelete<Image, String> {
	@Override
	public boolean add(Image image);

	@Override
	public Image get(String imageId);

	@Override
	public boolean delete(String imageId, long opuid, Date lmodify);

	List<Image> list(String parentId);

	boolean clean(String parentId);
}
