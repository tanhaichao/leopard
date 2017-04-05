package io.leopard.web.mvc.json;

import com.fasterxml.jackson.databind.JsonSerializer;

public interface ModuleSerializer {

	JsonSerializer<?> get();
}
