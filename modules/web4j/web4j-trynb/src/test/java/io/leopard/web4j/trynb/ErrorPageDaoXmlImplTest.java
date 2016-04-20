package io.leopard.web4j.trynb;

import io.leopard.burrow.lang.Json;
import io.leopard.web4j.trynb.model.ErrorConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ErrorPageDaoXmlImplTest {

	private ErrorPageDaoXmlImpl errorPageDaoXmlImpl = new ErrorPageDaoXmlImpl();

	@Test
	public void parse() throws IOException, ParserConfigurationException, SAXException {
		InputStream input = this.getClass().getResourceAsStream("trynb.xml");
		List<ErrorConfig> list = errorPageDaoXmlImpl.parse(input);
		Json.printList(list, "list");
	}

}