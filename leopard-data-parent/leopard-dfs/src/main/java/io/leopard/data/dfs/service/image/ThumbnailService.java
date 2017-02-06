package io.leopard.data.dfs.service.image;

import java.io.IOException;

/**
 * 缩略图.
 * 
 * @author 阿海
 *
 */
public interface ThumbnailService {
	byte[] read(String filename) throws IOException;

}
