package io.leopard.mvc.trynb;

import io.leopard.mvc.trynb.model.ErrorConfig;
import io.leopard.mvc.trynb.model.ExceptionConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TrynbDaoXmlImpl implements TrynbDao {

	@Override
	public List<ErrorConfig> list() {
		Resource resource = new ClassPathResource("/trynb.xml");
		if (!resource.exists()) {
			resource = new ClassPathResource("/trynb-default.xml");
		}
		try {
			InputStream input = resource.getInputStream();
			return this.parse(input);
		}
		catch (Exception e) {
			throw new RuntimeException("解析trynb.xml出错:" + e.getMessage(), e);
		}
	}

	protected List<ErrorConfig> parse(InputStream input) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(input);
		NodeList nodeList = document.getElementsByTagName("error");
		// System.out.println("nodeList:" + nodeList.getLength());
		List<ErrorConfig> list = new ArrayList<ErrorConfig>();
		int size = nodeList.getLength();
		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			ErrorConfig errorConfig = this.parseErrorConfig((Element) node);
			list.add(errorConfig);
		}
		input.close();
		return list;
	}

	protected ErrorConfig parseErrorConfig(Element element) {
		String url = element.getAttribute("url");
		String page = element.getAttribute("page");

		List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
		NodeList nodeList = element.getElementsByTagName("exception");
		int size = nodeList.getLength();
		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			ExceptionConfig exceptionConfig = this.parseExceptionConfig((Element) node);
			exceptionConfigList.add(exceptionConfig);
		}

		ErrorConfig errorConfig = new ErrorConfig();
		errorConfig.setUrl(url);
		errorConfig.setPage(page);
		errorConfig.setExceptionConfigList(exceptionConfigList);
		return errorConfig;
	}

	protected ExceptionConfig parseExceptionConfig(Element element) {
		String type = element.getAttribute("type");
		String statusCode = element.getAttribute("statusCode").trim();
		String message = element.getAttribute("message");
		String log = element.getAttribute("log");
		ExceptionConfig exceptionConfig = new ExceptionConfig();
		exceptionConfig.setType(type);
		exceptionConfig.setMessage(message);
		exceptionConfig.setStatusCode(statusCode);
		exceptionConfig.setLog(log);
		return exceptionConfig;
	}

	@Override
	public ErrorConfig find(String url) {
		throw new UnsupportedOperationException("Not Impl.");
	}
}
