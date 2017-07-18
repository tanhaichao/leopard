package io.leopard.sysconfig.dynamicenum;

import java.util.List;
import java.util.Map;

import io.leopard.lang.inum.daynamic.EnumConstant;

public interface DynamicEnumDao {

	List<EnumConstant> resolve(String enumId, Class<?> type);

	void loadData();
}
