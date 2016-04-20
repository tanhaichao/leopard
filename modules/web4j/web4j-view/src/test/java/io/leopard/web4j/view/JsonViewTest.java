package io.leopard.web4j.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class JsonViewTest {

	protected JsonView jsonView = Mockito.spy(new JsonView());

	@Test
	public void JsonView() {

	}

	@Test
	public void getStatus() {
		{
			JsonView jsonView = new JsonView(null);
			Assert.assertEquals(jsonView.getStatus(), "200");
		}
		{
			JsonView jsonView = new JsonView("-404", null);
			Assert.assertEquals(jsonView.getStatus(), "-404");
		}
	}

	@Test
	public void setStatus() {
		JsonView jsonView = new JsonView(null);
		jsonView.setStatus("-404");
		Assert.assertEquals(jsonView.getStatus(), "-404");
	}

	@Test
	public void getData() {
		JsonView jsonView = new JsonView("str");
		Assert.assertEquals("str", jsonView.getData());
	}

	@Test
	public void setData() {
		JsonView jsonView = new JsonView(null);
		jsonView.setData("str");
		Assert.assertEquals("str", jsonView.getData());
	}

	@Test
	public void getMessage() {
		JsonView jsonView = new JsonView(null);
		jsonView.setMessage("message");
		Assert.assertEquals("message", jsonView.getMessage());
	}

	@Test
	public void setMessage() {
		JsonView jsonView = new JsonView(null);
		jsonView.setMessage("message");
		Assert.assertEquals("message", jsonView.getMessage());
	}

	@Test
	public void getContentType() {
		JsonView jsonView = new JsonView(null);
		Assert.assertEquals("text/plain; charset=UTF-8", jsonView.getContentType());
	}

	@Test
	public void getBody() {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		JsonView jsonView = new JsonView("str");
		String body = jsonView.getBody(request, response);
		// {"status":200,"message":"","data":"str"}
		Assert.assertEquals("{\"status\":200,\"message\":\"\",\"data\":\"str\"}", body);
	}

	@Test
	public void getBody2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setParameter("callback", "callback");
		JsonView jsonView = new JsonView("str");
		String body = jsonView.getBody(request, response);
		// {"status":200,"message":"","data":"str"}
		Assert.assertEquals("callback({\"status\":200,\"message\":\"\",\"data\":\"str\"});", body);
	}

	@Test
	public void getBody3() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setParameter("var", "varname");
		JsonView jsonView = new JsonView("str");
		String body = jsonView.getBody(request, response);
		// {"status":200,"message":"","data":"str"}
		Assert.assertEquals("var varname = {\"status\":200,\"message\":\"\",\"data\":\"str\"};", body);
	}

	@Test
	public void getResult() {
		JsonView jsonView = new JsonView("str");
		Map<String, Object> result = jsonView.getResult();
		Assert.assertEquals("str", result.get("data"));
	}

	// @Test
	// public void toJson() {
	// JsonView jsonView = new JsonView("str");
	// // {"status":200,"message":"","data":"str"}
	// Assert.assertEquals("{\"status\":200,\"message\":\"\",\"data\":\"str\"}",
	// jsonView.toJson(false));
	// }
	//
	// @Test
	// public void toJsonp() {
	// JsonView jsonView = new JsonView("str");
	// // {"status":200,"message":"","data":"str"}
	// Assert.assertEquals("callback({\"status\":200,\"message\":\"\",\"data\":\"str\"});",
	// jsonView.toJsonp("callback", false));
	//
	// Assert.assertEquals("// 非法callback[callback-abc].",
	// jsonView.toJsonp("callback-abc", false));
	// }
	//
	// @Test
	// public void toScript() {
	// JsonView jsonView = new JsonView("str");
	// // {"status":200,"message":"","data":"str"}
	// Assert.assertEquals("var var1 = {\"status\":200,\"message\":\"\",\"data\":\"str\"};",
	// jsonView.toScript("var1", false));
	// Assert.assertEquals("// 非法var[var1-abc].", jsonView.toScript("var1-abc",
	// false));
	//
	// }

}