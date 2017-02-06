package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IRemoveListCache;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ListCacheManagerTest {

	@Test
	public void ListCacheManager() {
		new ListCacheManager();
	}

	protected static class User {
		private String username;

		public User() {

		}

		public User(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static class UserDaoMysqlImpl implements IGet<User, String>, IRemoveListCache<User> {
		private Map<String, User> data = new HashMap<String, User>();

		@Override
		public boolean add(User user) {
			data.put(user.getUsername(), user);
			return true;
		}

		@Override
		public User get(String username) {
			return data.get(username);
		}

		@Override
		public boolean removeListCache(User user) {
			data.remove(user.getUsername());
			return true;
		}

	}

	@Test
	public void transferModel() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		userDao.add(new User("hctan"));

		ListCacheManager.transferModel(userDao, "hctan");
		Assert.assertNotNull(ListCacheManager.getModel());
		ListCacheManager.removeListCache(userDao);

		Assert.assertNull(userDao.get("hctan"));
	}

}