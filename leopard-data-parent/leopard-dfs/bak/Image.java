package com.zhongcao.app.image;

import java.util.Date;

public class Image {

	private String imageId;
	private String parentId;
	private String uri;
	private Date posttime;
	private int displayOrder;
	private long uid;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

}
