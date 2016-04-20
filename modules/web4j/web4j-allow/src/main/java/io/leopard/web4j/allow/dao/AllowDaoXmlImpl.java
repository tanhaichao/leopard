package io.leopard.web4j.allow.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AllowDaoXmlImpl implements AllowDao {

	protected Map<String, String> cache = new ConcurrentHashMap<String, String>();

	protected InputStream read() throws IOException {
		System.out.println("read:");
		Resource resource = new ClassPathResource("/allow.xml");
		return resource.getInputStream();
	}

	@Override
	public void load() {
		try {
			InputStream input = this.read();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(input);
			NodeList nodeList = document.getElementsByTagName("allow");
			int size = nodeList.getLength();
			for (int i = 0; i < size; i++) {
				Element element = (Element) nodeList.item(i);
				String uri = element.getAttribute("uri");
				String ip = element.getAttribute("ip");

				System.out.println("uri:" + uri + " ip:" + ip);
			}
			input.close();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Boolean exist(Allow allow) {
		if (cache.isEmpty()) {

		}
		String key = allow.toString();
		return cache.containsKey(key);
	}

}
