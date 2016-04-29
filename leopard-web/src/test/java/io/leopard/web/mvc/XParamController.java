package io.leopard.web.mvc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.JettyServer;
import io.leopard.json.Json;
import io.leopard.web.view.TextView;

@Controller
public class XParamController {
	/**
	 * 新增视频
	 * 
	 * @param video
	 * @param sessUid
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public TextView add(User user) {
		String json = Json.toFormatJson(user);
		return new TextView(json);
	}

	@Test
	public void testAdd() throws Exception {
		JettyServer.start("src/test/webapp");
		{
			String str = Httpnb.doGet("http://localhost/passport/login.leo?uid=1");
			System.out.println("str:" + str);
		}
		String result = Httpnb.doGet("http://localhost/welcome.do");
		System.out.println("result:" + result);
		Assert.assertEquals("{\"status\":\"RuntimeException\",\"message\":\"ok\",\"data\":null}", result);
	}

	public static class User {
		private long uid;
		private String nickname;

		public long getUid() {
			return uid;
		}

		public void setUid(long uid) {
			this.uid = uid;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}
}
