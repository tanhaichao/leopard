package io.leopard.data.version;

import io.leopard.json.Json;
import io.leopard.redis.Redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractVersionTest {

	public static class User {
		private String username;
		private String nickname;

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

	}

	public static class UserDaoVersionImpl extends AbstractVersion<User, String> {

		@Override
		protected void replcale(User user) {
			user.setNickname("ahai");
		}

	}

	private UserDaoVersionImpl userDao = newInstance();
	protected static Redis redis = Mockito.mock(Redis.class);

	protected static UserDaoVersionImpl newInstance() {
		UserDaoVersionImpl userDao = new UserDaoVersionImpl();
		userDao.redis = redis;
		userDao.redisKey = "key";
		userDao.beanType = User.class;
		return userDao;
	}

	@Test
	public void getBean() {
		String json;
		{
			User user = new User();
			user.setUsername("hctan");
			json = Json.toJson(user);
		}
		Mockito.doReturn(json).when(redis).hget("key", "field");
		User user = userDao.getBean("field");
		Json.print(user, "user");
		Assert.assertEquals("hctan", user.getUsername());
	}

	@Test
	public void isVersion() {
		UserDaoVersionImpl.setVersion(false);
		Assert.assertFalse(UserDaoVersionImpl.isVersion());
		UserDaoVersionImpl.setVersion(true);
		Assert.assertTrue(UserDaoVersionImpl.isVersion());
	}

	@Test
	public void removeBean() {
		Mockito.doReturn(1L).when(redis).hdel("key", 1);
		Assert.assertTrue(this.userDao.removeBean(1));
	}

	@Test
	public void update() {

		User user = new User();
		user.setUsername("hctan");
		String json = Json.toJson(user);
		Mockito.doReturn(1L).when(redis).hset("key", "field", json);
		Assert.assertTrue(this.userDao.update("field", user));
	}

	@Test
	public void filter() {
		userDao.filter(null);

		User user = new User();
		user.setUsername("hctan");

		UserDaoVersionImpl.setVersion(true);
		userDao.filter(user);

		Assert.assertEquals("ahai", user.getNickname());

	}

	@Test
	public void filterList() {
		this.userDao.filterList(null);

		User user = new User();
		user.setUsername("hctan");

		List<User> userList = new ArrayList<User>();
		userList.add(user);
		this.userDao.filterList(userList);

		Assert.assertEquals("ahai", user.getNickname());
	}

}