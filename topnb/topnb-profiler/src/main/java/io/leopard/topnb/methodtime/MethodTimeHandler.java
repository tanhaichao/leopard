package io.leopard.topnb.methodtime;

import java.util.List;

public interface MethodTimeHandler {

	List<MethodDto> list(String entryName);

	String getTypeName(String entryName);

}
