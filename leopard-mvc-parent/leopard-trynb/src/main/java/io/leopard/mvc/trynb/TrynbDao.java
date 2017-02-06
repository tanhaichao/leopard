package io.leopard.mvc.trynb;

import io.leopard.mvc.trynb.model.ErrorConfig;

import java.util.List;

public interface TrynbDao {

	List<ErrorConfig> list();

	ErrorConfig find(String url);
}
