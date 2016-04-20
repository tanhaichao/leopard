package io.leopard.web4j.nobug.xss;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XssCheckerImpl implements XssChecker {

	protected static Log logger = LogFactory.getLog(XssCheckerImpl.class);

	private static XssChecker instance = new XssCheckerImpl();

	public static XssChecker getInstance() {
		return instance;
	}

	private List<XssChecker> list = new ArrayList<XssChecker>();

	public XssCheckerImpl() {
		// list.add(new XssCheckerEncodeImpl());
		list.add(new XssCheckerScriptImpl());
		list.add(new XssCheckerOnxxxImpl());
		// list.add(new XssCheckerUrlImpl());
		// list.add(new XssCheckerDefaultImpl());

		// list.add(new XssCheckerDateTimeImpl());
	}

	@Override
	public boolean check(String value) {
		// System.err.println("XssCheckerImpl value:" + value);
		value = StringUtils.lowerCase(value);
		for (XssChecker checker : list) {
			boolean check = checker.check(value);
			if (check) {
				logger.error("hasXss checker:" + checker + " value:" + value);
				return true;
			}
		}
		return false;
	}

}
