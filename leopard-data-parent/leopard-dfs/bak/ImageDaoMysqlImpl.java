package com.zhongcao.app.image;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;

@Repository
public class ImageDaoMysqlImpl implements ImageDao {

	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean add(Image image) {
		InsertBuilder builder = new InsertBuilder("image");
		builder.setString("imageId", image.getImageId());
		builder.setString("parentId", image.getParentId());
		builder.setString("uri", image.getUri());
		builder.setInt("displayOrder", image.getDisplayOrder());
		builder.setLong("uid", image.getUid());
		builder.setDate("posttime", image.getPosttime());
		return jdbc.insertForBoolean(builder);

	}

	@Override
	public Image get(String imageId) {
		String sql = "select * from image where imageId=?;";
		return jdbc.query(sql, Image.class, imageId);
	}

	@Override
	public boolean delete(String imageId, long opuid, Date lmodify) {
		String sql = "delete from image where imageId=?";
		return jdbc.updateForBoolean(sql, imageId);
	}

	@Override
	public List<Image> list(String parentId) {
		String sql = "select * from image where parentId=? order by displayOrder asc;";
		return jdbc.queryForList(sql, Image.class, parentId);
	}

	@Override
	public boolean clean(String parentId) {
		String sql = "delete from image where parentId=?;";
		return jdbc.updateForBoolean(sql, parentId);
	}

}
