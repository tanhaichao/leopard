package io.leopard.topnb.web.freemarker;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import io.leopard.topnb.web.Menu;
import io.leopard.web.freemarker.TemplateVariable;
import io.leopard.web.freemarker.template.RequestHolder;

/**
 * 消息Tab.
 * 
 * @author 阿海
 *
 */

public class MenuTemplateDirective implements TemplateDirectiveModel, TemplateVariable {
	// <ul class="navigation">
	// <li class="active"><a href="index.do"><span class="isw-grid"></span><span class="text">方法耗时</span></a></li>
	// <!--
	// <li><a href="pool.do"><span class="isw-archive"></span><span class="text">数据源连接池</span></a></li>
	// <li><a href="thread.do"><span class="isw-text_document"></span><span class="text">线程数量</span></a></li>
	//
	// <li><a href="/system/log.do"><span class="isw-text_document"></span><span class="text">日志管理</span></a></li>
	// -->
	// </ul>

	private static ServiceLoader<Menu> loader = ServiceLoader.load(Menu.class);

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		HttpServletRequest request = RequestHolder.getRequest();

		String uri = request.getRequestURI();
		StringBuilder sb = new StringBuilder();

		Iterator<Menu> iterator = loader.iterator();
		// System.out.println("iterator:" + iterator + " url:" + request.getRequestURI());
		while (iterator.hasNext()) {
			Menu menu = iterator.next();
			boolean active = uri.equals(menu.getUrl());
			sb.append("<li");
			if (active) {
				sb.append(" class=\"active\"");
			}
			sb.append("><a href=\"");
			sb.append(menu.getUrl());
			sb.append("\"><span class=\"isw-grid\"></span><span class=\"text\">");
			sb.append(menu.getName());
			sb.append("</span></a></li>");
		}
		env.getOut().write(sb.toString());
	}

	@Override
	public String getKey() {
		return "menu";
	}
}
