package io.leopard.web4j.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用HTML跳转页面(传递referer).
 * 
 * @author 阿海
 * 
 */
public class LocationView extends AbstractView {

	private final String url;

	public LocationView(String url) {
		this.url = url;
		// XssUtil.checkUrl(url);//FIXME ahai 未做xss检查
		// super.setXssChecked(true);
	}

	@Override
	public String getContentType() {
		return "text/html; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {

		StringBuilder sb = new StringBuilder();

		// TODO ahai 这里的URL要做安全性判断?
		// sb.append("<meta http-equiv=\"refresh\" content=\"0;url=" + this.url + "\">");
		sb.append("<body>\n");
		sb.append("<script type='text/javascript'>\n");
		sb.append("var url = '" + this.url + "';\n");
		sb.append("var a = document.createElement('a');\n");
		sb.append("if(!a.click) { \n");
		sb.append("window.location = url;\n");
		sb.append("}else {\n");
		sb.append("a.setAttribute('href', url);\n");
		sb.append("a.style.display = 'none';\n");
		sb.append("document.body.appendChild(a);\n");
		sb.append("a.click();\n");
		sb.append("}\n");
		sb.append("</script>\n");
		sb.append("</body>\n");
		return sb.toString();
	}

}
