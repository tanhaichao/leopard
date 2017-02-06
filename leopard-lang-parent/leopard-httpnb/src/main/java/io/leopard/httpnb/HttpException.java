package io.leopard.httpnb;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private long timeout;

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public HttpException(Throwable cause, HttpHeader header) {
		super(cause.getMessage(), cause);
		this.setTimeout(header.getTimeout());
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
}
