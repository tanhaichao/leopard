package io.leopard.im.qcloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.tls.tls_sigature.TlsSigatureUtil;

import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

public class AbstractQcloudIm {

	@Value("${qcloud.im.appId}")
	protected long appId;

	@Value("${qcloud.im.identifier}")
	protected String identifier;

	/**
	 * 秘钥
	 */
	private String privateKey;

	/**
	 * 公钥
	 */
	private String publicKey;

	@PostConstruct
	public void init() throws IOException {
		this.privateKey = this.findFileContent("/qcloud/im/private_key");
		this.publicKey = this.findFileContent("/qcloud/im/public_key");
	}

	protected String findFileContent(String path) throws IOException {
		InputStream input = new ClassPathResource(path).getInputStream();
		String content = IOUtils.toString(input);
		input.close();
		return content;
	}

	protected String getUserSign(String identifier) {
		try {
			String urlSign = TlsSigatureUtil.getUrlSign(appId, identifier, privateKey);
			return urlSign;
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected String getUrl(String url, Map<String, Object> params) {
		if (params.isEmpty()) {
			return url;
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> param : params.entrySet()) {
			if (param.getValue() == null) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append('&');
			}
			try {
				sb.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue().toString(), "UTF-8"));
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return url + "?" + sb.toString();
	}

	protected String doRequest(String url, Map<String, Object> request) {
		String method = "POST";
		return this.doRequest(url, method, request);
	}

	protected String doRequest(String url, String method, Map<String, Object> request) {
		String requestBody = Json.toFormatJson(request);

		String userSig = this.getUserSign(identifier);
		Map<String, Object> params = new HashMap<>();
		params.put("contenttype", "json");
		params.put("apn", "1");
		params.put("usersig", userSig);
		// params.put("random", "99999999");
		params.put("sdkappid", appId);
		params.put("identifier", this.identifier);

		url = getUrl(url, params);

		System.err.println("requestBody:" + requestBody);
		String json = Httpnb.execute(url, new HttpHeaderRequestBodyImpl(method, requestBody));
		return json;
	}

}
