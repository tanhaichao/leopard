package io.leopard.security.allow.dao;

public interface AllowDao {

	void load();

	Boolean exist(Allow allow);
}
