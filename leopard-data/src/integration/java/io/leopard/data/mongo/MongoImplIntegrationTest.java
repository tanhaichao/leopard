package io.leopard.data.mongo;

import io.leopard.burrow.lang.Json;
import io.leopard.data4j.mongo.Mongo;
import io.leopard.data4j.mongo.MongoImpl;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mongodb.BasicDBObject;

public class MongoImplIntegrationTest {

	private Mongo mongo = newInstance();

	public Mongo newInstance() {
		MongoImpl mongo = new MongoImpl("112.126.75.27:27017", "test", "users");
		mongo.init();
		return mongo;
	}

	public static class User {
		private long uid;
		private String username;
		private String nickname;
		private Date posttime;

		public long getUid() {
			return uid;
		}

		public void setUid(long uid) {
			this.uid = uid;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public Date getPosttime() {
			return posttime;
		}

		public void setPosttime(Date posttime) {
			this.posttime = posttime;
		}

	}

	@Test
	public void find() {
		User user = new User();
		user.setUid(1);
		user.setUsername("hctan");
		user.setNickname("ahai");
		user.setPosttime(new Date());
		this.mongo.drop();
		this.mongo.insert(user);
		user.setUid(2);
		this.mongo.insert(user);

		List<User> userList = mongo.find(new BasicDBObject(10), User.class, 0, 10);
		Json.printList(userList, "userList");
	}

	@Test
	public void MongoImpl() {

	}

}