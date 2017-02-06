package io.leopard.security.allow.dao;

public class AllowDaoImpl implements AllowDao {

	private AllowDao allowDao = new AllowDaoXmlImpl();

	@Override
	public Boolean exist(Allow allow) {
		return allowDao.exist(allow);
	}

	@Override
	public void load() {
		allowDao.load();
	}

}
