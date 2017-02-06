package io.leopard.web.session;

import java.util.Date;

import org.junit.Test;

public class JsonSerializerTest {

	private class User {
		private String username;
		private Date posttime;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Date getPosttime() {
			return posttime;
		}

		public void setPosttime(Date posttime) {
			this.posttime = posttime;
		}

	}

	@Test
	public void toJson() {
		User user = new User();
		user.setPosttime(new Date());
		String json = JsonSerializer.toJson(user);
		System.out.println("json:" + json);
	}

}