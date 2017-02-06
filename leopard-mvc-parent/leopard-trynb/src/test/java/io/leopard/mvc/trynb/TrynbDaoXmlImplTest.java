package io.leopard.mvc.trynb;

import io.leopard.mvc.trynb.TrynbDaoXmlImpl;
import io.leopard.mvc.trynb.model.ErrorConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class TrynbDaoXmlImplTest {

	private TrynbDaoXmlImpl errorPageDaoXmlImpl = new TrynbDaoXmlImpl();

	@Test
	public void parse() throws IOException, ParserConfigurationException, SAXException {
		InputStream input = this.getClass().getResourceAsStream("trynb.xml");
		List<ErrorConfig> list = errorPageDaoXmlImpl.parse(input);
		// Json.printList(list, "list");
		for (ErrorConfig config : list) {
			System.out.println("config:" + config);
		}
	}

}