package io.leopard.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

@Service
public class OssClientImpl implements OssClient {

	// private static final String endpoint = "http://oss.aliyuncs.com";
	// private static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";

	@Value("${aliyun.oss.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.bucketName}")
	private String bucketName;
	@Value("${aliyun.oss.accessKeyId}")
	private String accessKeyId;
	@Value("${aliyun.oss.secretAccessKey}")
	private String secretAccessKey;

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	@PostConstruct
	public void init() {
		// System.out.println("bucketName:" + bucketName);
		// System.out.println("accessKeyId:" + accessKeyId);
		// System.out.println("secretAccessKey:" + secretAccessKey);
	}

	@Override
	public String add(String dir, MultipartFile file) throws IOException {
		if (file == null) {
			throw new NullPointerException("文件不能为空.");
		}
		return this.add(file.getInputStream(), dir, file.getOriginalFilename(), file.getSize());
	}

	@Override
	public String add(InputStream input, String dir, String filename, long lenght) throws IOException {

		return this.add(input, dir, filename, lenght, null);
	}

	@Override
	public String add(InputStream input, String dir, String filename, long lenght, String contentType) throws IOException {
		if (input == null) {
			throw new NullPointerException("input不能为空.");
		}
		checkExtname(filename);
		OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		// System.err.println("endpoint:" + endpoint);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(lenght);
		if (contentType != null) {
			meta.setContentType(contentType);
		}
		filename = toUuidFileName(filename);
		String uri;
		if (dir == null || dir.length() == 0) {
			uri = filename;
		}
		else {
			uri = dir + "/" + filename;
		}
		System.out.println("uri:" + uri + " lenght:" + lenght);
		PutObjectResult result = client.putObject(bucketName, uri, input, meta);
		System.out.println(result.getETag());
		return "/" + uri;
	}

	/** 支持的图片后缀，支持大小写 */
	private static Set<String> extnameSet = new HashSet<String>();
	static {
		extnameSet.add("jpg");
		extnameSet.add("png");
		extnameSet.add("gif");
		extnameSet.add("jpeg");
	}

	/**
	 * 检查文件扩展名
	 * 
	 * @param fileName
	 * @param picPrefix
	 * @return
	 */
	private static void checkExtname(String filename) {
		if (StringUtils.isEmpty(filename)) {
			throw new IllegalArgumentException("文件名称不能为空.");
		}
		String extname = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		if (!extnameSet.contains(extname)) {
			throw new IllegalArgumentException("文件格式不合法[" + extname + "].");
		}
	}

	public static String toUuidFileName(String filename) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		return uuid + filename.substring(filename.lastIndexOf("."));
	}

}
