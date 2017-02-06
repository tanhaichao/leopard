package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class DaoProxyTest {

	public static class UserDao implements IStatus<String, String> {

		@Override
		public String get(String key) {

			return null;
		}

		@Override
		public boolean add(String bean) {

			return false;
		}

		@Override
		public boolean delete(String key, String opusername, Date lmodify) {

			return false;
		}

		@Override
		public Map<String, String> map(Set<String> keySet) {

			return null;
		}

		@Override
		public boolean undelete(String key, String username, Date lmodify) {

			return false;
		}

		@Override
		public String getIncludeDeleted(String key) {

			return null;
		}

		@Override
		public List<String> list(List<String> keyList) {
			return null;
		}

		@Override
		public boolean updateStatus(String key, int status, String username, Date lmodify, String reason) {

			return false;
		}

	}

	// private UserDao userDao = Mockito.mock(UserDao.class);
	//
	// @Test
	// public void delete() {
	// DaoProxy.delete(userDao, "key", "username");
	// Mockito.verify(userDao).delete(Mockito.eq("key"), Mockito.eq("username"),
	// Mockito.any(Date.class));
	// }

	@Test
	public void getTypeDeleted() {

	}

	@Test
	public void updateStatus() {

	}

	@Test
	public void deleteAndUpdateStatus() {

	}

	@Test
	public void undeleteAndUpdateStatus() {

	}

	@Test
	public void updateStatusOny() {

	}

	// @Test
	// public void listIncludeDeleted() {
	// DaoProxy.listIncludeDeleted(userDao, null);
	// LeopardMockito.verify(userDao, 0).list(Mockito.anyListOf(String.class));
	//
	// List<String> keyList = ListUtil.makeList("a,b");
	//
	// List<String> result = new ArrayList<String>();
	// result.add(null);
	// result.add(null);
	//
	// Mockito.doReturn(result).when(this.userDao).list(keyList);
	//
	// DaoProxy.listIncludeDeleted(userDao, keyList);
	// Mockito.verify(userDao).getIncludeDeleted("a");
	// Mockito.verify(userDao).getIncludeDeleted("b");
	// }
	//
	// @Test
	// public void map() {
	// DaoProxy.map(userDao, null);
	// LeopardMockito.verify(userDao, 0).get(Mockito.anyString());
	//
	// Set<String> keySet = SetUtil.makeSet("a,b");
	// DaoProxy.map(userDao, keySet);
	// Mockito.verify(userDao).get("a");
	// Mockito.verify(userDao).get("b");
	// }

	@Test
	public void DaoProxy() {
		new DaoProxy();
	}

}