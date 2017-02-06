package io.leopard.data.dfs;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

/**
 * 测试时使用.
 * 
 * @author 谭海潮
 *
 */
public class PictureList extends ArrayList<MultipartFile> {

	private static final long serialVersionUID = 1L;

	public PictureList(String... urls) {
		for (String url : urls) {
			this.add(url);
		}
	}

	public boolean add(String url) {
		UrlMultipartFile file = new UrlMultipartFile(url);
		return this.add(file);
	}

}
