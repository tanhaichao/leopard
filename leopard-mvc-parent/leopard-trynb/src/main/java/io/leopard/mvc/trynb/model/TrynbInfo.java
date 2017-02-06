package io.leopard.mvc.trynb.model;

public class TrynbInfo {

	private String page;
	private String message;
	private String statusCode;

	/**
	 * 是否使用了trynb.xml配置
	 */
	private boolean trynbMessage;
	private boolean apiMessage;
	/**
	 * 是否经过翻译
	 */
	protected boolean translate;

	private Exception exception;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean isTrynbMessage() {
		return trynbMessage;
	}

	public void setTrynbMessage(boolean trynbMessage) {
		this.trynbMessage = trynbMessage;
	}

	public boolean isTranslate() {
		return translate;
	}

	public void setTranslate(boolean translate) {
		this.translate = translate;
	}

	public boolean isApiMessage() {
		return apiMessage;
	}

	public void setApiMessage(boolean apiMessage) {
		this.apiMessage = apiMessage;
	}

}
