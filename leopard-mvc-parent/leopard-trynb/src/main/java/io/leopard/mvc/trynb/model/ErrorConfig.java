package io.leopard.mvc.trynb.model;

import java.util.List;

public class ErrorConfig {

	private String url;
	private String page;

	private List<ExceptionConfig> exceptionConfigList;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<ExceptionConfig> getExceptionConfigList() {
		return exceptionConfigList;
	}

	public void setExceptionConfigList(List<ExceptionConfig> exceptionConfigList) {
		this.exceptionConfigList = exceptionConfigList;
	}

	@Override
	public String toString() {
		return "ErrorConfig [url=" + url + ", page=" + page + "]";
	}

}
