package io.leopard.topnb.thread;

import java.util.List;

public interface ThreadDao {

	Thread[] listAllThreads();

	List<ThreadInfo> listAll();

	ThreadGroup getThreadGroup();

}
