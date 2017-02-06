package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IDelete;

import java.util.Date;

import org.junit.Test;

public class CacheExecuteTest {

	@Test
	public void CacheExecute() {
		new CacheExecute();
	}

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

	public static class UserDaoMysqlImpl implements IDelete<User, String> {
		@Override
		public boolean add(User bean) {

			return false;
		}

		@Override
		public User get(String key) {

			return null;
		}

		@Override
		public boolean delete(String key, String opusername, Date lmodify) {

			return false;
		}
	}

	public static class CategoryDaoMysqlImpl implements io.leopard.data4j.cache.api.uid.IDelete<User, String> {
		@Override
		public boolean add(User bean) {

			return false;
		}

		@Override
		public User get(String key) {

			return null;
		}

		@Override
		public boolean delete(String key, long opusername, Date lmodify) {

			return false;
		}
	}

	@Test
	public void add() {
		UserDaoMysqlImpl userDaoMysqlImpl = new UserDaoMysqlImpl();
		UserDaoMysqlImpl userDaoMemcachedImpl = new UserDaoMysqlImpl();
		CacheExecute.add(userDaoMysqlImpl, userDaoMemcachedImpl, new User("hctan"));
	}

	@Test
	public void delete() {
		UserDaoMysqlImpl userDaoMysqlImpl = new UserDaoMysqlImpl();
		UserDaoMysqlImpl userDaoMemcachedImpl = new UserDaoMysqlImpl();
		UserDaoMysqlImpl userDaoMemoryImpl = new UserDaoMysqlImpl();
		CacheExecute.delete(userDaoMysqlImpl, userDaoMemcachedImpl, "hctan", "hctan", new Date());
		CacheExecute.delete(userDaoMysqlImpl, userDaoMemcachedImpl, userDaoMemoryImpl, "hctan", "hctan", new Date());
	}

	@Test
	public void delete2() {
		CategoryDaoMysqlImpl categoryDaoMysqlImpl = new CategoryDaoMysqlImpl();
		CategoryDaoMysqlImpl categoryDaoMemcachedImpl = new CategoryDaoMysqlImpl();
		CategoryDaoMysqlImpl categoryDaoMemoryImpl = new CategoryDaoMysqlImpl();
		CacheExecute.delete(categoryDaoMysqlImpl, categoryDaoMemcachedImpl, "hctan", 1, new Date());
		CacheExecute.delete(categoryDaoMysqlImpl, categoryDaoMemcachedImpl, categoryDaoMemoryImpl, "hctan", 1, new Date());
	}
}