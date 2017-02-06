package io.leopard.web.view;

import io.leopard.json.Json;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 只支持Ajax.
 * 
 * @author 阿海
 * 
 */
public class AjaxView extends AbstractView {

	private String status;
	private String message;
	private Object data;

	public AjaxView() {

	}

	public AjaxView(Object data) {
		this(data, false);
	}

	public AjaxView(Object data, boolean checkXss) {
		this("200", data);
		if (checkXss) {
			this.checkXss(data);
			// super.setXssChecked(true);
		}
	}

	private void checkXss(Object data) {
		// XssAttributeCheckUtil.checkObject(data);//FIXME ahai 未做xss检查
	}

	public AjaxView(String status, Object data) {
		this.status = status;
		this.data = data;
		this.message = "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getContentType() {
		return "text/plain; charset=UTF-8";
	}

	/**
	 * 
	 * 1、默认返回json <br/>
	 * URL:http://message.game.yy.com/test/json.do<br/>
	 * 返回:{"status":200,"message":"","data":{"username":"hctan","nickname": "ahai"}}<br/>
	 * 2、自定义callback参数，返回jsonp<br/>
	 * URL:http://message.game.yy.com/test/json.do?callback=callback2 <br/>
	 * 返回:callback2({"status":200,"message":"","data":{"username":"hctan", "nickname":"ahai"}}); <br/>
	 * 3、非法callback参数 <br/>
	 * URL:http://message.game.yy.com/test/json.do?callback=callback.aa <br/>
	 * 返回:// 非法callback[callback.aa]. <br/>
	 * 
	 * 4、自定义var参数，返回script <br/>
	 * URL:http://message.game.yy.com/test/json.do?var=abc <br/>
	 * 返回:var abc = {"status":200,"message":"","data":{"username":"hctan","nickname" :"ahai"}}; <br/>
	 * 5、非法var参数 <br/>
	 * URL:http://message.game.yy.com/test/json.do?var=var.aa <br/>
	 * 返回:// 非法var[var.aa].
	 */
	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		boolean format = "true".equals(request.getParameter("format"));
		String dateFormat = request.getParameter("dateFormat");
		Object result = this.getResult();
		return new JsonOutput().output(result, format, dateFormat, request);
	}

	/**
	 * 返回结果.
	 * 
	 * @return
	 */
	protected Map<String, Object> getResult() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", this.getStatus());
		map.put("message", this.message);
		map.put("data", this.getData());
		return map;
	}

	public static class JsonOutput {
		public String output(Object data, boolean format, String dateFormat, HttpServletRequest request) {
			if (format) {
				return Json.toFormatJson(data);
			}
			else {
				return Json.toJson(data);
			}
		}
	}

}
