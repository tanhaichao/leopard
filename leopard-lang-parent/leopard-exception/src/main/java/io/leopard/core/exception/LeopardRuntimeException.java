package io.leopard.core.exception;

import java.lang.reflect.Field;

/**
 * 顶层RuntimeException
 * 
 * @author 阿海
 * 
 */
public class LeopardRuntimeException extends RuntimeException implements ApiException {

	protected static final long serialVersionUID = 1L;

	/**
	 * 在JSON接口时使用，比如转成英文
	 */
	private String apiMessage;

	public LeopardRuntimeException(String message) {
		super(message);
	}

	public LeopardRuntimeException(String message, String apiMessage) {
		super(message);
		this.apiMessage = apiMessage;
	}

	public LeopardRuntimeException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	public LeopardRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public void setMessage(String message) {
		try {
			Field field = Throwable.class.getDeclaredField("detailMessage");
			field.setAccessible(true);
			field.set(this, message);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String getApiMessage() {
		return apiMessage;
	}

	public void setApiMessage(String apiMessage) {
		this.apiMessage = apiMessage;
	}

}
