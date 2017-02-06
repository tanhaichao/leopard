package io.leopard.data.dfs.service.image;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageDfsService {

	String save(long uid, String folder, byte[] data, String sizeList) throws IOException;

	String save(long uid, String folder, MultipartFile file, String sizeList) throws IOException;

	String save(long uid, String folder, MultipartFile file, String sizeList, int width, int height) throws IOException;

	List<String> save(long uid, String folder, List<MultipartFile> pictureList, String sizeList) throws IOException;

}
