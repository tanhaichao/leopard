package io.leopard.mvc.trynb;

import io.leopard.mvc.trynb.model.ErrorConfig;

import java.util.List;

public class TrynbDaoImpl implements TrynbDao {

	private List<ErrorConfig> list = null;

	private TrynbDao trynbDao = new TrynbDaoXmlImpl();

	@Override
	public List<ErrorConfig> list() {
		if (list != null) {
			return list;
		}
		list = trynbDao.list();
		return list;
	}

	@Override
	public ErrorConfig find(String url) {
		for (ErrorConfig error : list()) {
			String prefix = error.getUrl();
			if (url.startsWith(prefix)) {
				return error;
			}
		}
		return null;
	}
}
