package io.leopard.data.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertyPlaceholderLeiImpl implements PropertyPlaceholderLei {

	@Override
	public Resource[] getResources(String env) {
		Resource dsnResource = new ClassPathResource("/" + env + "/app.properties");
		if (!dsnResource.exists()) {
			dsnResource = new ClassPathResource("/app.properties");
			if (!dsnResource.exists()) {
				System.err.println("/app.properties不存在");
			}
			else {
				dsnResource = decrypt(dsnResource);
			}
		}
		return new Resource[] { dsnResource };
	}

	/**
	 * 解密.
	 * 
	 * @param resource
	 * @return
	 */
	protected Resource decrypt(Resource resource) {
		String content;
		try {
			InputStream input = resource.getInputStream();
			content = IOUtils.toString(input);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		Pattern p = Pattern.compile("^[^#=\\s]+$", Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String body = m.group();
			// System.err.println("body:" + body);
			String replacement;
			try {
				replacement = this.decode(body);
			}
			catch (Exception e) {
				System.err.println("app.properties解密出错:" + e.getMessage());
				replacement = body;
			}
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		// System.out.println("content:" + sb.toString());
		return new ByteArrayResource(sb.toString().getBytes());
	}

	private PropertyDecoder propertyDecoder;

	protected String decode(String encode) {
		if (propertyDecoder == null) {
			propertyDecoder = new PropertyDecoderImpl();
		}
		// System.err.println("decode:" + encode);
		return propertyDecoder.decode(encode);
	}
}
