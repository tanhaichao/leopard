package io.leopard.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云OSS
 * 
 * @author 谭海潮
 *
 */
public interface OssClient {

	String add(InputStream input, String dir, String filename, long lenght) throws IOException;

	String add(InputStream input, String dir, String filename, long lenght, String contentType) throws IOException;

	String add(String dir, MultipartFile file) throws IOException;

}
