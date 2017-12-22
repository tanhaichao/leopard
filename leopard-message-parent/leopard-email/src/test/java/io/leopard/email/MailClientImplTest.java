package io.leopard.email;

import org.apache.commons.mail.EmailException;
import org.junit.Test;

public class MailClientImplTest {

	private MailClientImpl mailClient = new MailClientImpl();

	@Test
	public void sendText() throws EmailException {
		mailClient.sendText("hctan@163.com", "test", "content");
	}

	@Test
	public void sendHtml() throws EmailException {
		mailClient.sendHtml("hctan@163.com", "test", "<font color=\"red\">content</font>");
	}

}