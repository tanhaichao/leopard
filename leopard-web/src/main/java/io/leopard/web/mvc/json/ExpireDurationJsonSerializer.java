package io.leopard.web.mvc.json;

import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.AbstractJsonSerializer;

/**
 * 到期时长(精确到秒)
 * 
 * @author 谭海潮
 *
 */
public class ExpireDurationJsonSerializer extends AbstractJsonSerializer<Date> {

	@Override
	public void out(Date expireTime, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		gen.writeObject(expireTime);
		String fieldName = gen.getOutputContext().getCurrentName();
		String durationFieldName = fieldName.replaceFirst("Time$", "Duration");
		int expireDuration = 0;
		if (expireTime != null) {
			expireDuration = (int) ((expireTime.getTime() - System.currentTimeMillis()) / 1000L);
			if (expireDuration < 0) {
				expireDuration = 0;
			}
		}
		gen.writeFieldName(durationFieldName);
		gen.writeNumber(expireDuration);
	}

}
