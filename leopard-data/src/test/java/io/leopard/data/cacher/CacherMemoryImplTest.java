package io.leopard.data.cacher;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CacherMemoryImplTest {

	public static class UserDao extends CacherMemoryImpl<String, String> {
		public UserDao(Expiry expiry) {
			super(expiry);
		}

		@Override
		public String load(String key) {
			return null;
		}
	}

	private final UserDao userDao = Mockito.spy(new UserDao(Expiry.MINUTE));

	@Test
	public void get() {
		Mockito.doReturn("value").when(this.userDao).load("key");
		Assert.assertEquals("value", userDao.get("key"));
	}

	@Test
	public void remove() {
		Mockito.doReturn("value").when(this.userDao).load("key");

		userDao.get("key");

		int slot = userDao.getSlot(System.currentTimeMillis(), Expiry.MINUTE.getSeconds());
		String cacheKey = userDao.getCacheKey("key", slot);

		Assert.assertTrue(userDao.remove(cacheKey));
		Assert.assertFalse(userDao.remove(cacheKey));
	}

	@Test
	public void getCacheKey() {
		Assert.assertEquals("1", userDao.getCacheKey(null, 1));
	}

	@Test
	public void getPrev() {
		Assert.assertNull(userDao.getPrev("key", 1));
	}

}