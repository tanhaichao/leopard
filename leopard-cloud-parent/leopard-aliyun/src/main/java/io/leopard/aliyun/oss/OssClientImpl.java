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

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

@Service
public class OssClientImpl extends AbstractOssClient {

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
	public String add(InputStream input, String dir, String filename, long lenght, String contentType) throws IOException {
		if (input == null) {
			throw new NullPointerException("input不能为空.");
		}
		
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

	@Override
	public boolean move(String uri, String destUri) {
		throw new RuntimeException("not impl.");
	}

}
