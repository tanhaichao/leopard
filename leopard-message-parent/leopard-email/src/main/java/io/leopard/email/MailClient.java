package io.leopard.email;

import org.apache.commons.mail.EmailException;

/**
 * 邮件发送器
 * 
 * @author 谭海潮
 *
 */
public interface MailClient {

	boolean sendText(String to, String subject, String content) throws EmailException;

	boolean sendHtml(String to, String subject, String content) throws EmailException;
}
