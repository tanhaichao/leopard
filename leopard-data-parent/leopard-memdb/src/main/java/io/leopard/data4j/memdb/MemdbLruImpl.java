package io.leopard.data4j.memdb;

public class MemdbLruImpl extends MemdbImpl implements Memdb {

	public MemdbLruImpl(int maxEntries) {
		// TODO LRU未实现
		data = new SynchronizedLRUMap<String, String>(10, maxEntries);
	}

}
