package io.leopard.web.mvc;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Date Json序列化.
 * 
 * @author 谭海潮
 *
 */
public class DateJsonSerializer extends StdSerializer<Date> {

	private static final long serialVersionUID = 1L;

	public DateJsonSerializer() {
		super(Date.class, false);
	}

	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException {
		// generator.writeNumber(date.getTime());

		generator.writeString(date.getTime() + "ms");
	}

}
