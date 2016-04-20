package io.leopard.data4j.log.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log4jPropertiesParserImpl implements Log4jPropertiesParser {

	public String parse(String content) {
		if (content.indexOf("A1") != -1 || content.indexOf("E1") != -1) {
			// throw new IllegalArgumentException("log4.properties包含all.log相关配置，Leopard已经做了默认配置，请删除.");
			return content;
		}
		if (content.indexOf("all.log") != -1 || content.indexOf("error.log") != -1) {
			return content;
		}
		String content2 = this.appendLogger(content);
		content2 += this.getDefaultConfig();
		return content2;
	}

	protected String appendLogger(String content) {
		// log4j.rootLogger=
		String regex = "log4j.rootLogger=.+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();

		if (m.find()) {
			String rootLogger = m.group();
			// System.err.println("rootLogger:" + rootLogger);
			m.appendReplacement(sb, rootLogger + ", A1, W1, E1");
		}
		m.appendTail(sb);

		return sb.toString();
	}

	protected String getDefaultConfig() {

		String logDir = LogDirLeiImpl.getLogDir();

		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("log4j.appender.A1=io.leopard.monitor.alarm.DailyAutoRollingFileAppender\n");
		sb.append("log4j.appender.A1.Threshold=INFO\n");
		sb.append("log4j.appender.A1.layout=org.apache.log4j.PatternLayout\n");
		sb.append("log4j.appender.A1.layout.ConversionPattern=%d %p [%x,%t] - [%c] - <%m>%n\n");
		sb.append("log4j.appender.A1.DatePattern='.'yyyyMMdd\n");
		sb.append("log4j.appender.A1.File=" + logDir + "/all.log\n");
		sb.append("\n");
		sb.append("log4j.appender.W1=io.leopard.monitor.alarm.DailyAutoRollingFileAppender\n");
		sb.append("log4j.appender.W1.Threshold=WARN\n");
		sb.append("log4j.appender.W1.layout=org.apache.log4j.PatternLayout\n");
		sb.append("log4j.appender.W1.layout.ConversionPattern=%d %p [%x,%t] - [%c] - <%m>%n\n");
		sb.append("log4j.appender.W1.DatePattern='.'yyyyMMdd\n");
		sb.append("log4j.appender.W1.File=" + logDir + "/warn.log\n");
		sb.append("\n");
		sb.append("log4j.appender.E1=io.leopard.monitor.alarm.DailyAutoRollingFileAppender\n");
		sb.append("log4j.appender.E1.Threshold=ERROR\n");
		sb.append("log4j.appender.E1.layout=org.apache.log4j.PatternLayout\n");
		sb.append("log4j.appender.E1.layout.ConversionPattern=%d %p [%x,%t] - [%c] - <%m>%n\n");
		sb.append("log4j.appender.E1.DatePattern='.'yyyyMMdd\n");
		sb.append("log4j.appender.E1.File=" + logDir + "/error.log\n");
		sb.append("\n");
		return sb.toString();
	}

}
