package io.leopard.web.view;

/**
 * 成功文本(正常返回200，错误时自动返回异常消息并打印堆栈信息到log).
 * 
 * 用途(拨测接口).
 * 
 * @author ahai
 * 
 */
public class OkTextView extends TextView {

	public OkTextView(final String message) {
		super(message);
	}

}
