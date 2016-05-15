package io.leopard.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.data.schema.RegisterComponentUtil;

/**
 * Redis数据源标签
 * 
 * @author 阿海
 * 
 */
public class RedisDsnBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String hash = element.getAttribute("hash");
		if ("none".equals(hash) || StringUtils.isEmpty(hash)) {
			return createDefaultRedisImpl(element, parserContext);
		}
		else if ("uid".equals(hash)) {
			return this.createRedisHashImpl(element, parserContext, "long");
		}
		else if ("passport".equals(hash)) {
			return this.createRedisHashImpl(element, parserContext, "string");
		}
		else if ("default".equals(hash)) {
			return this.createRedisHashImpl(element, parserContext, "default");
		}
		else {
			throw new IllegalArgumentException("redis数据源，使用了未知hash类型[" + hash + "].");
		}
	}

	protected List<String> parseNameListByExpression(String name) {
		String regex = "([a-zA-Z0-9]+)\\(([0-9]+)\\-([0-9]+)\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		if (!m.find()) {
			throw new IllegalArgumentException("数据源名称表达式不合法[" + name + "].");
		}
		String prefix = m.group(1);
		int start = Integer.parseInt(m.group(2));
		int end = Integer.parseInt(m.group(3));
		List<String> list = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			String str = prefix + i;
			list.add(str);
		}
		return list;
	}

	protected List<String> parseNameList(String name) {
		AssertUtil.assertNotEmpty(name, "参数name不能为空.");
		if (name.indexOf("(") > -1) {
			return this.parseNameListByExpression(name);
		}

		String[] names = StringUtils.split(name, ",");
		// System.err.println("name:" + name + " names:" + names[1]);
		List<String> list = new ArrayList<String>();
		for (String str : names) {
			list.add(str.trim());
		}
		return list;
	}

	protected BeanDefinition createRedisHashImpl(Element element, ParserContext parserContext, String type) {
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");

		String log = element.getAttribute("log");
		String maxActive = element.getAttribute("maxActive");
		String initialPoolSize = element.getAttribute("initialPoolSize");
		String enableBackup = element.getAttribute("enableBackup");
		String backupTime = element.getAttribute("backupTime");
		String timeout = element.getAttribute("timeout");
		// String password = element.getAttribute("password");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getRedisHashImpl());

		List<String> nameList = this.parseNameList(name);

		String[] serverList = new String[nameList.size()];
		// System.out.println("names:" + names.length + " names:" +
		// StringUtils.join(names, ","));
		for (int i = 0; i < serverList.length; i++) {
			serverList[i] = this.getServer(nameList.get(i));
		}

		// System.err.println("name:" + name + " nameList:" + nameList +
		// " serverList:" + serverList[0]);
		builder.addPropertyValue("serverList", serverList);
		builder.addPropertyValue("hashType", type);
		if (StringUtils.isNotEmpty(log)) {
			builder.addPropertyValue("log", Boolean.valueOf(log));
		}
		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(initialPoolSize)) {
			builder.addPropertyValue("initialPoolSize", Integer.valueOf(initialPoolSize));
		}
		// if (StringUtils.isNotEmpty(password)) {
		// builder.addPropertyValue("password", password);
		// }
		if (StringUtils.isNotEmpty(enableBackup)) {
			builder.addPropertyValue("enableBackup", Boolean.valueOf(enableBackup));
		}

		builder.addPropertyValue("backupTime", backupTime);
		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}

	protected BeanDefinition createDefaultRedisImpl(Element element, ParserContext parserContext) {
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");

		String maxActive = element.getAttribute("maxActive");
		String initialPoolSize = element.getAttribute("initialPoolSize");
		String enableBackup = element.getAttribute("enableBackup");
		String backupTime = element.getAttribute("backupTime");
		String timeout = element.getAttribute("timeout");
		// String password = element.getAttribute("password");
		// String createConnectionFactory = element.getAttribute("createConnectionFactory");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getRedisImpl());

		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(initialPoolSize)) {
			builder.addPropertyValue("initialPoolSize", Integer.valueOf(initialPoolSize));
		}

		if (StringUtils.isNotEmpty(enableBackup)) {
			builder.addPropertyValue("enableBackup", Boolean.valueOf(enableBackup));
		}
		builder.addPropertyValue("backupTime", backupTime);
		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}
		// if (StringUtils.isNotEmpty(password)) {
		// builder.addPropertyValue("password", password);
		// }
		String server = this.getServer(name);
		// System.out.println("redis-dsn server:" + server);

		builder.addPropertyValue("server", server);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}

	protected String getServer(String name) {
		return "${" + name + ".redis}";
	}
}