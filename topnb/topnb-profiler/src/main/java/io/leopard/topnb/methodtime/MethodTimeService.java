package io.leopard.topnb.methodtime;

import java.util.List;

public interface MethodTimeService {
	// List<String> listAllEntryName();

	boolean add(String methodName, long time);

	List<MethodDto> listAll(String entryName);

	long getCount(String entryName);

	// int consume();

}
