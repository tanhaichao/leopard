package io.leopard.mvc.cors;

import java.util.List;

public interface AllowOriginResolver {

	List<String> resolve(String cors);
}
