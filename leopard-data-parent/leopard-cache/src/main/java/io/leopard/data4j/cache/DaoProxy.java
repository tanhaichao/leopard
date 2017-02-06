package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IDelete;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IStatus;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaoProxy {
	private static Log logger = LogFactory.getLog(DaoProxy.class);

	public interface DeleteSuccess<BEAN, KEYTYPE> {
		public boolean deleteSuccess(KEYTYPE key, String username, BEAN bean);
	}

	public interface UndeleteSuccess<BEAN, KEYTYPE> {
		public boolean undeleteSuccess(KEYTYPE key, String username, BEAN bean);
	}

	public interface UpdateStatusSuccess<BEAN, KEYTYPE> {
		public boolean updateStatusSuccess(KEYTYPE key, int type, boolean on, String username, String reason, BEAN bean);
	}

	public static <BEAN, KEYTYPE> boolean delete(IDelete<BEAN, KEYTYPE> dao, KEYTYPE key, String username) {
		return dao.delete(key, username, new Date());
	}

	public static <BEAN, KEYTYPE> List<BEAN> listIncludeDeleted(IStatus<BEAN, KEYTYPE> statusDao, List<KEYTYPE> keyList) {
		if (keyList == null || keyList.isEmpty()) {
			return null;
		}
		List<BEAN> list = statusDao.list(keyList);
		int index = 0;
		for (BEAN bean : list) {
			if (bean == null) {
				KEYTYPE key = keyList.get(index);
				bean = statusDao.getIncludeDeleted(key);
				if (bean == null) {
					logger.warn("记录[" + key + "]不存在.");
				}
				list.set(index, bean);
			}
			index++;
		}
		return list;
	}

	public static <BEAN, KEYTYPE> Map<KEYTYPE, BEAN> map(IGet<BEAN, KEYTYPE> dao, Set<KEYTYPE> keySet) {
		Map<KEYTYPE, BEAN> map = new LinkedHashMap<KEYTYPE, BEAN>();
		if (keySet != null) {
			for (KEYTYPE key : keySet) {
				BEAN bean = dao.get(key);
				map.put(key, bean);
			}
		}
		return map;
	}
}
