package io.leopard.biz.count;

import java.util.List;

public interface CountBiz {

	boolean add(String key, long count);

	long get(String key);

	List<Long> list();
}
