package io.leopard.data.dfs.service.image;

public class ImageInfo {

	/**
	 * 图片文件名
	 */
	private String uri;

	/**
	 * 图片完整地址(包含域名)
	 */
	private String url;

	/**
	 * 图片宽度
	 */
	private int width;
	/**
	 * 图片高度
	 */
	private int height;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
