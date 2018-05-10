package io.leopard.web.mvc.json;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * MVC层JSON生成器
 * 
 * @author 谭海潮
 *
 */
public class MvcJsonGenerator {

	protected ObjectWriter formatWriter;

	protected ObjectMapper mapper; // can reuse, share

	public MvcJsonGenerator() {
		Iterator<ModuleSerializer> iterator = ServiceLoader.load(ModuleSerializer.class).iterator();
		// voFiller.init();
		// Onum序列化
		SimpleModule module = new SimpleModule();
		module.addSerializer(new OnumJsonSerializer());
		while (iterator.hasNext()) {
			ModuleSerializer moduleSerializer = iterator.next();
			JsonSerializer<?> serializer = moduleSerializer.get();
			if (serializer != null) {
				module.addSerializer(serializer);
			}
		}

		// boolean enable = UnderlineNameConfiger.isEnable();
		// // System.err.println("MappingJacksonResponseBodyAdvice underline:" + underline + " enable:" + enable);
		// if (enable) {
		// mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		// // mapper.registerModule(module);
		// // formatWriter = mapper.writer().withDefaultPrettyPrinter();
		// // mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		// }
		// else {
		mapper = new ObjectMapper();
		// mapper = new ObjectMapper();
		// }
		mapper.registerModule(module);
		formatWriter = mapper.writer().withDefaultPrettyPrinter();
	}

	public String toJson(Object data, boolean format) throws JsonProcessingException {
		String json = null;
		if (format) {
			json = formatWriter.writeValueAsString(data);
		}
		else {
			json = mapper.writeValueAsString(data);
		}
		return json;
	}
}
