package io.leopard.jetty.test;

import io.leopard.httpnb.Httpnb;

import java.util.ArrayList;
import java.util.List;

public class HttpUnit {

	private List<Paramater> params = new ArrayList<Paramater>();

	public void setParamater(String name, Object value) {
		Paramater param = new Paramater();
		param.setName(name);
		param.setValue(value);
		this.params.add(param);
	}

	public String doGet(String url) {
		return Httpnb.doGet(url);
	}

	public static class Paramater {
		private String name;
		private Object value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}
}
