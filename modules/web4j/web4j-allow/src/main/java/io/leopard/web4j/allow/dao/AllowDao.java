package io.leopard.web4j.allow.dao;

import java.io.IOException;

public interface AllowDao {

	void load();

	Boolean exist(Allow allow);
}
