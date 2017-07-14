package io.leopard.sysconfig;

import java.util.Map;

public interface SysconfigDao {

	Object resolve(String key, Class<?> type);

	Map<String, String> loadData();
}
