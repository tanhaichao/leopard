package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IGet;

import org.junit.Test;

public class CacheUpdaterTest {
	public static class User {
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

	public static class UserDaoMysqlImpl implements IGet<User, String> {
		@Override
		public boolean add(User bean) {

			return false;
		}

		@Override
		public User get(String key) {

			return null;
		}
	}

	public static class UserDaoMemcachedImpl implements IGet<User, String> {
		@Override
		public boolean add(User bean) {

			return false;
		}

		@Override
		public User get(String key) {

			return null;
		}
	}

	public static class UserDaoMemoryImpl implements IGet<User, String> {
		@Override
		public boolean add(User bean) {

			return false;
		}

		@Override
		public User get(String key) {

			return null;
		}
	}

	@Test
	public void CacheUpdater() {
		new CacheUpdater();
	}

	@Test
	public void add() {
		UserDaoMysqlImpl userDaoMysqlImpl = new UserDaoMysqlImpl();
		UserDaoMemcachedImpl userDaoMemcachedImpl = new UserDaoMemcachedImpl();
		UserDaoMemoryImpl userDaoMemoryImpl = new UserDaoMemoryImpl();
		CacheUpdater.add(userDaoMysqlImpl, userDaoMemcachedImpl, new User("hctan"));
		CacheUpdater.add(userDaoMysqlImpl, userDaoMemcachedImpl, userDaoMemoryImpl, new User("hctan"));
	}

	@Test
	public void update() {

	}

}