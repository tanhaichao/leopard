package io.leopard.mvc.cors;

public interface AllowOriginResolver {

	boolean isEnable();

	void setCors(String cors);

	boolean match(String host);
}
