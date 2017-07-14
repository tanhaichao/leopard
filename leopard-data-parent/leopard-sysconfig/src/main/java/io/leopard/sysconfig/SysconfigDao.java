package io.leopard.sysconfig;

public interface SysconfigDao {

	Object resolve(String key, Class<?> type);
}
