package io.leopard.topnb.thread;

import java.util.List;

public interface ThreadService {

	List<ThreadInfo> listAll();

	int getActiveCount();
}
