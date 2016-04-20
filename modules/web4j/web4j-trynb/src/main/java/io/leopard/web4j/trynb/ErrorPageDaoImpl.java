package io.leopard.web4j.trynb;

import io.leopard.web4j.trynb.model.ErrorConfig;

import java.util.List;

public class ErrorPageDaoImpl implements ErrorPageDao {

	private List<ErrorConfig> list = null;

	private ErrorPageDao errorPageDao = new ErrorPageDaoXmlImpl();

	@Override
	public List<ErrorConfig> list() {
		if (list != null) {
			return list;
		}
		list = errorPageDao.list();
		return list;
	}
}
