package io.leopard.myjetty.web.freemarker;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonView {

	private Object data;

	public JsonView(Object data) {
		this.data = data;
	}

	public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		String json = data.toString();

		Writer out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}
}
