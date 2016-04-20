package io.leopard.data.cacher;

import io.leopard.json.Json;
import io.leopard.memcache.IMemcache;
import io.leopard.memcache.Memcache;
import io.leopard.memcache.MemcacheMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class CacheManagerTest {

	public static class User {
		protected String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static interface UserDao {
		User get(String username);
	}

	public static class UserDaoMemcachedImpl implements UserDao, IMemcache {
		private final Memcache memcache = new MemcacheMemoryImpl();

		@Override
		public User get(String username) {
			String json = memcache.get(username);
			return Json.toObject(json, User.class);
		}

		@Override
		public Memcache getMemcache() {
			return memcache;
		}
	}

	public static class UserCacheManager extends CacheManager {

		public UserCacheManager(int expireSeconds) {
			super(expireSeconds);
		}

		@Override
		public Object invoke(Object[] args) {
			String username = (String) args[0];
			User user = new User();
			user.setUsername(username);
			return user;
		}

	}

	public static class User2CacheManager extends CacheManager {

		public User2CacheManager(int expireSeconds) {
			super(expireSeconds);
		}

		@Override
		public Object invoke(Object[] args) {
			return null;
		}

	}

	@Test
	public void execute() {
		UserDao userDaoMemcachedImpl = new UserDaoMemcachedImpl();
		UserCacheManager manager = new UserCacheManager(10);
		User user = manager.execute(userDaoMemcachedImpl, "hctan").get("hctan");
		Assert.assertNotNull(user);
	}

	@Test
	public void execute2() {
		UserDao userDaoMemcachedImpl = new UserDaoMemcachedImpl();
		User2CacheManager manager = new User2CacheManager(10);
		User user = manager.execute(userDaoMemcachedImpl, "hctan").get("hctan");
		Assert.assertNull(user);
	}
}