package io.leopard.mvc.trynb.message;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.MysqlDataTruncation;

public class MessageParserImpl implements MessageParser {

	private static MessageParser INSTANCE = new MessageParserImpl();

	private List<MessageParser> list = new ArrayList<MessageParser>();

	public MessageParserImpl() {
		list.add(new TooLongMessageParser());
		list.add(new OutOfRangeValueMessageParser());
		list.add(new NotDefaultValueMessageParser());
	}

	@Override
	public String parse(String message) {
		for (MessageParser parser : list) {
			String result = parser.parse(message);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public static String parse(MysqlDataTruncation exception) {//TODO 这里改成SQLException
		String msg;
		try {
			msg = INSTANCE.parse(exception.getMessage());
			if (msg == null) {
				msg = exception.getMessage();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}
}
